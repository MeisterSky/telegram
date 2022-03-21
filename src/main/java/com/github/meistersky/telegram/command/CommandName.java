package com.github.meistersky.telegram.command;

/**
 * Enumeration for {@link Command}'s.
 */
public enum CommandName {

    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    NO("/no"),
    CREATE_GROUP("/create_group"),
    DELETE_GROUP("/delete_group"),
    GET_GROUPS("/get_groups"),
    GROUP_ADD("/group_add"),
    GROUP_REMOVE("/group_remove"),
    CALL_GROUP("/call_group"),
    STAT("/stat");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

}
