package com.github.meistersky.telegram.bot;

import com.github.meistersky.telegram.command.CommandContainer;
import com.github.meistersky.telegram.constants.Constant;
import com.github.meistersky.telegram.service.SendBotMessageServiceImpl;
import com.github.meistersky.telegram.service.TelegramUserService;
import com.github.meistersky.telegram.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.meistersky.telegram.command.CommandName.NO;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private final CommandContainer commandContainer;

    @Autowired
    public TelegramBot(TelegramUserService telegramUserService, UserGroupService userGroupService) {
        this.commandContainer =
                new CommandContainer(new SendBotMessageServiceImpl(this),
                        telegramUserService, userGroupService);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(Constant.REGEX_GROUP)[0].toLowerCase();
                commandContainer.findCommand(commandIdentifier).execute(update);

            } else {
                commandContainer.findCommand(NO.getCommandName()).execute(update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}