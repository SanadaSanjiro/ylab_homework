package website.ylab.financetracker;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.util.StringSplitter;

import static org.junit.jupiter.api.Assertions.*;

class StringSplitterTest {

    @Test
    void splitString() {
        String[] strings = StringSplitter.SplitString("Command arg1 arg2");
        assertEquals("Command", strings[0]);
        assertEquals("arg1", strings[1]);
        assertEquals("arg2", strings[2]);
    }
}