package com.brageast.project.webmessage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebHtmlController {

    @GetMapping(path = {"wx", "wx/**"})
    String vueHtml() {
        return "vx";
    }

}
