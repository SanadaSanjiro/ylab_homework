package website.ylab.financetracker.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbSchemaCreator {
    private final ConnectionProvider connectionProvider;

    public DbSchemaCreator(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public String createDbSchema() {
        System.out.println("Trying to create DB schema");
        String schema = connectionProvider.getSchema();
        String createSchemaSQL ="CREATE SCHEMA IF NOT EXISTS " + schema + ";";
        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement();)
        {
            statement.execute(createSchemaSQL);
        } catch (SQLException e) {
            System.out.println("DbSchemaCreator got SQL Exception in transaction " + e.getMessage());
            return "Error occurred while creating the database";
        } return "Schema created";
    }
}