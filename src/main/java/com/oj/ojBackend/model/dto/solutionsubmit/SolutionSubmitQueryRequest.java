package com.oj.ojBackend.model.dto.solutionsubmit;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.oj.ojBackend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * query request
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SolutionSubmitQueryRequest extends PageRequest implements Serializable {

    /**
     * language used for coding
     */
    private String language;

    /**
     * id of the question
     */
    private Long questionId;

    /**
     * status of judging
     */
    private Integer status;

    /**
     * user id of the creator
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}