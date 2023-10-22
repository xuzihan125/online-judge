package com.oj.ojBackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * problem
 * @TableName question
 */
@TableName(value ="question")
@Data
public class Question implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
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
    private String tags;

    /**
     * answer of the problem
     */
    private String answer;

    /**
     * total time of submit
     */
    private Integer submitNum;

    /**
     * total number of accept
     */
    private Integer acceptNum;

    /**
     * test case.(json format)
     */
    private String judgeCase;

    /**
     * test settings(json format)
     */
    private String judgeConfig;

    /**
     * number of likes
     */
    private Integer likeNum;

    /**
     * number of favor
     */
    private Integer favourNum;

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
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}