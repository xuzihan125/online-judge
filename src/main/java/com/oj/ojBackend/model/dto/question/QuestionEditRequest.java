package com.oj.ojBackend.model.dto.question;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * question edit request
 */
@Data
public class QuestionEditRequest implements Serializable {

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