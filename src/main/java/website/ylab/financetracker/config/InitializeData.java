package website.ylab.financetracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import website.ylab.financetracker.service.AdminCreator;
import website.ylab.financetracker.service.DbSchemaCreator;
import website.ylab.financetracker.service.LiquibaseStarter;

/**
 * Initialize database. Creates schema, applies liquibase migrations and adds Admin user at application startup
 */
@Component
public class InitializeData {
    private final DbSchemaCreator dbSchemaCreator;
    private final LiquibaseStarter liquibaseStarter;
    private final AdminCreator adminCreator;

    @Autowired
    public InitializeData(DbSchemaCreator dbSchemaCreator,
                          LiquibaseStarter liquibaseStarter,
                          AdminCreator adminCreator)
    {
        this.dbSchemaCreator = dbSchemaCreator;
        this.liquibaseStarter = liquibaseStarter;
        this.adminCreator = adminCreator;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        dbSchemaCreator.createDbSchema();
        liquibaseStarter.applyMigrations();
        adminCreator.createAdmin();
    }
}