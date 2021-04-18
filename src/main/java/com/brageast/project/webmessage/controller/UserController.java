package com.brageast.project.webmessage.controller;

import com.brageast.project.webmessage.config.security.CustomizeUserDetails;
import com.brageast.project.webmessage.exception.UserNotFoundException;
import com.brageast.project.webmessage.pojo.ResponseMessage;
import com.brageast.project.webmessage.pojo.table.UserTable;
import com.brageast.project.webmessage.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping(path = "api/user")
public class UserController {

    private final UserService userService;

    @Transactional
    @GetMapping(path = {"{id}", ""})
    ResponseMessage<UserTable> getUserTableById(@PathVariable(required = false) Long id) {
        final UserTable table;
        if (id == null) {
            table = ((CustomizeUserDetails) getCurrentUserDetails()).getUserTable();
        } else {
            table = userService.findUser(id);
        }
        return ResponseMessage.ok(table);
    }

    private UserDetails getCurrentUserDetails() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLogin = authentication != null && authentication.isAuthenticated();
        if (isLogin && authentication.getPrincipal() instanceof UserDetails) {
            return (CustomizeUserDetails) authentication.getPrincipal();
        }
        throw new UserNotFoundException("未找到相关用户!");
    }

}
