package com.github.meistersky.telegram.command;

import com.github.meistersky.telegram.service.SendBotMessageService;
import com.github.meistersky.telegram.service.TelegramUserService;
import com.github.meistersky.telegram.service.UserGroupService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.meistersky.telegram.command.CommandUtils.getChatId;
import static com.github.meistersky.telegram.command.CommandUtils.getMessage;
import static org.apache.commons.lang3.StringUtils.SPACE;

/**
 * CreateGroup {@link Command}.
 */
public class CreateGroupCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final UserGroupService userGroupService;

    public final static String CREATE_GROUP_MESSAGE_TITLE = "Укажи название группы через пробел в одно слово\n"
            + "Например: /create_group Support для создания группы с названием 'Support'";
    public final static String CREATE_GROUP_MESSAGE_DONE = " ✅ группа создана";
    public final static String CREATE_GROUP_MESSAGE_IS_PRESENT = " ⚠ такая группа уже есть";

    @Override
    public void execute(Update update) {
        Long chatId = getChatId(update);
        if (getMessage(update).split(SPACE).length < 2) {
            sendBotMessageService.sendMessage(chatId, CREATE_GROUP_MESSAGE_TITLE);
        } else if (getMessage(update).split(SPACE).length > 1) {
            String[] stringArray = getMessage(update).split(SPACE);
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < stringArray.length; i++) {
                sb.append(stringArray[i]);
            }
            String groupTitle = sb.toString();
            if (userGroupService.isExist(chatId, groupTitle)) {
                sendBotMessageService.sendMessage(chatId, groupTitle + CREATE_GROUP_MESSAGE_IS_PRESENT);
            } else {
                String users = "";
                userGroupService.save(chatId, groupTitle, users);
                sendBotMessageService.sendMessage(chatId, groupTitle + CREATE_GROUP_MESSAGE_DONE);
            }
        }
    }

    public CreateGroupCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService,
                              UserGroupService userGroupService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userGroupService = userGroupService;
    }
}
