package website.ylab.financetracker.util;

import java.util.Map;

/**
 * Reads config data from files, return it as Map with key and values
 */
public interface ConfigReader {
    /**
     * Reads config data from files, return it as Map with key and values
     * @param path path to configuration file
     * @param delimiter a sequence of characters that separates keys from values
     * @param commentMarker a character sequence that marks a line of a file as a comment
     *   when placed at the beginning of a line
     * @return Map<String, String> where keys is a names of parameter
     */
    Map<String, String> read(String path, String delimiter, String commentMarker);
}
