package com.oj.ojBackend.model.dto.user;

import com.oj.ojBackend.common.PageRequest;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * user query request
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

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
     * user profile
     */
    private String userProfile;

    /**
     * user role：user/admin/ban
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}