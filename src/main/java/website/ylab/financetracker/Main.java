package website.ylab.financetracker;

import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.auth.UserAuthService;
import website.ylab.financetracker.commands.Command;
import website.ylab.financetracker.out.persistence.postgre.ConnectionProvider;
import website.ylab.financetracker.out.persistence.postgre.ConnectionProviderImplementation;
import website.ylab.financetracker.out.persistence.postgre.DbSchemaCreator;
import website.ylab.financetracker.out.persistence.postgre.liquibase.LiquibaseStarter;
import website.ylab.financetracker.util.DataBaseConfigProvider;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConnectionProvider connectionProvider = new ConnectionProviderImplementation();
        if (DataBaseConfigProvider.getPersistenceType().equalsIgnoreCase("postgresql")) {
            System.out.println("Выполняется подготовка базы данных. Пожалуйста, подождите.");
            DbSchemaCreator schemaCreator = new DbSchemaCreator(connectionProvider);
            schemaCreator.createDbSchema();
            LiquibaseStarter liquibaseStarter = new LiquibaseStarter(connectionProvider);
            liquibaseStarter.applyMigrations();
        }

        System.out.println("Enter your commands here");
        Scanner scanner = new Scanner(System.in);
        String currentUserName="";
        while (true) {
            TrackerUser currentUser = UserAuthService.getCurrentUser();
            if (Objects.nonNull(currentUser)) {
                currentUserName = currentUser.getUsername();
            } else {
                currentUserName = "";
            }
            System.out.print(currentUserName + "> ");
            String s = scanner.nextLine();
            if (s.trim().equalsIgnoreCase("EXIT"))
                break;
            System.out.println(Command.runCommand(StringSplitter.SplitString(s)));
        }
    }
}