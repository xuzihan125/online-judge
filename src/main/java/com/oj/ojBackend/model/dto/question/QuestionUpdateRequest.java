package com.oj.ojBackend.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * question update request
 *
 */
@Data
public class QuestionUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * title of the problem
     */
    private String title;

    /**
     * content of the problem
     */
    private String content;

    /**
     * tags of the problem, stored in json format
     */
    private List<String> tags;

    /**
     * answer of the problem
     */
    private String answer;


    /**
     * test case.(json format)
     */
    private List<JudgeCase> judgeCase;

    /**
     * test settings(json format)
     */
    private JudgeConfig judgeConfig;

    private static final long serialVersionUID = 1L;
}