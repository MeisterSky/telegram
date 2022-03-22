package com.github.meistersky.telegram.command;

import com.github.meistersky.telegram.constants.Constant;
import com.github.meistersky.telegram.repository.entity.UserGroup;
import com.github.meistersky.telegram.service.SendBotMessageService;
import com.github.meistersky.telegram.service.UserGroupService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.meistersky.telegram.command.CommandUtils.getChatId;
import static com.github.meistersky.telegram.command.CommandUtils.getMessage;
import static org.apache.commons.lang3.StringUtils.SPACE;

/**
 * UserAdd {@link Command}.
 */
public class UserAddCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final UserGroupService userGroupService;

    public final static String GROUP_ADD_MESSAGE_TITLE =
            "Чтобы добавить пользователей в группу - передай команду, укажи через пробел название группы и "
                    + "перечисли пользователей (тоже через пробел), например:\n"
                    + "/user_add TechSupport @Support911 @Telegram @PavelDurovs";

    public final static String GROUP_ADD_MESSAGE_DONE = "добавил в группу ✅ ";

    public final static String GROUP_ADD_MESSAGE_NOT_FOUND = " ⚠ группа не найдена";

    public UserAddCommand(SendBotMessageService sendBotMessageService, UserGroupService userGroupService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userGroupService = userGroupService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = getChatId(update);
        if (getMessage(update).split(Constant.REGEX_GROUP).length < 2) {
            sendBotMessageService.sendMessage(chatId, GROUP_ADD_MESSAGE_TITLE);
        } else if (getMessage(update).split(Constant.REGEX_GROUP).length > 1) {
            String[] stringArray = getMessage(update).split(SPACE);
            String groupTitle = stringArray[1];
            if (userGroupService.isExistGroup(chatId, groupTitle)) {
                StringBuilder newUsers = new StringBuilder();
                for (int i = 2; i < stringArray.length; i++) {
                    newUsers.append(stringArray[i]).append(" ");
                }
                UserGroup userGroup = userGroupService.findByTitle(chatId, groupTitle);
                String users = userGroup.getUsers() + newUsers;
                userGroup.setUsers(users);
                userGroupService.save(userGroup);
                sendBotMessageService.sendMessage(chatId, GROUP_ADD_MESSAGE_DONE + groupTitle);
            } else {
                sendBotMessageService.sendMessage(chatId, groupTitle + GROUP_ADD_MESSAGE_NOT_FOUND);
            }
        }
    }
}
