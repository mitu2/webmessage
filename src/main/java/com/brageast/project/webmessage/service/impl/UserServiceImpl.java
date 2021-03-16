package com.brageast.project.webmessage.service.impl;

import com.brageast.project.webmessage.db.repository.UserRepository;
import com.brageast.project.webmessage.entity.User;
import com.brageast.project.webmessage.entity.table.UserTable;
import com.brageast.project.webmessage.exception.UserExistedException;
import com.brageast.project.webmessage.exception.UserNotFoundException;
import com.brageast.project.webmessage.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    private final Converter<User, UserTable> userUserTableConvert;

    /**
     * 添加一个用户
     * @param user 用户信息
     * @return user添加成功信息
     */
    @Override
    public UserTable addUser(User user) throws UserExistedException {
        final String username = user.getUsername();
        if (userRepository.existsByUsername(username)) {
            throw new UserExistedException(username);
        }
        UserTable userTable = userUserTableConvert.convert(user);
        if (userTable != null) {
            userTable = userRepository.save(userTable);
            log.info("用户{}添加添加成功, 信息: {}", username, userTable.toString());
        }
        return userTable;
    }

    /**
     * 根据id查找用户
     *
     * @param id 用户id
     * @return 用户信息
     * @throws UserNotFoundException 如果未找到抛出异常
     */
    @Override
    public UserTable findUser(Integer id) throws UserNotFoundException {
        final Optional<UserTable> userTable = userRepository.findById(id);
        return userTable.orElseThrow(() -> new UserNotFoundException("未找到用户, id:" + id));
    }

    /**
     * 根据用户名查找用户
     *
     * @param username@return 用户信息
     * @throws UserNotFoundException 如果未找到抛出异常
     */
    @Override
    public UserTable findUser(String username) throws UserNotFoundException {
        final UserTable userTable = userRepository.findByUsername(username);
        if (userTable == null) {
            throw new UserExistedException("未找到用户, 用户名:" + username);
        }
        return userTable;
    }

}
