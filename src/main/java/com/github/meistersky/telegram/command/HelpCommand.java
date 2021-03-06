package com.github.meistersky.telegram.command;

import com.github.meistersky.telegram.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.meistersky.telegram.command.CommandName.*;

/**
 * Help {@link Command}.
 */
public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = "✨<b>Доступные команды</b>✨\n\n"

            + "<b>Начать\\закончить работу с ботом:</b>\n"
            + START.getCommandName() + " - начать работу со мной\n"
            + STOP.getCommandName() + " - приостановить работу со мной\n\n"
            + "<b>Функциональные команды:</b>\n"
            + GROUP_CREATE.getCommandName() + " - создать группу\n"
            + GROUP_DELETE.getCommandName() + " - удалить группу\n"
            + USER_ADD.getCommandName() + " - добавить пользователя в группу\n"
            + USER_REMOVE.getCommandName() + " - удалить пользователя из группы\n"
            + GET_GROUPS.getCommandName() + " - получить список групп и их пользователей\n"
            + CALL.getCommandName() + " - вызвать группу\n\n"
            + "<b>Вспомогательные команды:</b>\n"
            + STAT.getCommandName() + " - получить статистику пользователей\n"
            + HELP.getCommandName() + " - получить помощь в работе со мной\n\n";

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), HELP_MESSAGE);
    }
}
