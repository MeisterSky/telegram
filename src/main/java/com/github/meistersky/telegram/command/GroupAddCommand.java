package com.github.meistersky.telegram.command;

import com.github.meistersky.telegram.constants.Constant;
import com.github.meistersky.telegram.service.SendBotMessageService;
import com.github.meistersky.telegram.service.UserGroupService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.meistersky.telegram.command.CommandUtils.getChatId;
import static com.github.meistersky.telegram.command.CommandUtils.getMessage;
import static org.apache.commons.lang3.StringUtils.SPACE;

public class GroupAddCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final UserGroupService userGroupService;

    public final static String CREATE_GROUP_MESSAGE_TITLE =
            "Чтобы добавить пользователей в группу - передай команду, укажи через пробел название группы и "
                    + "перечисли пользователей (тоже через пробел), например:\n"
                    + "/group_add TechSupport @Support @Telegram @PavelDurovs";

    public final static String CREATE_GROUP_MESSAGE_DONE = " ✅ добавил";

    public GroupAddCommand(SendBotMessageService sendBotMessageService, UserGroupService userGroupService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userGroupService = userGroupService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = getChatId(update);
        if (getMessage(update).split(Constant.REGEX_GROUP).length < 2) {
            sendBotMessageService.sendMessage(chatId, CREATE_GROUP_MESSAGE_TITLE);
        } else if (getMessage(update).split(Constant.REGEX_GROUP).length > 1) {
            String[] stringArray = getMessage(update).split(SPACE);
            String groupTitle = stringArray[1];
            StringBuilder sb = new StringBuilder();
            for (int i = 2; i < stringArray.length; i++) {
                sb.append(stringArray[i]).append(" ");
            }
            String users = sb.toString();
            userGroupService.save(chatId, groupTitle, users);
            sendBotMessageService.sendMessage(chatId, groupTitle + CREATE_GROUP_MESSAGE_DONE);
        }
    }
}
