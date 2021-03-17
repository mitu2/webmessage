package com.brageast.project.webmessage.controller;

import com.brageast.project.webmessage.entity.ResponseMessage;
import com.brageast.project.webmessage.entity.User;
import com.brageast.project.webmessage.entity.table.UserTable;
import com.brageast.project.webmessage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LoginController {

    private final UserService userService;

    @PostMapping(path = "register")
    @ResponseBody
    ResponseMessage register(@Valid @RequestBody User user) {
        final UserTable userTable = userService.addUser(user);
        return ResponseMessage
                .builder()
                .data(userTable)
                .message("用户注册成功")
                .build();
    }

    @GetMapping("register")
    String registerHtml() {
        return "register";
    }

    @PostMapping(path = "login")
    @ResponseBody
    ResponseMessage login(@RequestBody User user) {
        final String token = userService.doLogin(user);
        return ResponseMessage
                .builder()
                .data(token)
                .build();
    }

}
