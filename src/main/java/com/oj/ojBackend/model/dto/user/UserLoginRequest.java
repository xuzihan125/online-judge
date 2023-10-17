package com.oj.ojBackend.model.dto.user;

import java.io.Serializable;
import lombok.Data;

/**
 * user login request
 *
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;
}
