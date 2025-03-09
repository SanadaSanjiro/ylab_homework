package website.ylab.financetracker.commands;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void fromString() {
        Optional<Command> optionalCommand = Command.fromString("LOGIN");
        assertTrue(optionalCommand.isPresent());
        assertEquals(Command.LOGIN, optionalCommand.get());

        optionalCommand = Command.fromString("login");
        assertTrue(optionalCommand.isPresent());
        assertEquals(Command.LOGIN, optionalCommand.get());

        optionalCommand = Command.fromString("BadCommand");
        assertFalse(optionalCommand.isPresent());
    }

    @Test
    void runCommand() {
        assertEquals("No command", Command.runCommand(null));

        String[] args = new String[0];
        assertEquals("No command", Command.runCommand(args));

        args = new String[]{"bad command"};
        assertEquals("Bad command", Command.runCommand(args));
    }
}