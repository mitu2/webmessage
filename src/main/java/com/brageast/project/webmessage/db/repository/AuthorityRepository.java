package com.brageast.project.webmessage.db.repository;

import com.brageast.project.webmessage.entity.table.AuthorityTable;
import com.brageast.project.webmessage.entity.table.UserTable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepositoryImplementation<AuthorityTable, Integer> {
}
