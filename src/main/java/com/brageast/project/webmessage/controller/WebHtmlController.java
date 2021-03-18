package com.brageast.project.webmessage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebHtmlController {

    @GetMapping(path = {"wx", "wx/**"})
    String vueHtml() {
        return "vx";
    }

}
