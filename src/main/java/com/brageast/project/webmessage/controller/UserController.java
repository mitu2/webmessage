package com.brageast.project.webmessage.controller;

import com.brageast.project.webmessage.config.security.CustomizeUserDetailsService;
import com.brageast.project.webmessage.exception.UserNotFoundException;
import com.brageast.project.webmessage.pojo.ResponseMessage;
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

    private final CustomizeUserDetailsService userDetailsService;

    @Transactional
    @GetMapping(path = {"{username}", ""})
    ResponseMessage<UserDetails> getUserTableByUsername(@PathVariable(required = false) String username) {
        final UserDetails userDetails;
        if (username == null) {
            userDetails = getCurrentUserDetails();
        } else {
            userDetails = userDetailsService.loadUserByUsername(username);
        }
        return ResponseMessage.ok(userDetails);
    }

    private UserDetails getCurrentUserDetails() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLogin = authentication != null && authentication.isAuthenticated();
        if (isLogin && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        throw new UserNotFoundException("未找到相关用户!");
    }

}
