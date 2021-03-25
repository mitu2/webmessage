package com.brageast.project.webmessage.config;

import com.brageast.project.webmessage.config.security.CheckTokenFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/websocket")
//                .allowedMethods("GET")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }
}
