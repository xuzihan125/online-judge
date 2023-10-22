package com.oj.ojBackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.ojBackend.model.dto.question.QuestionQueryRequest;
import com.oj.ojBackend.model.entity.Question;
import com.oj.ojBackend.model.vo.QuestionAdminVO;
import com.oj.ojBackend.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author xuzih
* @description 针对表【question(problem)】的数据库操作Service
* @createDate 2023-10-02 21:28:26
*/
public interface QuestionService extends IService<Question> {
    /**
     * verification
     *
     * @param question
     * @param add
     */
    void validQuestion(Question question, boolean add);

    /**
     * create query condition
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);


    /**
     * create question vo
     *
     * @param question
     * @param request
     * @return
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * create question vo
     *
     * @param question
     * @param request
     * @return
     */
    QuestionAdminVO getQuestionAdminVO(Question question, HttpServletRequest request);




    /**
     * create page question vo 
     *
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);

    /**
     * create page question
     *
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionAdminVO> getQuestionAdminVOPage(Page<Question> questionPage, HttpServletRequest request);
}
