package website.ylab.financetracker.commands;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TransactionCommandTest {

    @Test
    void fromString() {
        Optional<TransactionCommand> optionalCommand = TransactionCommand.fromString("CREATE");
        assertTrue(optionalCommand.isPresent());
        assertEquals(TransactionCommand.CREATE, optionalCommand.get());

        optionalCommand = TransactionCommand.fromString("update");
        assertTrue(optionalCommand.isPresent());
        assertEquals(TransactionCommand.UPDATE, optionalCommand.get());

        optionalCommand = TransactionCommand.fromString("BadCommand");
        assertFalse(optionalCommand.isPresent());
    }

    @Test
    void runUserCommand() {
        assertEquals("No required arguments", TransactionCommand.runUserCommand(null));

        String[] args = new String[1];
        assertEquals("No required arguments", TransactionCommand.runUserCommand(args));

        args = new String[]{"command", "bad command"};
        assertEquals("Bad command", TransactionCommand.runUserCommand(args));
    }
}