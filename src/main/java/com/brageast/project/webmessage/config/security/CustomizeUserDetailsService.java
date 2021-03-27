package com.brageast.project.webmessage.config.security;

import com.brageast.project.webmessage.db.repository.AuthorityRepository;
import com.brageast.project.webmessage.db.repository.UserRepository;
import com.brageast.project.webmessage.pojo.table.UserTable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomizeUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserTable userTable = userRepository.findByUsername(username);
        if (userTable == null) {
            throw new UsernameNotFoundException("未找到用户" + username);
        }
        return new CustomizeUserDetails(userTable, authorityRepository.findAllByUserId(userTable.getId()));
    }
}
