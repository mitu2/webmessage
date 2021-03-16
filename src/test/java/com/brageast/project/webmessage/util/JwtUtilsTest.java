package com.brageast.project.webmessage.util;

import com.brageast.project.webmessage.entity.table.UserTable;
import org.junit.jupiter.api.Test;

class JwtUtilsTest {

    @Test
    void testBuildToken() throws Exception {
        final String token = JwtUtils.buildToken(UserTable.builder().username("admin").build());
        System.out.println(token);
        System.out.println(JwtUtils.getUsername(token));
    }

}