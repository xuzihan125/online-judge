package com.oj.ojBackend.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.oj.ojBackend.annotation.AuthCheck;
import com.oj.ojBackend.common.BaseResponse;
import com.oj.ojBackend.common.DeleteRequest;
import com.oj.ojBackend.common.ErrorCode;
import com.oj.ojBackend.common.ResultUtils;
import com.oj.ojBackend.constant.UserConstant;
import com.oj.ojBackend.exception.BusinessException;
import com.oj.ojBackend.exception.ThrowUtils;
import com.oj.ojBackend.model.dto.question.*;
import com.oj.ojBackend.model.dto.solutionsubmit.SolutionSubmitAddRequest;
import com.oj.ojBackend.model.dto.solutionsubmit.SolutionSubmitQueryRequest;
import com.oj.ojBackend.model.entity.Question;
import com.oj.ojBackend.model.entity.SolutionSubmit;
import com.oj.ojBackend.model.entity.User;
import com.oj.ojBackend.model.enums.UserRoleEnum;
import com.oj.ojBackend.model.vo.QuestionAdminVO;
import com.oj.ojBackend.model.vo.QuestionVO;
import com.oj.ojBackend.model.vo.SolutionSubmitVO;
import com.oj.ojBackend.service.QuestionService;
import com.oj.ojBackend.service.SolutionSubmitService;
import com.oj.ojBackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * question api
 *
 */
@RestController
@RequestMapping("/question")
@Slf4j
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();


    /**
     * create new question
     *
     * @param questionAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addQuestion(@RequestBody QuestionAddRequest questionAddRequest, HttpServletRequest request) {
        if (questionAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionAddRequest, question);
        List<String> tags = questionAddRequest.getTags();
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }
        List<JudgeCase> judgeCaseList = questionAddRequest.getJudgeCase();
        if(judgeCaseList != null){
            question.setJudgeCase(GSON.toJson(judgeCaseList));
        }
        JudgeConfig judgeConfig = questionAddRequest.getJudgeConfig();
        if(judgeConfig!=null){
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        questionService.validQuestion(question, true);
        User loginUser = userService.getLoginUser(request);
        question.setUserId(loginUser.getId());
        question.setFavourNum(0);
        question.setLikeNum(0);
        boolean result = questionService.save(question);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newQuestionId = question.getId();
        return ResultUtils.success(newQuestionId);
    }

    /**
     * delete question
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteQuestion(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // check question existence
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // only creator or the admin can delete question
        if (!oldQuestion.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = questionService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * update(only by admin)
     *
     * @param questionUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest questionUpdateRequest) {
        if (questionUpdateRequest == null || questionUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionUpdateRequest, question);
        List<String> tags = questionUpdateRequest.getTags();
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }
        List<JudgeCase> judgeCaseList = questionUpdateRequest.getJudgeCase();
        if(judgeCaseList != null){
            question.setJudgeCase(GSON.toJson(judgeCaseList));
        }
        JudgeConfig judgeConfig = questionUpdateRequest.getJudgeConfig();
        if(judgeConfig!=null){
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        // check for question id
        questionService.validQuestion(question, false);
        long id = questionUpdateRequest.getId();
        // check if question id exist
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = questionService.updateById(question);
        return ResultUtils.success(result);
    }

    /**
     * get masked question vo based on id
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<QuestionVO> getQuestionVOById(Long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(id);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(questionService.getQuestionVO(question, request));
    }

    /**
     * get unmasked question by id(only by creator or admin)
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<QuestionAdminVO> getQuestionAdminVOById(Long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(id);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        User user = userService.getLoginUser(request);
        if(!question.getUserId().equals(user.getId()) && user.getUserRole().equals(UserRoleEnum.ADMIN.getValue())){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        return ResultUtils.success(questionService.getQuestionAdminVO(question, request));
    }

    /**
     * get masked question vo in page
     *
     * @param questionQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<QuestionVO>> listQuestionVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
            HttpServletRequest request) {
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // check crawler
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        return ResultUtils.success(questionService.getQuestionVOPage(questionPage, request));
    }

    /**
     * get unmasked question in page (only by admin)
     *
     * @param questionQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionAdminVO>> listQuestionAdminVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
                                                                  HttpServletRequest request) {
        if(!userService.isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // check crawler
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        return ResultUtils.success(questionService.getQuestionAdminVOPage(questionPage, request));
    }

    /**
     * get masked question create by current user in page
     *
     * @param questionQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<QuestionVO>> listMyQuestionVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
            HttpServletRequest request) {
        if (questionQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        questionQueryRequest.setUserId(loginUser.getId());
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // check crawler
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        return ResultUtils.success(questionService.getQuestionVOPage(questionPage, request));
    }

    /**
     * edit question(creator or admin)
     *
     * @param questionEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editQuestion(@RequestBody QuestionEditRequest questionEditRequest, HttpServletRequest request) {
        if (questionEditRequest == null || questionEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionEditRequest, question);
        List<String> tags = questionEditRequest.getTags();
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }
        // check parameter
        questionService.validQuestion(question, false);
        User loginUser = userService.getLoginUser(request);
        long id = questionEditRequest.getId();
        // check for question existence
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // only user or admin can edit
        if (!oldQuestion.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = questionService.updateById(question);
        return ResultUtils.success(result);
    }

    @Resource
    private SolutionSubmitService solutionSubmitService;

    /**
     * submit solution
     *
     * @param solutionSubmitAddRequest
     * @param request
     * @return resultNum new question id
     */
    @PostMapping("/solutionSubmit/do")
    public BaseResponse<Long> doSolutionSubmit(@RequestBody SolutionSubmitAddRequest solutionSubmitAddRequest,
                                               HttpServletRequest request) {
        if (solutionSubmitAddRequest == null || solutionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // can only submit after login
        final User loginUser = userService.getLoginUser(request);
        long result = solutionSubmitService.doSolutionSubmit(solutionSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }

    /**
     * get solution submit list in pages(only admin or user himself can see private info(code))
     *
     * @param solutionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/solutionSubmit/list/page/vo")
    public BaseResponse<Page<SolutionSubmitVO>> listSolutionSubmitVOByPage(@RequestBody SolutionSubmitQueryRequest solutionSubmitQueryRequest,
                                                                     HttpServletRequest request) {
        long current = solutionSubmitQueryRequest.getCurrent();
        long size = solutionSubmitQueryRequest.getPageSize();
        //check for crawler
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // get original data from database
        Page<SolutionSubmit> solutionSubmitPage = solutionSubmitService.page(new Page<>(current, size),
                solutionSubmitService.getQueryWrapper(solutionSubmitQueryRequest));
        // Data Masking
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(solutionSubmitService.getSolutionSubmitVOPage(solutionSubmitPage, loginUser));
    }

}
