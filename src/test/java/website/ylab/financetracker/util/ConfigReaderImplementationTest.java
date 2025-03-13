package website.ylab.financetracker.util;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConfigReaderImplementationTest {
    String configFilePath = "src/test/java/website/ylab/financetracker/resources/test.cfg";

    /**
     * For testing, a test.cfg file with the following contents should be placed
     * in the src/test/java/website/ylab/financetracker/resources/ folder:
     * # First comment
     * CommentedParameter: valueThatShouldNotExist
     * Parameter1: value1
     * parameter2: value2
     */
    @Test
    void read() {
        ConfigReaderImplementation cri = new ConfigReaderImplementation();
        Map<String, String > result = cri.read(configFilePath, ": ", "#");
        assertTrue(Files.exists(Paths.get(configFilePath)));
        assertFalse(result.containsKey("commentedparameter"));
        assertEquals("value1", result.get("parameter1"));
        assertEquals("value2", result.get("parameter2"));
    }
}