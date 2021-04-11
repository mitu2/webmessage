package com.brageast.project.webmessage.pojo.table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_group_ref")
@RequiredArgsConstructor
@NoArgsConstructor
public class GroupRefTable {

    @Id
    @Column(nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, name = "user_id")
    private Long userId;

    @NonNull
    @Column(nullable = false, name = "group_id")
    private Long groupId;

}
