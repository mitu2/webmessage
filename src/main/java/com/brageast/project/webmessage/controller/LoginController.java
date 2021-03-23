package com.brageast.project.webmessage.controller;

import com.brageast.project.webmessage.pojo.ResponseMessage;
import com.brageast.project.webmessage.pojo.User;
import com.brageast.project.webmessage.pojo.table.UserTable;
import com.brageast.project.webmessage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping(path = {"register", "login"})
    String registerAndLoginHtml() {
        return "index";
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
