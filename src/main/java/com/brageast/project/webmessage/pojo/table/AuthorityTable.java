package com.brageast.project.webmessage.pojo.table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_authority")
@NoArgsConstructor
@RequiredArgsConstructor
public class AuthorityTable implements GrantedAuthority {

    @Id
    @Column(name = "authority_id", nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @NonNull
    @Column(nullable = false)
    private String authority;

}
