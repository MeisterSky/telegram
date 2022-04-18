package com.github.meistersky.telegram.command;

import com.github.meistersky.telegram.constants.Constant;
import com.github.meistersky.telegram.service.SendBotMessageService;
import com.github.meistersky.telegram.service.UserGroupService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.meistersky.telegram.command.CommandUtils.getChatId;
import static com.github.meistersky.telegram.command.CommandUtils.getMessage;

/**
 * GroupCreate {@link Command}.
 */
public class GroupCreateCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final UserGroupService userGroupService;

    public final static String CREATE_GROUP_MESSAGE_TITLE = """
            Чтобы создать группу - передай команду и укажи название группы через пробел в одно слово, например:
            /group_create TechSupport
             для создания группы с названием 'TechSupport'""";

    public final static String CREATE_GROUP_MESSAGE_DONE = " ✅ группа создана";

    public final static String CREATE_GROUP_MESSAGE_IS_PRESENT = " ⚠ такая группа уже есть";

    @Override
    public void execute(Update update) {
        Long chatId = getChatId(update);
        if (getMessage(update).split(Constant.REGEX_GROUP).length < 2) {
            sendBotMessageService.sendMessage(chatId, CREATE_GROUP_MESSAGE_TITLE);
        } else if (getMessage(update).split(Constant.REGEX_GROUP).length > 1) {
            String[] stringArray = getMessage(update).split(Constant.REGEX_GROUP);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < stringArray.length; i++) {
                stringBuilder.append(stringArray[i]);
            }
            String groupTitle = stringBuilder.toString();
            if (userGroupService.isExistGroup(chatId, groupTitle)) {
                sendBotMessageService.sendMessage(chatId, groupTitle + CREATE_GROUP_MESSAGE_IS_PRESENT);
            } else {
                String users = "";
                userGroupService.save(chatId, groupTitle, users);
                sendBotMessageService.sendMessage(chatId, groupTitle + CREATE_GROUP_MESSAGE_DONE);
            }
        }
    }

    public GroupCreateCommand(SendBotMessageService sendBotMessageService, UserGroupService userGroupService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userGroupService = userGroupService;
    }
}
