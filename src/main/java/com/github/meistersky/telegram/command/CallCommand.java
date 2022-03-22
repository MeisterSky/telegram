package com.github.meistersky.telegram.command;

import com.github.meistersky.telegram.service.SendBotMessageService;
import com.github.meistersky.telegram.service.UserGroupService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CallCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final UserGroupService userGroupService;

    public CallCommand(SendBotMessageService sendBotMessageService, UserGroupService userGroupService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userGroupService = userGroupService;
    }

    @Override
    public void execute(Update update) {

    }
}
