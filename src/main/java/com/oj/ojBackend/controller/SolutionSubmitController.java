//package com.oj.ojBackend.controller;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.oj.ojBackend.common.BaseResponse;
//import com.oj.ojBackend.common.ErrorCode;
//import com.oj.ojBackend.common.ResultUtils;
//import com.oj.ojBackend.exception.BusinessException;
//import com.oj.ojBackend.exception.ThrowUtils;
//import com.oj.ojBackend.model.dto.question.QuestionQueryRequest;
//import com.oj.ojBackend.model.dto.solutionsubmit.SolutionSubmitAddRequest;
//import com.oj.ojBackend.model.dto.solutionsubmit.SolutionSubmitQueryRequest;
//import com.oj.ojBackend.model.entity.Question;
//import com.oj.ojBackend.model.entity.SolutionSubmit;
//import com.oj.ojBackend.model.entity.User;
//import com.oj.ojBackend.model.vo.QuestionVO;
//import com.oj.ojBackend.model.vo.SolutionSubmitVO;
//import com.oj.ojBackend.service.SolutionSubmitService;
//import com.oj.ojBackend.service.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 帖子点赞接口
// *
// * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
// * @from <a href="https://yupi.icu">编程导航知识星球</a>
// */
//@RestController
//@RequestMapping("/question_submit")
//@Slf4j
//public class SolutionSubmitController {
//
//    @Resource
//    private SolutionSubmitService solutionSubmitService;
//
//    @Resource
//    private UserService userService;
//
//    /**
//     * submit solution
//     *
//     * @param solutionSubmitAddRequest
//     * @param request
//     * @return resultNum new question id
//     */
//    @PostMapping("/")
//    public BaseResponse<Long> doSolutionSubmit(@RequestBody SolutionSubmitAddRequest solutionSubmitAddRequest,
//                                         HttpServletRequest request) {
//        if (solutionSubmitAddRequest == null || solutionSubmitAddRequest.getQuestionId() <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        // can only submit after login
//        final User loginUser = userService.getLoginUser(request);
//        long result = solutionSubmitService.doSolutionSubmit(solutionSubmitAddRequest, loginUser);
//        return ResultUtils.success(result);
//    }
//
//    /**
//     * get solution submit list in pages(only admin or user himself can see private info(code))
//     *
//     * @param solutionSubmitQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/list/page/vo")
//    public BaseResponse<Page<SolutionSubmitVO>> listQuestionVOByPage(@RequestBody SolutionSubmitQueryRequest solutionSubmitQueryRequest,
//                                                                     HttpServletRequest request) {
//        long current = solutionSubmitQueryRequest.getCurrent();
//        long size = solutionSubmitQueryRequest.getPageSize();
//        //check for crawler
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        // get original data from database
//        Page<SolutionSubmit> solutionSubmitPage = solutionSubmitService.page(new Page<>(current, size),
//                solutionSubmitService.getQueryWrapper(solutionSubmitQueryRequest));
//        // Data Masking
//        User loginUser = userService.getLoginUser(request);
//        return ResultUtils.success(solutionSubmitService.getSolutionSubmitVOPage(solutionSubmitPage, loginUser));
//    }
//
//}
