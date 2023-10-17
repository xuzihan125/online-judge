package com.oj.ojBackend.model.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 user vo (masked)
 *
 */
@Data
public class UserVO implements Serializable {

    /**
     * id
     */
    private Long id;

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

    private static final long serialVersionUID = 1L;
}