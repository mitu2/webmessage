package com.brageast.project.webmessage.controller;

import com.brageast.project.webmessage.pojo.table.UserTable;
import com.brageast.project.webmessage.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {

    private final UserService userService;

    @GetMapping(path = {"user/{username}", "user"})
    UserTable getUserTableByUsername(@PathVariable(required = false) String username) {
        if (username == null) {
            return userService.findCurrentLoginUserTable();
        }
        return userService.findUser(username);
    }

}
