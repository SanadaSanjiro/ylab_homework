package website.ylab.financetracker.out.persistence.postgre.auth;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import website.ylab.financetracker.auth.Role;
import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.out.persistence.postgre.ConnectionProvider;
import website.ylab.financetracker.out.persistence.postgre.DbSchemaCreator;
import website.ylab.financetracker.out.persistence.postgre.liquibase.LiquibaseStarter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PostgreUserRepositoryTest {
    static String username = "Bob";
    static String password = "123456";
    static String email = "bob@gmail.com";
    static TrackerUser user = null;

    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("ylabft_db")
            .withUsername("ft_admin")
            .withPassword("MyP@ss4DB");
    static ConnectionProvider connectionProvider;
    static LiquibaseStarter liquibaseStarter;

    @BeforeAll
    static void beforeAll() throws Exception {
        System.out.println("Starting testcontainer");
        postgreSQLContainer.start();
        connectionProvider = new ConnectionProvider() {
            @Override
            public Connection getConnection() throws SQLException {
                return DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(),
                        postgreSQLContainer.getPassword());
            }
            @Override
            public String getSchema() {
                return "fin_tracker";
            }

            @Override
            public String getChangelog() {
                return "db/changelog/db.changelog-master.yml";
            }

            @Override
            public String getPersistenceType() {
                return "postgresql";
            }
        };
        System.out.println("Creating schema");
        DbSchemaCreator schemaCreator = new DbSchemaCreator(connectionProvider);
        schemaCreator.createDbSchema();
        System.out.println("Applying liquibase migrations");
        liquibaseStarter = new LiquibaseStarter(connectionProvider);
        liquibaseStarter.applyMigrations();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @BeforeEach
    void beforeEach() {
        user = getTestUser();
    }


    @Test
    void create() throws Exception {
        PostgreUserRepository repository = new PostgreUserRepository(connectionProvider);
        Optional<TrackerUser> result = repository.create(user);
        assertTrue(result.isPresent());
        TrackerUser returnedUser = result.get();
        assertTrue(username.equalsIgnoreCase(returnedUser.getUsername()));
        assertEquals(password, returnedUser.getPassword());
        assertTrue(email.equalsIgnoreCase(returnedUser.getEmail()));
        assertEquals(Role.USER, returnedUser.getRole());
        assertTrue(returnedUser.getId()>0L);
    }

    @Test
    void get() {
    }

    @Test
    void getById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAllUsers() {
    }

    private TrackerUser getTestUser() {
        TrackerUser user = new TrackerUser(username, email, password);
        user.setRole(Role.USER);
        user.setEnabled(true);
        return user;
    }
}