package com.oj.ojBackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.ojBackend.model.dto.solutionsubmit.SolutionSubmitQueryRequest;
import com.oj.ojBackend.model.dto.solutionsubmit.SolutionSubmitAddRequest;
import com.oj.ojBackend.model.entity.SolutionSubmit;
import com.oj.ojBackend.model.entity.SolutionSubmit;
import com.oj.ojBackend.model.entity.User;
import com.oj.ojBackend.model.vo.SolutionSubmitVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author xuzih
* @description focus on the operation for table solution_submit
* @createDate 2023-10-02 21:28:26
*/
public interface SolutionSubmitService extends IService<SolutionSubmit> {
    /**
     * add one submit of solution to database
     *
     * @param solutionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doSolutionSubmit(SolutionSubmitAddRequest solutionSubmitAddRequest, User loginUser);

    /**
     * create query condition
     *
     * @param solutionSubmitQueryRequest
     * @return
     */
    QueryWrapper<SolutionSubmit> getQueryWrapper(SolutionSubmitQueryRequest solutionSubmitQueryRequest);


    /**
     * create solutionSubmit vo
     *
     * @param solutionSubmit
     * @param request
     * @return
     */
    SolutionSubmitVO getSolutionSubmitVO(SolutionSubmit solutionSubmit, User loginUser);

    /**
     * create page solutionSubmit vo 
     *
     * @param solutionSubmitPage
     * @param request
     * @return
     */
    Page<SolutionSubmitVO> getSolutionSubmitVOPage(Page<SolutionSubmit> solutionSubmitPage, User loginUser);
    
}
