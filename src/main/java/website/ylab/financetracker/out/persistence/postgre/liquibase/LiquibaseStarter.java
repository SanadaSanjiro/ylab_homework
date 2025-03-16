package website.ylab.financetracker.out.persistence.postgre.liquibase;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import website.ylab.financetracker.out.persistence.postgre.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * ensures interaction of the system with the liquibase
 */
public class LiquibaseStarter {
    private final ConnectionProvider connectionProvider;

    public LiquibaseStarter(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    /**
     * applies liquibase database migrations
     * @return String with migration result
     */
    public String applyMigrations() {
        try (Connection connection = connectionProvider.getConnection()) {
            connection.setSchema(connectionProvider.getSchema());
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase =
                    new Liquibase(connectionProvider.getChangelog(), new ClassLoaderResourceAccessor(), database);
            liquibase.update();
        } catch (SQLException | LiquibaseException e) {
            return e.getMessage();
        } return "Migrations applied";
    }
}