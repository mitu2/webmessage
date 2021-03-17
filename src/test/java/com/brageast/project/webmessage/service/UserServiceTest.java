package com.brageast.project.webmessage.service;

import com.brageast.project.webmessage.db.repository.AuthorityRepository;
import com.brageast.project.webmessage.entity.User;
import com.brageast.project.webmessage.entity.table.AuthorityTable;
import com.brageast.project.webmessage.entity.table.UserTable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private EntityManager manager;

    @Test
    @Transactional
    void addUser() {
        final AuthorityTable base = authorityRepository.save(new AuthorityTable(1L, "base"));
        System.out.println(base);
        final UserTable userTable = userService.addUser(new User("admin", "admin", "admin@admin.com"));
        System.out.println(userTable);
        manager.getTransaction().commit();
        final UserTable user = userService.findUser(userTable.getId());
        System.out.println(user.getAuthorityTables());
    }

    @Transactional
    @Test
    void findUser() {
        final UserTable user = userService.findUser(1L);
        System.out.println(user.getAuthorityTables());
    }
}