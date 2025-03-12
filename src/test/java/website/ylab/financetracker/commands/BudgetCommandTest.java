package website.ylab.financetracker.commands;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BudgetCommandTest {

    @Test
    void fromString() {
        Optional<BudgetCommand> optionalCommand = BudgetCommand.fromString("SET");
        assertTrue(optionalCommand.isPresent());
        assertEquals(BudgetCommand.SET, optionalCommand.get());

        optionalCommand = BudgetCommand.fromString("show");
        assertTrue(optionalCommand.isPresent());
        assertEquals(BudgetCommand.SHOW, optionalCommand.get());

        optionalCommand = BudgetCommand.fromString("BadCommand");
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