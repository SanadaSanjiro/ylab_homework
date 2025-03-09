package website.ylab.financetracker.commands;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserCommandTest {

    @Test
    void fromString() {
        Optional<UserCommand> optionalCommand = UserCommand.fromString("CHANGE");
        assertTrue(optionalCommand.isPresent());
        assertEquals(UserCommand.CHANGE, optionalCommand.get());

        optionalCommand = UserCommand.fromString("delete");
        assertTrue(optionalCommand.isPresent());
        assertEquals(UserCommand.DELETE, optionalCommand.get());

        optionalCommand = UserCommand.fromString("BadCommand");
        assertFalse(optionalCommand.isPresent());
    }

    @Test
    void runUserCommand() {
        assertEquals("No required arguments", UserCommand.runUserCommand(null));

        String[] args = new String[1];
        assertEquals("No required arguments", UserCommand.runUserCommand(args));

        args = new String[]{"command", "bad command"};
        assertEquals("Bad command", UserCommand.runUserCommand(args));
    }
}