package com.github.meistersky.telegram.command;

import com.github.meistersky.telegram.service.SendBotMessageService;
import com.github.meistersky.telegram.service.TelegramUserService;
import com.github.meistersky.telegram.service.UserGroupService;
import com.google.common.collect.ImmutableMap;

import static com.github.meistersky.telegram.command.CommandName.*;

/**
 * Container of the {@link Command}s, which are using for handling telegram commands.
 */
public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService,
    UserGroupService userGroupService) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService))
                .put(GROUP_CREATE.getCommandName(), new GroupCreateCommand(sendBotMessageService, userGroupService))
                .put(GROUP_DELETE.getCommandName(), new GroupDeleteCommand(sendBotMessageService, userGroupService))
                .put(USER_ADD.getCommandName(), new UserAddCommand(sendBotMessageService, userGroupService))
                .put(USER_REMOVE.getCommandName(), new UserRemoveCommand(sendBotMessageService, userGroupService))
                .put(GET_GROUPS.getCommandName(), new GetGroupsCommand(sendBotMessageService, userGroupService))
                .put(CALL.getCommandName(), new CallCommand(sendBotMessageService, userGroupService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command findCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

}