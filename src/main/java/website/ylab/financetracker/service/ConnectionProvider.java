package website.ylab.financetracker.service;

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
    String getSchema();
}
