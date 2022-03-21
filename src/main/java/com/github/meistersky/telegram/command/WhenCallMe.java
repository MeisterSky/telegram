package com.github.meistersky.telegram.command;

import com.github.meistersky.telegram.bot.TelegramBot;
import com.github.meistersky.telegram.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * WhenCallMe {@link Command}.
 */
public class WhenCallMe implements Command {

    private final SendBotMessageService sendBotMessageService;

    String telegramBotName = TelegramBot.BOT_NAME;

    public WhenCallMe(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        update.getMessage().getChatId();
    }
}
