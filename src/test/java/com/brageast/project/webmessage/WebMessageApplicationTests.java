package com.brageast.project.webmessage;

import com.brageast.project.webmessage.config.security.CustomizeUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootTest
class WebMessageApplicationTests {

    @Autowired
    private CustomizeUserDetailsService userDetailsService;

    @Test
    void contextLoads() {
        final UserDetails userDetails = userDetailsService.loadUserByUsername("admin");

    }

}
