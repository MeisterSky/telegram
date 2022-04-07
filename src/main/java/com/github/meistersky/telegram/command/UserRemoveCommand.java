package com.github.meistersky.telegram.command;

import com.github.meistersky.telegram.constants.Constant;
import com.github.meistersky.telegram.repository.entity.UserGroup;
import com.github.meistersky.telegram.service.SendBotMessageService;
import com.github.meistersky.telegram.service.UserGroupService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

import static com.github.meistersky.telegram.command.CommandUtils.getChatId;
import static com.github.meistersky.telegram.command.CommandUtils.getMessage;
import static org.apache.commons.lang3.StringUtils.SPACE;

/**
 * UserRemove {@link Command}.
 */
public class UserRemoveCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final UserGroupService userGroupService;

    public final static String GROUP_REMOVE_MESSAGE_TITLE =
            "Чтобы удалить пользователей из группы - передай команду, укажи через пробел название группы и "
                    + "перечисли пользователей (тоже через пробел), например:\n"
                    + "/user_remove TechSupport @Support911 @Telegram";

    public final static String GROUP_REMOVE_MESSAGE_DONE = "удалил из группы ✅ ";

    public final static String GROUP_REMOVE_MESSAGE_NOT_FOUND = " ⚠ группа не найдена";

    public UserRemoveCommand(SendBotMessageService sendBotMessageService, UserGroupService userGroupService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userGroupService = userGroupService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = getChatId(update);
        if (getMessage(update).split(Constant.REGEX_GROUP).length < 2) {
            sendBotMessageService.sendMessage(chatId, GROUP_REMOVE_MESSAGE_TITLE);
        } else if (getMessage(update).split(Constant.REGEX_GROUP).length > 1) {
            String[] stringArray = getMessage(update).split(SPACE);
            String groupTitle = stringArray[1];

            if (userGroupService.isExistGroup(chatId, groupTitle)) {
                UserGroup userGroup = userGroupService.findByTitle(chatId, groupTitle);
                List<String> usersList = new ArrayList<>(List.of(userGroup.getUsers().split(" ")));

                for (int i = 2; i < stringArray.length; i++) {
                    usersList.remove(stringArray[i]);
                }

                StringBuilder users = new StringBuilder();
                for (String s : usersList) {
                    users.append(s).append(" ");
                }

                userGroup.setUsers(users.toString());
                userGroupService.save(userGroup);

                sendBotMessageService.sendMessage(chatId, GROUP_REMOVE_MESSAGE_DONE + groupTitle);
            } else {
                sendBotMessageService.sendMessage(chatId, groupTitle + GROUP_REMOVE_MESSAGE_NOT_FOUND);
            }
        }
    }
}
