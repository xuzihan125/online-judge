package com.oj.ojBackend.constant;

/**
 * constant for user role type
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public interface UserConstant {

    /**
     * user in login state
     */
    String USER_LOGIN_STATE = "user_login";

    //  region 权限

    /**
     * default user role
     */
    String DEFAULT_ROLE = "user";

    /**
     * admin user role
     */
    String ADMIN_ROLE = "admin";

    /**
     * banned user role
     */
    String BAN_ROLE = "ban";

    // endregion
}
