package com.github.meistersky.telegram.command;

/**
 * Enumeration for {@link Command}'s.
 */
public enum CommandName {

    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    NO("/no"),
    GROUP_CREATE("/group_create"),
    GROUP_DELETE("/group_delete"),
    GET_GROUPS("/get_groups"),
    USER_ADD("/user_add"),
    USER_REMOVE("/user_remove"),
    CALL("/call"),
    STAT("/stat");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

}
