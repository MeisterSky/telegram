package com.github.meistersky.telegram.command;

import org.junit.jupiter.api.DisplayName;

import static com.github.meistersky.telegram.command.CommandName.HELP;
import static com.github.meistersky.telegram.command.HelpCommand.HELP_MESSAGE;

@DisplayName("Unit-level testing for HelpCommand")
public class HelpCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return HELP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return HELP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new HelpCommand(sendBotMessageService);
    }
}

