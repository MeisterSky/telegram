package com.github.meistersky.telegram.repository.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * User Group entity.
 */
@Data
@Entity
@Table(name = "user_group")
public class UserGroup {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "title")
    private String title;

    @Column(name = "users")
    private String users;
}
