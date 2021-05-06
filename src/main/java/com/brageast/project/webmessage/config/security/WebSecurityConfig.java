package com.brageast.project.webmessage.config.security;

import com.brageast.project.webmessage.pojo.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ObjectMapper mapper;

    @Value("${spring.profiles.active}")
    private String active;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 查看persistent_logins表是否在Mysql中存在
     */
    private static final String CHECK_INFORMATION_SCHEMA_TABLE_SQL = "SELECT table_name FROM information_schema.TABLES WHERE table_name ='persistent_logins';";

    @Bean
    PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        JdbcTemplate jdbcTemplate = tokenRepository.getJdbcTemplate();
        if (jdbcTemplate != null) {
            List<String> query = jdbcTemplate.query(CHECK_INFORMATION_SCHEMA_TABLE_SQL, new BeanPropertyRowMapper<>(String.class));
            if (query.isEmpty()) {
                log.info("第一次为您创建persistent_logins表");
                tokenRepository.setCreateTableOnStartup(true);
            }
        }
        return tokenRepository;
    }


    private static final String ID_FOR_ENCODE = "bcrypt";

    @Bean
    @SuppressWarnings("deprecation")
    PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(ID_FOR_ENCODE, new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());
        return new DelegatingPasswordEncoder(ID_FOR_ENCODE, encoders);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if ("dev".equalsIgnoreCase(active)) {
            //允许跨域请求
            // by default uses a Bean by the name of corsConfigurationSource(官方说明，使下面配置的bean生效)
            http.cors(Customizer.withDefaults());
        }

        http.logout(logoutConfigurer -> {
            logoutConfigurer
                    .logoutUrl("/logout")
                    .logoutSuccessHandler((request, response, authentication) -> {
                        response.setCharacterEncoding("utf-8");
                        response.setContentType("application/json;charset=utf-8");
                        String msg = mapper.writeValueAsString(ResponseMessage.ok("登出成功"));
                        response.getWriter()
                                .println(msg);
                    })
                    .permitAll();
        });

        http.formLogin(loginConfigurer -> {
            loginConfigurer
                    .loginPage("/login")
                    .loginProcessingUrl("/api/login")
                    .passwordParameter("password")
                    .usernameParameter("username")
                    .successHandler((request, response, authentication) -> {
                        response.setCharacterEncoding("utf-8");
                        response.setContentType("application/json;charset=utf-8");
                        String msg = mapper.writeValueAsString(ResponseMessage.ok("登陆成功"));
                        response.getWriter()
                                .println(msg);

                    })
                    .failureHandler(((request, response, exception) -> {
                        response.setStatus(400);
                        response.setCharacterEncoding("utf-8");
                        response.setContentType("application/json;charset=utf-8");
                        String msg = mapper.writeValueAsString(ResponseMessage
                                .of(HttpStatus.BAD_REQUEST)
                                .message("登陆失败")
                                .build()
                        );
                        response.getWriter()
                                .println(msg);
                    }))
                    .permitAll();
        });

//        http.sessionManagement(sm -> {
//            // 默认不创建Session
//            sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        });

        http.rememberMe(rm -> {
            rm.tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(5L))
                    .userDetailsService(userDetailsService)
                    .rememberMeParameter("remember-me");
        });

        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/static/**", "/web-socket/**", "/api/**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    @Bean(name = "myAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
