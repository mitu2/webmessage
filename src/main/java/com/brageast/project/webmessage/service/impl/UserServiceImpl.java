package com.brageast.project.webmessage.service.impl;

import com.brageast.project.webmessage.db.repository.UserRepository;
import com.brageast.project.webmessage.exception.UserExistedException;
import com.brageast.project.webmessage.exception.UserLoginFailedException;
import com.brageast.project.webmessage.exception.UserNotFoundException;
import com.brageast.project.webmessage.pojo.User;
import com.brageast.project.webmessage.pojo.entity.UserEntity;
import com.brageast.project.webmessage.pojo.table.UserTable;
import com.brageast.project.webmessage.service.UserService;
import com.brageast.project.webmessage.util.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ObjectMapper mapper;

    @Autowired
    public UserServiceImpl(Jackson2ObjectMapperBuilder builder) {
        this.mapper = builder.build();
    }


    /**
     * 添加一个用户
     *
     * @param user 用户信息
     * @return user添加成功信息
     */
    @Override
    public UserTable addUser(User user) throws UserExistedException {
        final String username = user.getUsername();
        if (userRepository.existsByUsername(username)) {
            throw new UserExistedException("用户名" + username + "已存在");
        }

        // User -> UserTable 转化User类型并加密密码
        UserTable userTable = userAsTable(user);
        if (userTable == null) {
            return null;
        }
        userTable.setPassword(passwordEncoder.encode(user.getPassword()));
        UserTable save = userRepository.save(userTable);
        if (log.isDebugEnabled()) {
            log.info("用户{}添加添加成功, 信息: {}", username, save.toString());
        }

        return save;
    }

    private UserTable userAsTable(User user) {
        if (user instanceof UserTable) {
            return (UserTable) user;
        }
        try {
            return mapper.readValue(mapper.writeValueAsString(user), UserTable.class);
        } catch (JsonProcessingException e) {
            if (log.isDebugEnabled()) {
                log.error(e.getMessage(), e);
            }
            return null;
        }
    }

    /**
     * 根据id查找用户
     *
     * @param id 用户id
     * @return 用户信息
     * @throws UserNotFoundException 如果未找到抛出异常
     */
    @Override
    public UserTable findUser(Long id) throws UserNotFoundException {

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("未找到用户, id:" + id));
    }

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名字
     * @return 用户信息
     * @throws UserNotFoundException 如果未找到抛出异常
     */
    @Override
    public UserTable findUser(String username) throws UserNotFoundException {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UserNotFoundException("未找到用户, 用户名:" + username));
    }

    /**
     * 用户尝试登录相关操作
     *
     * @param user 用户账户密码信息
     * @return 生成token
     * @throws UserLoginFailedException 用户登录失败
     */
    @Override
    public String doLogin(User user) throws UserLoginFailedException {
        Objects.requireNonNull(user, "user字段不能为空!");
        final UserTable userTable = findUser(user.getUsername());
        if (passwordEncoder.matches(user.getPassword(), userTable.getPassword())) {
            return JwtUtils.createToken(userTable);
        }
        throw new UserLoginFailedException("用户" + user.getUsername() + "登录失败, 密码错误!");
    }

}
