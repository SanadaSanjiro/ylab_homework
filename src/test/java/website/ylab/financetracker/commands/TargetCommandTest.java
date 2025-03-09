package website.ylab.financetracker.commands;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TargetCommandTest {

    @Test
    void fromString() {
        Optional<TargetCommand> optionalCommand = TargetCommand.fromString("SET");
        assertTrue(optionalCommand.isPresent());
        assertEquals(TargetCommand.SET, optionalCommand.get());

        optionalCommand = TargetCommand.fromString("show");
        assertTrue(optionalCommand.isPresent());
        assertEquals(TargetCommand.SHOW, optionalCommand.get());

        optionalCommand = TargetCommand.fromString("BadCommand");
        assertFalse(optionalCommand.isPresent());
    }

    @Test
    void runUserCommand() {
        assertEquals("No required arguments", TargetCommand.runUserCommand(null));

        String[] args = new String[1];
        assertEquals("No required arguments", TargetCommand.runUserCommand(args));

        args = new String[]{"command", "bad command"};
        assertEquals("Bad command", TargetCommand.runUserCommand(args));
    }
}