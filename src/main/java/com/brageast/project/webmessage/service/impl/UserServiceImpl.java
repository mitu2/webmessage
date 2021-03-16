package com.brageast.project.webmessage.service.impl;

import com.brageast.project.webmessage.db.repository.UserRepository;
import com.brageast.project.webmessage.entity.User;
import com.brageast.project.webmessage.entity.table.UserTable;
import com.brageast.project.webmessage.exception.UserExistedException;
import com.brageast.project.webmessage.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

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
     */
    @Override
    public void addUser(User user) {
        final String username = user.getUsername();
        if (userRepository.existsByUsernameEquals(username)) {
            throw new UserExistedException(username);
        }
        final UserTable userTable = userUserTableConvert.convert(user);
        if (userTable != null) {
            userRepository.save(userTable);
            log.info("用户{}添加添加成功, 信息: {}", username, userTable.toString());
        }
    }

}
