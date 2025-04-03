package website.ylab.financetracker.service;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * ensures interaction of the system with the liquibase
 */
@Service
public class LiquibaseStarter {
    @Value("${spring.liquibase.change-log}")
    private String changelog;
    private final ConnectionProvider connectionProvider;
    Logger logger = LogManager.getLogger(LiquibaseStarter.class);

    @Autowired
    public LiquibaseStarter(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    /**
     * applies liquibase database migrations
     */
    public void applyMigrations() {
        try (Connection connection = connectionProvider.getConnection()) {
            connection.setSchema(connectionProvider.getSchema());
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase =
                    new Liquibase(changelog,
                            new ClassLoaderResourceAccessor(), database);
            liquibase.update();
        } catch (SQLException | LiquibaseException e) {
            logger.error("Liquibase migrations failed. ", e);
        } logger.info("Liquibase migrations completed.");
    }
}