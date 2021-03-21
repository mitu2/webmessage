package com.brageast.project.webmessage.db.repository;

import com.brageast.project.webmessage.pojo.table.AuthorityTable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "authority")
public interface AuthorityRepository extends JpaRepositoryImplementation<AuthorityTable, Long> {

    List<AuthorityTable> findAllByUserId(Long userId);

}
