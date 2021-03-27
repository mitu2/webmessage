package com.brageast.project.webmessage.pojo.table;

import com.brageast.project.webmessage.pojo.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserTable implements User {

    @Id
    @Column(nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20, unique = true)
    private String username;
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(name = "avatar")
    private String avatar;
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
     *
     * @see AuthorityTable
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT),
            insertable = false,
            updatable = false
    )
    @JsonIgnore
    @ToString.Exclude
    private List<AuthorityTable> authorities;

}
