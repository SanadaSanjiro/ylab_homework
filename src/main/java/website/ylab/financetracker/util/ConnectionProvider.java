package website.ylab.financetracker.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * provides DB connections for repositories
 */
public interface ConnectionProvider {
    /**
     * provides DB connections for repositories
     * @return Connection
     * @throws SQLException exception if something goes wrong
     */
    Connection getConnection() throws SQLException;

    /**
     * get schema name from config file
     * @return String schema name
     */
    public String getSchema();

    /**
     * get master changelog file name from config file
     * @return String master changelog file name
     */
    public String getChangelog();

    /**
     * get persistence type from config file
     * @return String persistence type. Could be ram or postgresql
     */
    public String getPersistenceType();
}
