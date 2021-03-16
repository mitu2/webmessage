package com.brageast.project.webmessage.entity.table;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_authority")
public class AuthorityTable implements GrantedAuthority {

    @Id
    @Column(name = "authority_id", nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id", updatable = false, insertable = false, nullable = false)
    private String userId;
    @Column(nullable = false)
    private String authority;


}
