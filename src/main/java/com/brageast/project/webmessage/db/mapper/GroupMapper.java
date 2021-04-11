package com.brageast.project.webmessage.db.mapper;

import com.brageast.project.webmessage.pojo.table.GroupTable;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface GroupMapper {


    @Results(id = "GROUP_TABLE", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "group_describe", property = "describe")
    })
    @Select(value = {
            "<script>",
            "SELECT id, name, group_describe",
            "FROM user_group",
            "WHERE id in (",
            "    <foreach collection=\"groupIds\" item=\"item\" index=\"ind\" separator=\",\">",
            "        #{item}",
            "    </foreach>",
            ") </script>"
    })
    List<GroupTable> findGroupInGroupIds(@Param("groupIds") Set<Long> groupIds);

}
