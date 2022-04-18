package com.github.meistersky.telegram.service;

import com.github.meistersky.telegram.bot.TelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@DisplayName("Unit-level testing for SendBotMessageService")
public class SendBotMessageServiceTest {

    private SendBotMessageService sendBotMessageService;
    private TelegramBot javarushBot;
    private TelegramUserService telegramUserService;

    @BeforeEach
    public void init() {
        javarushBot = Mockito.mock(TelegramBot.class);
        telegramUserService = Mockito.mock(TelegramUserService.class);
        sendBotMessageService = new SendBotMessageServiceImpl(javarushBot, telegramUserService);
    }

    @Test
    public void shouldProperlySendMessage() throws TelegramApiException {
        //given
        Long chatId = 123L;
        String message = "test_message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableHtml(true);

        //when
        sendBotMessageService.sendMessage(chatId, message);

        //then
        Mockito.verify(javarushBot).execute(sendMessage);
    }
}
