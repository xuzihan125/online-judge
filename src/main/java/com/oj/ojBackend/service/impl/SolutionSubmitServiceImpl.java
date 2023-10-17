package com.oj.ojBackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.ojBackend.common.ErrorCode;
import com.oj.ojBackend.constant.CommonConstant;
import com.oj.ojBackend.exception.BusinessException;
import com.oj.ojBackend.judge.JudgeService;
import com.oj.ojBackend.mapper.SolutionSubmitMapper;
import com.oj.ojBackend.model.dto.solutionsubmit.SolutionSubmitQueryRequest;
import com.oj.ojBackend.model.dto.solutionsubmit.SolutionSubmitAddRequest;
import com.oj.ojBackend.model.entity.Question;
import com.oj.ojBackend.model.entity.SolutionSubmit;
import com.oj.ojBackend.model.entity.User;
import com.oj.ojBackend.model.enums.SolutionLanguageEnum;
import com.oj.ojBackend.model.enums.SolutionSubmitStatusEnum;
import com.oj.ojBackend.model.vo.SolutionSubmitVO;
import com.oj.ojBackend.model.vo.UserVO;
import com.oj.ojBackend.service.QuestionService;
import com.oj.ojBackend.service.SolutionSubmitService;
import com.oj.ojBackend.service.UserService;
import com.oj.ojBackend.utils.SqlUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author xuzih
 * @description implement operation service on solution_submit table
 * @createDate 2023-10-02 21:28:26
 */
@Service
public class SolutionSubmitServiceImpl extends ServiceImpl<SolutionSubmitMapper, SolutionSubmit>
        implements SolutionSubmitService {
    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    @Resource
    @Lazy
    private JudgeService judgeService;

    /**
     * submit one solution
     *
     * @param solutionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doSolutionSubmit(SolutionSubmitAddRequest solutionSubmitAddRequest, User loginUser) {
        // check if the solutionSubmit exists.
        String language = solutionSubmitAddRequest.getLanguage();
        SolutionLanguageEnum languageEnum = SolutionLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "language provided not acceptable");
        }
        String code = solutionSubmitAddRequest.getCode();
        Long questionId = solutionSubmitAddRequest.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //todo check if the solutionSubmit is already submitted
        Long userId = loginUser.getId();
        SolutionSubmit solutionSubmit = new SolutionSubmit();
        solutionSubmit.setLanguage(language);
        solutionSubmit.setCode(code);
        solutionSubmit.setJudgeInfo("{}");
        solutionSubmit.setStatus(SolutionSubmitStatusEnum.WAITING.getValue());
        solutionSubmit.setQuestionId(questionId);
        solutionSubmit.setUserId(userId);
        boolean save = this.save(solutionSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "fail to insert new solution submission");
        }
        CompletableFuture.runAsync(() ->{
            judgeService.doJudge(solutionSubmit.getId());
        });
        return solutionSubmit.getId();
    }

    /**
     * get the query wrapper based on the query object
     *
     * @param solutionSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<SolutionSubmit> getQueryWrapper(SolutionSubmitQueryRequest solutionSubmitQueryRequest) {
        QueryWrapper<SolutionSubmit> queryWrapper = new QueryWrapper<>();
        if (solutionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        String language = solutionSubmitQueryRequest.getLanguage();
        Long questionId = solutionSubmitQueryRequest.getQuestionId();
        Integer status = solutionSubmitQueryRequest.getStatus();
        Long userId = solutionSubmitQueryRequest.getUserId();
        String sortField = solutionSubmitQueryRequest.getSortField();
        String sortOrder = solutionSubmitQueryRequest.getSortOrder();

        // build search condition
        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(SolutionSubmitStatusEnum.getEnumByValue(status) != null, "status", status);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);

        return queryWrapper;
    }

    /**
     * get masked solution submit data
     * @param solutionSubmit
     * @param loginUser
     * @return
     */
    @Override
    public SolutionSubmitVO getSolutionSubmitVO(SolutionSubmit solutionSubmit, User loginUser) {
        SolutionSubmitVO solutionSubmitVO = SolutionSubmitVO.objToVo(solutionSubmit);
        // data mask
        Long userId = loginUser.getId();
        if (!userId.equals(solutionSubmitVO.getId()) && userService.isAdmin(loginUser)) {
            solutionSubmitVO.setCode("");
        }
        return solutionSubmitVO;
    }

    /**
     * get masked solution submit data in page
     * @param solutionSubmitPage
     * @param loginUser
     * @return
     */
    @Override
    public Page<SolutionSubmitVO> getSolutionSubmitVOPage(Page<SolutionSubmit> solutionSubmitPage, User loginUser) {
        List<SolutionSubmit> solutionSubmitList = solutionSubmitPage.getRecords();
        Page<SolutionSubmitVO> solutionSubmitVOPage = new Page<>(solutionSubmitPage.getCurrent(), solutionSubmitPage.getSize(), solutionSubmitPage.getTotal());
        if (CollectionUtils.isEmpty(solutionSubmitList)) {
            return solutionSubmitVOPage;
        }
        // mask data
        List<SolutionSubmitVO> solutionSubmitVOList = solutionSubmitList.stream().map(solutionSubmit -> getSolutionSubmitVO(solutionSubmit, loginUser)).collect(Collectors.toList());
        solutionSubmitVOPage.setRecords(solutionSubmitVOList);
        return solutionSubmitVOPage;
    }

}




