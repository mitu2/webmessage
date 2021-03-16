package com.brageast.project.webmessage.controller;

import com.brageast.project.webmessage.entity.User;
import com.brageast.project.webmessage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "api/user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {

    private final UserService userService;

    @PostMapping(path = "register")
    ResponseEntity<Object> register(@Valid @RequestBody User user) {
        userService.addUser(user);
        return null;
    }

}
