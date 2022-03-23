package com.github.meistersky.telegram.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Service for sending messages via telegram-bot.
 */
public interface SendBotMessageService {

    /**
     * Send message via telegram bot.
     *
     * @param chatId provided chatId in which messages would be sent.
     * @param message provided message to be sent.
     */
    void sendMessage(Long chatId, String message);

    /**
     * Send messages via telegram bot.
     *
     * @param message provided finished message from the provided messages to send.
     */
    void sendMessage(SendMessage message);
}
