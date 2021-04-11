package com.brageast.project.webmessage.service.impl;

import com.brageast.project.webmessage.db.mapper.GroupMapper;
import com.brageast.project.webmessage.db.repository.GroupRefRepository;
import com.brageast.project.webmessage.pojo.table.GroupRefTable;
import com.brageast.project.webmessage.pojo.table.GroupTable;
import com.brageast.project.webmessage.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GroupServiceImpl implements GroupService {

    private final GroupMapper groupMapper;

    private final GroupRefRepository groupRefRepository;

    @Override
    public List<GroupRefTable> getGroupRefByUserId(Long userId) {
        if (userId == null) {
            return Collections.emptyList();
        }
        return groupRefRepository.findByUserId(userId);
    }

    @Override
    public List<GroupTable> getGroupInGroupIds(Set<Long> groupIds) {
        if (groupIds == null || groupIds.isEmpty()) {
            return Collections.emptyList();
        }
        return groupMapper.findGroupInGroupIds(groupIds);
    }
}
