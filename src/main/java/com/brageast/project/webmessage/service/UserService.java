package com.brageast.project.webmessage.service;

import com.brageast.project.webmessage.entity.User;
import com.brageast.project.webmessage.entity.table.UserTable;
import com.brageast.project.webmessage.exception.UserExistedException;
import com.brageast.project.webmessage.exception.UserNotFoundException;

public interface UserService {

    /**
     * 添加一个用户
     * @param user 用户信息
     * @throws UserNotFoundException 如果用户已存在抛出异常
     * @return user添加成功信息
     */
    UserTable addUser(User user) throws UserExistedException;

    /**
     * 根据id查找用户
     * @param id 用户id
     * @return 用户信息
     * @throws UserNotFoundException 如果未找到抛出异常
     */
    UserTable findUser(Integer id) throws UserNotFoundException;

    /**
     * 根据用户名查找用户
     * @param id 用户id
     * @return 用户信息
     * @throws UserNotFoundException 如果未找到抛出异常
     */
    UserTable findUser(String username) throws UserNotFoundException;

}
