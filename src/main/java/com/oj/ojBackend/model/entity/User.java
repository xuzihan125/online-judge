package com.oj.ojBackend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * user account
     */
    private String userAccount;

    /**
     * user password
     */
    private String userPassword;

    /**
     * id from other part
     */
    private String unionId;

    /**
     * openId from other platform
     */
    private String mpOpenId;

    /**
     * username
     */
    private String userName;

    /**
     * user avatar
     */
    private String userAvatar;

    /**
     * user profile
     */
    private String userProfile;

    /**
     * user role：user/admin/ban
     */
    private String userRole;

    /**
     * create time
     */
    private Date createTime;

    /**
     * last update time
     */
    private Date updateTime;

    /**
     * account deleted
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}