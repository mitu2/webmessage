package com.brageast.project.webmessage.service;

import com.brageast.project.webmessage.pojo.table.GroupRefTable;
import com.brageast.project.webmessage.pojo.table.GroupTable;

import java.util.List;
import java.util.Set;

public interface GroupService {

    List<GroupRefTable> getGroupRefByUserId(Long userId);

    /**
     * 得到groupId对应的user_group表集合
     * @param groupIds group id 集合
     * @return 得到groupId对应的user_group表集合
     */
    List<GroupTable> getGroupInGroupIds(Set<Long> groupIds);

}
