package com.github.meistersky.telegram.service;

import com.github.meistersky.telegram.bot.TelegramBot;
import com.github.meistersky.telegram.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Implementation of {@link SendBotMessageService} interface.
 */
@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final TelegramBot telegramBot;
    private final TelegramUserService telegramUserService;

    @Autowired
    public SendBotMessageServiceImpl(TelegramBot telegramBot, TelegramUserService telegramUserService) {
        this.telegramBot = telegramBot;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        if (isBlank(message)) return;

        try {
            List<TelegramUser> telegramUserList = telegramUserService.retrieveAllActiveUsers();
            if (telegramUserList.isEmpty()) return;
            for (TelegramUser telegramUser : telegramUserList) {
                if (telegramUser.getChatId().equals(chatId)) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(chatId.toString());
                    sendMessage.enableHtml(true);
                    sendMessage.setText(message);
                    telegramBot.execute(sendMessage);
                }
            }
        } catch (TelegramApiException e) {
            //todo add logging to the project.
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(SendMessage message) {
        try {
            List<TelegramUser> telegramUserList = telegramUserService.retrieveAllActiveUsers();
            if (telegramUserList.isEmpty()) return;
            for (TelegramUser telegramUser : telegramUserList) {
                if (telegramUser.getChatId().toString().equals(message.getChatId())) {
                    telegramBot.execute(message);
                }
            }
        } catch (TelegramApiException e) {
            //todo add logging to the project.
            e.printStackTrace();
        }
    }
}
