package com.brageast.project.webmessage.db.repository;

import com.brageast.project.webmessage.pojo.table.GroupRefTable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRefRepository extends JpaRepositoryImplementation<GroupRefTable, Long> {

    List<GroupRefTable> findByUserId(Long userId);

}
