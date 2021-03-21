package com.brageast.project.webmessage.db.repository;

import com.brageast.project.webmessage.pojo.table.UserTable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "users")
public interface UserRepository extends JpaRepositoryImplementation<UserTable, Long> {

    /**
     * 查找用户是否存在
     *
     * @param username 用户名
     * @return 是否存在用户
     */
    boolean existsByUsername(String username);

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserTable findByUsername(String username);

}
