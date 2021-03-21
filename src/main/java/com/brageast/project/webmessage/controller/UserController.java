package com.brageast.project.webmessage.controller;

import com.brageast.project.webmessage.pojo.table.UserTable;
import com.brageast.project.webmessage.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {

    private final UserService userService;

    @GetMapping(path = "user/{username}")
    UserTable getUserTableByUsername(@PathVariable String username) {
        return userService.findUser(username);
    }

    @GetMapping(path = "user")
    UserTable getUserTable() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserTable) {
            return (UserTable) authentication.getPrincipal();
        }
        return null;
    }
}
