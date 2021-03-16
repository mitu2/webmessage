package com.brageast.project.webmessage.entity.table;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserTable implements UserDetails {

    @Id
    @Column(nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 20, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
//    @Builder.Default
//    @Column(name = "account_non_expired", nullable = false)
//    private boolean accountNonExpired = true;
//    @Builder.Default
//    @Column(name = "account_non_locked", nullable = false)
//    private boolean accountNonLocked = true;
//    @Builder.Default
//    @Column(name = "credentials_non_expired", nullable = false)
//    private boolean credentialsNonExpired = true;
    @Builder.Default
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;
    @CreationTimestamp
    @Column(name = "create_time")
    private Date createTime;
    @UpdateTimestamp
    @Column(name = "update_time")
    private Date updateTime;
    /**
     * 用户相关角色权限列表
     * @see AuthorityTable
     */
    @OneToMany
    @ToString.Exclude
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private transient List<AuthorityTable> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return enabled != null ? enabled : false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled != null ? enabled : false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled != null ? enabled : false;
    }

    @Override
    public boolean isEnabled() {
        return enabled != null ? enabled : false;
    }
}
