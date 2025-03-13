package website.ylab.financetracker.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Provides a method for parsing configuration files whose lines look like this:
 * parameter-delimiter-value
 * Lines that begin with the character sequence specified by the commentMarker parameter
 * are considered comments and will be ignored.
 */
public class ConfigReaderImplementation implements ConfigReader {

    /**
     * parse configuration files
     * @param path path to configuration file
     * @param delimiter a sequence of characters that separates keys from values
     * @param commentMarker a character sequence that marks a line of a file as a comment
     *   when placed at the beginning of a line
     * @return Map<String, String> consisting of keys and their corresponding parameters.
     * Parameter names will be stored in low case, values - as is
     */
    @Override
    public Map<String, String> read(String path, String delimiter, String commentMarker) {
        Map<String, String> map = new HashMap<>();
        try {
            String CHARSET = "cp1251";
            List<String> lines = Files.readAllLines(Paths.get(path),
                    Charset.forName(CHARSET));
            map = lines.stream()
                    .filter(line->!line.startsWith(commentMarker))
                    .collect(Collectors.toMap(line->line.split(delimiter)[0].toLowerCase(),
                            line->line.split(delimiter)[1]));
            System.out.println(map);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return map;
    }
}