package com.brageast.project.webmessage.config;

import com.brageast.project.webmessage.entity.User;
import com.brageast.project.webmessage.entity.table.UserTable;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
        basePackages = "com.brageast.project.webmessage.db.repository"
)
@MapperScan(
        basePackages = "com.brageast.project.webmessage.db.mapper"
)
@EnableTransactionManagement
public class DataConfig {

}
