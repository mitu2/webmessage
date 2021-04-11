package com.brageast.project.webmessage.db.repository;

import com.brageast.project.webmessage.pojo.table.GroupTable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepositoryImplementation<GroupTable, Long> {
}
