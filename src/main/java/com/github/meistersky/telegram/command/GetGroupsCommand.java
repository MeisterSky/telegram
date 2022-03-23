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
 * GetGroups {@link Command}.
 */
public class GetGroupsCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final UserGroupService userGroupService;

    public final static String GROUP_MESSAGE_EMPTY = """
            Пока нет никаких групп. Чтобы добавить группу, напиши /group_create и укажи название группы через пробел, например:
            /group_create TechSupport
             для создания группы с названием 'TechSupport'""";

    public final static String GROUP_MESSAGE_ALL_GROUPS = "Вот список всех групп и их пользователей для этого чата:\n\n%s";

    public GetGroupsCommand(SendBotMessageService sendBotMessageService, UserGroupService userGroupService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userGroupService = userGroupService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = getChatId(update);
        if (getMessage(update).split(Constant.REGEX_GROUP).length < 2) {
            String message;
            List<UserGroup> userGroups = userGroupService.findAllByChatId(chatId);
            if (CollectionUtils.isEmpty(userGroups)) {
                message = GROUP_MESSAGE_EMPTY;
            } else {
                String userGroupData = userGroups.stream()
                        .map(group -> format("<b>%s</b>:\n%s\n\n", group.getTitle(), group.getUsers().replace('@', ' ')))
                        .collect(Collectors.joining());

                message = String.format(GROUP_MESSAGE_ALL_GROUPS, userGroupData);
            }

            sendBotMessageService.sendMessage(chatId, message);
        }
    }
}
