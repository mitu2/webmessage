package com.brageast.project.webmessage.controller;

import com.brageast.project.webmessage.pojo.table.GroupRefTable;
import com.brageast.project.webmessage.pojo.table.GroupTable;
import com.brageast.project.webmessage.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "api/group")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GroupController {

    private final GroupService groupService;

    @PostMapping(path = "/getGroupInGroupIds")
    List<GroupTable> getGroupInGroupIds(@RequestBody Set<Long> ids) {
        return groupService.getGroupInGroupIds(ids);
    }

    @GetMapping(path = "getGroupRefByUserId")
    List<GroupRefTable> getGroupRefByUserId(@RequestParam(name = "userId") Long userId) {
        return groupService.getGroupRefByUserId(userId);
    }


}
