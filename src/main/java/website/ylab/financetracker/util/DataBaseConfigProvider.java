package website.ylab.financetracker.util;

import java.util.Map;

/**
 * Provides static methods for database configuration by config files
 */
public class DataBaseConfigProvider {
    static {
        getConfiguration(new ConfigReaderImplementation());
    }
    // path to database configuration file
    private static final String CONFIG_FILE_PATH = "src/main/resources/db.cfg";
    // separator of parameter names and their values
    private static final String DELIMITER = ": ";
    // a character sequence that marks a line of a file as a comment
    private static final String COMMENT_MARKER = "#";

    // switches system to a different data store methods
    private static String persistenceType;

    // database user name
    private static String dbUser;
    // database user password
    private static String dbPassword;
    // database URL
    private static String dbUrl;
    // database schema name
    private static String dbSchema;

    // path to master changelog file
    private static String lqChangelog;

    /**
     * database user name from configuration file
     * @return String with database username
     */
    public static String getDbUser() {
        return  dbUser;
    }

    /**
     * database user password from configuration file
     * @return String database user password
     */
    public static String getDbPassword() {
        return dbPassword;
    }

    /**
     * database URL from configuration file
     * @return String database URL
     */
    public static String getDbUrl() {
        return dbUrl;
    }

    /**
     * database schema name from configuration file
     * @return String database schema name
     */
    public static String getDbSchema() {
        return dbSchema;
    }

    /**
     * path to a liquibase master changelog file
     * @return String path to a liquibase master changelog file
     */
    public static String getLqChangelog() {
        return lqChangelog;
    }

    /**
     * data store method (ram, postgres e t.c.)
     * @return String data store method
     */
    public static String getPersistenceType() {
        return persistenceType;
    }

    private static void getConfiguration(ConfigReader confreader) {
        Map<String, String> properties = confreader.read(CONFIG_FILE_PATH, DELIMITER, COMMENT_MARKER);
        persistenceType = properties.get("persistence");
        dbUser = properties.get("username");
        dbPassword = properties.get("password");
        dbUrl = properties.get("url");
        dbSchema = properties.get("schema");
        lqChangelog = properties.get("changelog");
    }
}