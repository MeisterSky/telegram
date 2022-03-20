package com.github.meistersky.telegram.command;

import com.github.meistersky.telegram.repository.entity.UserGroup;
import com.github.meistersky.telegram.service.SendBotMessageService;
import com.github.meistersky.telegram.service.TelegramUserService;
import com.github.meistersky.telegram.service.UserGroupService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

import static com.github.meistersky.telegram.command.CommandUtils.getChatId;
import static com.github.meistersky.telegram.command.CommandUtils.getMessage;
import static org.apache.commons.lang3.StringUtils.SPACE;

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
        Long chatId = getChatId(update);
        String groupTitle = getMessage(update).split(SPACE)[1];
        String[] stringArray = getMessage(update).split(SPACE);
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < stringArray.length; i++) {
            sb.append(stringArray[i]).append(" ");
        }
        String users = sb.toString();
        UserGroup userGroup = userGroupService.save(chatId, groupTitle, users);
    }

    public CreateGroupCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService,
                              UserGroupService userGroupService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.userGroupService = userGroupService;
    }
}
