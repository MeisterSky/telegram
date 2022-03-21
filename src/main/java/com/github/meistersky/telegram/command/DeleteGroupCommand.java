package com.github.meistersky.telegram.command;

import com.github.meistersky.telegram.constants.Constant;
import com.github.meistersky.telegram.repository.entity.UserGroup;
import com.github.meistersky.telegram.service.SendBotMessageService;
import com.github.meistersky.telegram.service.UserGroupService;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.meistersky.telegram.command.CommandUtils.getChatId;
import static com.github.meistersky.telegram.command.CommandUtils.getMessage;
import static java.lang.String.format;

/**
 * Delete Group {@link Command}.
 */
public class DeleteGroupCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final UserGroupService userGroupService;

    public final static String DELETE_GROUP_MESSAGE_DONE = " ✅ группа удалена";

    public final static String DELETE_GROUP_MESSAGE_NOT_FOUND = " ⚠ группа не найдена";

    public final static String GROUP_MESSAGE_EMPTY = """
            Пока нет никаких групп. Чтобы добавить группу, напиши /create_group и укажи название группы через пробел, например:
            /create_group TechSupport
            для создания группы с названием 'TechSupport'""";

    public final static String GROUP_MESSAGE_MANUAL = """
            Чтобы удалить группу - передай команду вместе с названием группы, например:\s
            /delete_group %s\s
                        
            Вот список всех групп для этого чата:\s
            """;


    public DeleteGroupCommand(SendBotMessageService sendBotMessageService, UserGroupService groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userGroupService = groupSubService;
    }

    @Override
    public void execute(Update update) {
        if (getMessage(update).split(Constant.REGEX_GROUP).length < 2) {
            sendGroupIdList(getChatId(update));
            return;
        }
        String[] message = getMessage(update).split(Constant.REGEX_GROUP);
        String groupTitle = "";
        for (int i = 1; i < message.length; i++) {
            if (!message[i].isEmpty()) {
                groupTitle = message[i];
                break;
            }
        }
        Long chatId = getChatId(update);
        UserGroup userGroup = userGroupService.findByTitle(chatId, groupTitle);
        if (userGroup != null) {
            userGroupService.delete(userGroup);
            sendBotMessageService.sendMessage(chatId, userGroup.getTitle() + DELETE_GROUP_MESSAGE_DONE);
        } else {
            sendBotMessageService.sendMessage(chatId, groupTitle + DELETE_GROUP_MESSAGE_NOT_FOUND);
        }
    }

    private void sendGroupIdList(Long chatId) {
        String message;
        List<UserGroup> userGroups = userGroupService.findAllByChatId(chatId);
        if (CollectionUtils.isEmpty(userGroups)) {
            message = GROUP_MESSAGE_EMPTY;
        } else {
            String userGroupData = userGroups.stream()
                    .map(group -> format("%s\n", group.getTitle()))
                    .collect(Collectors.joining());

            message = String.format(GROUP_MESSAGE_MANUAL, userGroups.get(0).getTitle()) + userGroupData;
        }

        sendBotMessageService.sendMessage(chatId, message);
    }
}
