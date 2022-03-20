package com.github.meistersky.telegram.command;

import com.github.meistersky.telegram.service.SendBotMessageService;
import com.github.meistersky.telegram.service.TelegramUserService;
import com.github.meistersky.telegram.service.UserGroupService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * CreateGroup {@link Command}.
 */
public class CreateGroupCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final UserGroupService userGroupService;

    public final static String CREATE_GROUP_MESSAGE = "Укажи название группы";

    @Override
    public void execute(Update update) {

    }

    public CreateGroupCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService
    , UserGroupService userGroupService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.userGroupService = userGroupService;
    }
}
