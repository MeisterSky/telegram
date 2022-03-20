package com.github.meistersky.telegram.repository.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Telegram User entity.
 */
@Data
@Entity
@Table(name = "tg_user")
public class TelegramUser {

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "active")
    private boolean active;
}