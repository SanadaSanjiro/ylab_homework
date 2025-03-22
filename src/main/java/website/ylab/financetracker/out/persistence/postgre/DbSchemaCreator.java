package website.ylab.financetracker.out.persistence.postgre;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbSchemaCreator {
    private final ConnectionProvider connectionProvider;

    public DbSchemaCreator(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public String createDbSchema() {
        String schema = connectionProvider.getSchema();
        String createSchemaSQL ="CREATE SCHEMA IF NOT EXISTS " + schema + ";";
        try (Connection connection = connectionProvider.getConnection())
        {
            Statement statement = connection.createStatement();
            statement.execute(createSchemaSQL);
        } catch (SQLException e) {
            System.out.println("Got SQL Exception in transaction " + e.getMessage());
            return "Error occurred while creating the database";
        } return "Schema created";
    }
}