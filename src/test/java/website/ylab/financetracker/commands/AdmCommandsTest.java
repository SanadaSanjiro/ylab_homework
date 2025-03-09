package website.ylab.financetracker.commands;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AdmCommandsTest {

    @Test
    void fromString() {
        Optional<AdmCommands> optionalCommand = AdmCommands.fromString("USERS");
        assertTrue(optionalCommand.isPresent());
        assertEquals(AdmCommands.USERS, optionalCommand.get());

        optionalCommand = AdmCommands.fromString("block");
        assertTrue(optionalCommand.isPresent());
        assertEquals(AdmCommands.BLOCK, optionalCommand.get());

        optionalCommand = AdmCommands.fromString("BadCommand");
        assertFalse(optionalCommand.isPresent());
    }

    @Test
    void runUserCommand() {
        assertEquals("No required arguments", BudgetCommand.runUserCommand(null));

        String[] args = new String[1];
        assertEquals("No required arguments", BudgetCommand.runUserCommand(args));

        args = new String[]{"command", "bad command"};
        assertEquals("Bad command", BudgetCommand.runUserCommand(args));
    }
}