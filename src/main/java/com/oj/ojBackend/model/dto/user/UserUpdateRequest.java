package com.oj.ojBackend.model.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * user
 *
 */
@Data
public class UserUpdateRequest implements Serializable {
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

    private static final long serialVersionUID = 1L;
}