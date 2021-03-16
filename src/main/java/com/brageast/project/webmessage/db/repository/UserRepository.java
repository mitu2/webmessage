package com.brageast.project.webmessage.db.repository;

import com.brageast.project.webmessage.entity.table.UserTable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepositoryImplementation<UserTable, Integer> {

    boolean existsByUsername(String username);

    UserTable findByUsername(String username);

}
