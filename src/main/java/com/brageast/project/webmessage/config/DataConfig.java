package com.brageast.project.webmessage.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
