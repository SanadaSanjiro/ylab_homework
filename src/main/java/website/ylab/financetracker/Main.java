package website.ylab.financetracker;

import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.service.auth.UserService;
import website.ylab.financetracker.in.dto.auth.UserResponse;
import website.ylab.financetracker.util.*;


public class Main {
    public static void main(String[] args) {
        ConnectionProvider connectionProvider = new ConnectionProviderImplementation();
        System.out.println("Выполняется подготовка базы данных. Пожалуйста, подождите.");
        DbSchemaCreator schemaCreator = new DbSchemaCreator(connectionProvider);
        schemaCreator.createDbSchema();
        LiquibaseStarter liquibaseStarter = new LiquibaseStarter(connectionProvider);
        liquibaseStarter.applyMigrations();

        System.out.println("Enter your commands here");
        UserService userService = ServiceProvider.getUserService();
        UserResponse currentUser = userService.getResponseById(1L);
        System.out.println(currentUser);
    }
}