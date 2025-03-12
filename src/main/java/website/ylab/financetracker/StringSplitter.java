package website.ylab.financetracker;

import java.util.regex.Pattern;

/**
 * The class is used to split the command line into parts.
 */
public class StringSplitter {
    private static final Pattern SPACE = Pattern.compile(" ");

    public static String[] SplitString(String input) {
        return SPACE.split(input);
    }
}