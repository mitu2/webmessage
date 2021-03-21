package com.brageast.project.webmessage.config.security;

import com.brageast.project.webmessage.pojo.User;
import com.brageast.project.webmessage.pojo.table.UserTable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    private final UserDetailsService userDetailsService;

    private final CheckTokenFilter checkTokenFilter;

//    @Bean
//    PersistentTokenRepository persistentTokenRepository() {
//        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        tokenRepository.setDataSource(dataSource);
//        tokenRepository.setCreateTableOnStartup(true);
//        return tokenRepository;
//    }

    @Bean
    public Converter<User, UserTable> userUserTableConvert() {
        // NOTE: 如果用lambda形式会导致丢失泛型信息
        return new Converter<User, UserTable>() {
            @Override
            public UserTable convert(User user) {
                return UserTable
                        .builder()
                        .username(user.getUsername())
                        .password(passwordEncoder().encode(user.getPassword()))
                        .email(user.getEmail())
                        .build();
            }
        };
    }

    @Bean
    @SuppressWarnings("deprecation")
    PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(ar -> {
            ar.antMatchers(HttpMethod.GET, "/login", "/register", "/logout", "/user/**", "/wx/**")
                    .permitAll()
                    .antMatchers(HttpMethod.POST, "/login", "/register")
                    .permitAll()
                    .antMatchers("/static/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated();
        });

        http.sessionManagement(sm -> {
            // 默认不创建Session
            sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

//        http.rememberMe(rm -> {
//            rm.tokenRepository(persistentTokenRepository())
//                    .tokenValiditySeconds(60 * 60 * 24)
//                    .userDetailsService(userDetailsService);
//        });

        http.csrf(AbstractHttpConfigurer::disable);

        // 禁用请求头缓存
        http.headers(HeadersConfigurer::cacheControl);

        // 过滤器
        http.addFilterBefore(checkTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
