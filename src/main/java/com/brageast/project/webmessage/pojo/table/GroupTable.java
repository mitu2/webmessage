package com.brageast.project.webmessage.pojo.table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_group")
@RequiredArgsConstructor
@NoArgsConstructor
public class GroupTable {

    @Id
    @Column(nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, length = 15, name = "name")
    private String name;

    @NonNull
    @Column(nullable = false, name = "group_describe", length = 80)
    private String describe;

}
