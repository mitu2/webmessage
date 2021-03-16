package com.brageast.project.webmessage.service;

import com.brageast.project.webmessage.db.repository.AuthorityRepository;
import com.brageast.project.webmessage.entity.User;
import com.brageast.project.webmessage.entity.table.AuthorityTable;
import com.brageast.project.webmessage.entity.table.UserTable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Transactional
    @Test
    void addUser() {
        final UserTable userTable = userService.addUser(new User("admin", "admin", "admin@admin.com"));
        System.out.println(userTable);
        final AuthorityTable base = authorityRepository.saveAndFlush(new AuthorityTable(userTable.getId(), "base"));
        System.out.println(base);

        final UserTable user = userService.findUser(userTable.getId());
        System.out.println(user.getAuthorityTables());
    }
}