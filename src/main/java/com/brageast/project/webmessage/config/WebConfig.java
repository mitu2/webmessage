package com.brageast.project.webmessage.config;

import com.brageast.project.webmessage.exception.UserNotFoundException;
import com.brageast.project.webmessage.pojo.entity.UserEntity;
import com.brageast.project.webmessage.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    @Autowired
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.build();
    }

    @Component
    @ConditionalOnProperty(prefix = "default-user", value = "enabled")
    public static class InnerApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

        private final UserService userService;

        @Value("${default-user.username:admin}")
        private String username;
        @Value("${default-user.password:admin}")
        private String password;
        @Value("${default-user.email:chenmoand@outlook.com}")
        private String email;

        @Autowired
        public InnerApplicationReadyListener(UserService userService) {
            this.userService = userService;
        }

        @Override
        public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
            try {
                userService.findUser(username);
                log.info("已存在默认{}用户！", username);
            } catch (UserNotFoundException e) {
                userService.addUser(new UserEntity(username, password, email, null));
                log.info("添加默认{}用户成功！", username);
            }
        }
    }


}
