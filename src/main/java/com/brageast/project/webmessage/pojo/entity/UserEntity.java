package com.brageast.project.webmessage.pojo.entity;

import com.brageast.project.webmessage.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserEntity implements User {

    @NotBlank(message = "用户名不能为空")
    @Length(min = 2, max = 18, message = "用户名不能小于2或者大于18")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Length(min = 5, max = 15, message = "密码不能小于6或者大于15")
    private String password;
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
//    @NotBlank(message = "头像不能为空")
    private String avatar;

}
