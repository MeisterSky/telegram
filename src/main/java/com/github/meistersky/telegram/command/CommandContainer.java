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
                .put(CREATE_GROUP.getCommandName(), new CreateGroupCommand(sendBotMessageService, userGroupService))
                .put(DELETE_GROUP.getCommandName(), new DeleteGroupCommand(sendBotMessageService, userGroupService))
                .put(GROUP_ADD.getCommandName(), new GroupAddCommand(sendBotMessageService, userGroupService))
                .put(GROUP_REMOVE.getCommandName(), new GroupRemoveCommand(sendBotMessageService, userGroupService))
                .put(GET_GROUPS.getCommandName(), new GetGroupsCommand(sendBotMessageService, userGroupService))
                .put(CALL_GROUP.getCommandName(), new CallGroupCommand(sendBotMessageService, userGroupService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command findCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

}