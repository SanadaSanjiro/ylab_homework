package website.ylab.financetracker;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import website.ylab.financetracker.util.ConnectionProvider;
import website.ylab.financetracker.util.ConnectionProviderImplementation;
import website.ylab.financetracker.util.DbSchemaCreator;
import website.ylab.financetracker.util.LiquibaseStarter;

import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Initialize web-application before run. Creates DB-schema, applies Liquibase migrations
 */
public class ApplicationListener implements ServletContextListener {
    private ServletContext sc = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.sc = sce.getServletContext();
        try {
            // This very important line of code registers the database driver.
            // Without it, you will always get the SQLException. No suitable driver found for jdbc:postgresql
            Class.forName("org.postgresql.Driver");
            ConnectionProvider connectionProvider = new ConnectionProviderImplementation();
            System.out.println("Database starting...");
            DbSchemaCreator schemaCreator = new DbSchemaCreator(connectionProvider);
            System.out.println(schemaCreator.createDbSchema());
            LiquibaseStarter liquibaseStarter = new LiquibaseStarter(connectionProvider);
            System.out.println(liquibaseStarter.applyMigrations());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Close DB connection to avoid memory leaks
        try {
            DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
        } catch (SQLException|SecurityException e) {
            e.printStackTrace();
        }
        this.sc = null;
        System.out.println("Application Stopped");
    }
}