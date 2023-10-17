package com.oj.ojBackend.model.dto.solutionsubmit;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * solution add request
 *
 */
@Data
public class SolutionSubmitAddRequest implements Serializable {

    /**
     * language used for coding
     */
    private String language;

    /**
     * code submitted by user
     */
    private String code;

    /**
     * id of the question
     */
    private Long questionId;

    /**
     * user id of the creater
     */
    private Long userId;

    /**
     * time of creation
     */
    private Date createTime;

    /**
     * time of last update
     */
    private Date updateTime;

    /**
     * is this problem deleted
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}