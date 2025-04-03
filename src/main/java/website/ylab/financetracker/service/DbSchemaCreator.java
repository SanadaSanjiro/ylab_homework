package website.ylab.financetracker.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class DbSchemaCreator {
    private final ConnectionProvider connectionProvider;
    Logger logger = LogManager.getLogger(DbSchemaCreator.class);

    public DbSchemaCreator(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void createDbSchema() {
        logger.info("Trying to create DB schema");
        String schema = connectionProvider.getSchema();
        String createSchemaSQL ="CREATE SCHEMA IF NOT EXISTS " + schema + ";";
        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement())
        {
            statement.execute(createSchemaSQL);
            logger.info("Schema created");
        } catch (SQLException e) {
            logger.error("DbSchemaCreator got an exception: {}", e.getMessage());
        }
    }
}