package website.ylab.financetracker.commands;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StatCommandTest {

    @Test
    void fromString() {
        Optional<StatCommand> optionalCommand = StatCommand.fromString("BALANCE");
        assertTrue(optionalCommand.isPresent());
        assertEquals(StatCommand.BALANCE, optionalCommand.get());

        optionalCommand = StatCommand.fromString("report");
        assertTrue(optionalCommand.isPresent());
        assertEquals(StatCommand.REPORT, optionalCommand.get());

        optionalCommand = StatCommand.fromString("BadCommand");
        assertFalse(optionalCommand.isPresent());
    }

    @Test
    void runUserCommand() {
        assertEquals("No required arguments", StatCommand.runUserCommand(null));

        String[] args = new String[1];
        assertEquals("No required arguments", StatCommand.runUserCommand(args));

        args = new String[]{"command", "bad command"};
        assertEquals("Bad command", StatCommand.runUserCommand(args));
    }
}