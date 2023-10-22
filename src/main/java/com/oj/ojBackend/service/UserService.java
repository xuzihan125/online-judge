package com.oj.ojBackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.ojBackend.model.dto.user.UserQueryRequest;
import com.oj.ojBackend.model.entity.User;
import com.oj.ojBackend.model.vo.LoginUserVO;
import com.oj.ojBackend.model.vo.UserVO;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * user service
 *
 */
public interface UserService extends IService<User> {

    /**
     * user register
     *
     * @param userAccount   user account
     * @param userPassword  user password
     * @param checkPassword user password validation
     * @return id of new user
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * user login
     *
     * @param userAccount  user account
     * @param userPassword user password
     * @param request
     * @return masked user data
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * get current login user
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * get current login user (allow not login)
     *
     * @param request
     * @return
     */
    User getLoginUserPermitNull(HttpServletRequest request);

    /**
     * check if current user is admin
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * check if user is admin
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);

    /**
     * user logout
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * masked login user info
     *
     * @return
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * masked user info
     *
     * @param user
     * @return
     */
    UserVO getUserVO(User user);

    /**
     * get list of masked user info
     *
     * @param userList
     * @return
     */
    List<UserVO> getUserVO(List<User> userList);

    /**
     * get query SQL
     *
     * @param userQueryRequest
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

}
