package com.brageast.project.webmessage.db.repository;

import com.brageast.project.webmessage.entity.table.AuthorityTable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "authority")
public interface AuthorityRepository extends JpaRepositoryImplementation<AuthorityTable, Long> {
}
