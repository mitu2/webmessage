package com.brageast.project.webmessage.pojo.entity;

import com.brageast.project.webmessage.pojo.Group;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
public class GroupEntity implements Group {

    @Max(value = 15L, message = "组的命名长度不能超过15")
    @NotBlank(message = "组的命名不能为空")
    private String name;

    private String describe;

}
