package com.brageast.project.webmessage.config.security;

import com.brageast.project.webmessage.pojo.table.UserTable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CustomizeUserDetails implements UserDetails {

    private final UserTable userTable;
    private final List<? extends GrantedAuthority> authorities;

    public CustomizeUserDetails(UserTable userTable) {
        this.userTable = userTable;
        this.authorities = userTable.getAuthorities();
    }

    public CustomizeUserDetails(UserTable userTable, List<? extends GrantedAuthority> authorities) {
        this.userTable = userTable;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities == null ? Collections.EMPTY_LIST : this.authorities;
    }

    @Override
    public String getPassword() {
        return userTable.getPassword();
    }

    @Override
    public String getUsername() {
        return userTable.getUsername();
    }


    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return Optional.ofNullable(userTable.getEnabled())
                .orElse(false);
    }

    public UserTable getUserTable() {
        return userTable;
    }
}
