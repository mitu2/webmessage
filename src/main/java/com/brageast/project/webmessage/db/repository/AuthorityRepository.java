package com.brageast.project.webmessage.db.repository;

import com.brageast.project.webmessage.pojo.table.AuthorityTable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepositoryImplementation<AuthorityTable, Long> {

    List<AuthorityTable> findAllByUserId(Long userId);

}
