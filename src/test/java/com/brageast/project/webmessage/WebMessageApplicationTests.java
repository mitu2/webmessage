package com.brageast.project.webmessage;

import com.brageast.project.webmessage.db.mapper.GroupMapper;
import com.brageast.project.webmessage.pojo.table.GroupTable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;

/**
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class WebMessageApplicationTests {

    @Autowired
    private GroupMapper groupMapper;

    @Test
    void contextLoads() {
//        HashSet<Long> ids = new HashSet<>();
//        ids.add(1L);
//        ids.add(2L);
//        List<GroupTable> groupInGroupIds = groupMapper.findGroupInGroupIds(ids);
    }

}
