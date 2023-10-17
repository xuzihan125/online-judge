package com.oj.ojBackend.model.dto.question;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.oj.ojBackend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * question query request
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {

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
     * user id of the creater
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}