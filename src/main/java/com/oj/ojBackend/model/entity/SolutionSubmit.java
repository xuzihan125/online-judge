package com.oj.ojBackend.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * solution submit by user
 * @TableName solution_submit
 */
@TableName(value ="solution_submit")
@Data
public class SolutionSubmit implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * language used for coding
     */
    private String language;

    /**
     * code submitted by user
     */
    private String code;

    /**
     * result of judge. stored in a json format
     */
    private String judgeInfo;

    /**
     * status of judging(0 - to be judged / 1 - judging / 2 - success / 3 - fail)
     */
    private Integer status;

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