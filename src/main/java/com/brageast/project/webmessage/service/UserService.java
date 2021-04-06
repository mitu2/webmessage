package com.brageast.project.webmessage.service;

import com.brageast.project.webmessage.pojo.User;
import com.brageast.project.webmessage.pojo.entity.UserEntity;
import com.brageast.project.webmessage.pojo.table.UserTable;
import com.brageast.project.webmessage.exception.UserExistedException;
import com.brageast.project.webmessage.exception.UserLoginFailedException;
import com.brageast.project.webmessage.exception.UserNotFoundException;

import javax.validation.constraints.NotNull;

public interface UserService {

    /**
     * 添加一个用户
     *
     * @param user 用户信息
     * @return user添加成功信息
     * @throws UserNotFoundException 如果用户已存在抛出异常
     */
    UserTable addUser(User user) throws UserExistedException;

    /**
     * 根据id查找用户
     *
     * @param id 用户id
     * @return 用户信息
     * @throws UserNotFoundException 如果未找到抛出异常
     */
    UserTable findUser(Long id) throws UserNotFoundException;

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名字
     * @return 用户信息
     * @throws UserNotFoundException 如果未找到抛出异常
     */
    UserTable findUser(String username) throws UserNotFoundException;

    /**
     * 用户尝试登录相关操作
     *
     * @param user 用户账户密码信息 用户不能为空
     * @return 生成token
     * @throws UserLoginFailedException 用户登录失败
     * @throws UserNotFoundException 用户未找到异常
     */
    String doLogin(@NotNull User user) throws UserLoginFailedException, UserNotFoundException;

}
