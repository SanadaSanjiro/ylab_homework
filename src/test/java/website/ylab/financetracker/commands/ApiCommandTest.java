package website.ylab.financetracker.commands;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ApiCommandTest {

    @Test
    void fromString() {
        Optional<ApiCommand> optionalCommand = ApiCommand.fromString("EXCEEDANCE");
        assertTrue(optionalCommand.isPresent());
        assertEquals(ApiCommand.EXCEEDANCE, optionalCommand.get());

        optionalCommand = ApiCommand.fromString("emails");
        assertTrue(optionalCommand.isPresent());
        assertEquals(ApiCommand.EMAILS, optionalCommand.get());

        optionalCommand = ApiCommand.fromString("BadCommand");
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