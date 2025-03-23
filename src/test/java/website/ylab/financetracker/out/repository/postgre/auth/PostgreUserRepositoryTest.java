package website.ylab.financetracker.out.repository.postgre.auth;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import website.ylab.financetracker.service.auth.Role;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.util.ConnectionProvider;
import website.ylab.financetracker.util.DbSchemaCreator;
import website.ylab.financetracker.util.LiquibaseStarter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
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
    static PostgreUserRepository repository;

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
        repository = new PostgreUserRepository(connectionProvider);
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @BeforeEach
    void beforeEach() {
        user = getByNameTestUser();
    }


    @Test
    void create() {
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
    void getByName() {
        System.out.println(user);
        repository.create(user);
        Optional<TrackerUser> result = repository.getByName(username);
        assertTrue(result.isPresent());
        TrackerUser returnedUser = result.get();
        assertTrue(username.equalsIgnoreCase(returnedUser.getUsername()));
        assertEquals(password, returnedUser.getPassword());
        assertTrue(email.equalsIgnoreCase(returnedUser.getEmail()));
        assertEquals(Role.USER, returnedUser.getRole());
        assertTrue(returnedUser.getId()>0L);
    }

    @Test
    void getById() {
        Optional<TrackerUser> result = repository.create(user);
        assertTrue(result.isPresent());
        long id = result.get().getId();
        assertTrue(id>0L);
        result = repository.getById(id);
        assertTrue(result.isPresent());
        TrackerUser returnedUser = result.get();
        assertTrue(username.equalsIgnoreCase(returnedUser.getUsername()));
        assertEquals(password, returnedUser.getPassword());
        assertTrue(email.equalsIgnoreCase(returnedUser.getEmail()));
        assertEquals(Role.USER, returnedUser.getRole());
    }


    @Test
    void update() {
        String newName = "Alice";
        String newEmail ="alice@mail.ru";
        String newPassword ="456";
        TrackerUser changedUser =  new TrackerUser().setUsername(newName).setEmail(newEmail).setPassword(newPassword);
        changedUser.setEnabled(false);
        changedUser.setRole(Role.ADMIN);
        Optional<TrackerUser> result = repository.create(user);
        assertTrue(result.isPresent());
        long id = result.get().getId();
        assertTrue(id>0L);
        changedUser.setId(id);
        result = repository.update(changedUser);
        assertTrue(result.isPresent());
        TrackerUser returnedUser = result.get();
        assertTrue(newName.equalsIgnoreCase(returnedUser.getUsername()));
        assertEquals(newPassword, returnedUser.getPassword());
        assertTrue(newEmail.equalsIgnoreCase(returnedUser.getEmail()));
        assertEquals(Role.ADMIN, returnedUser.getRole());
    }

    @Test
    void delete() {
        Optional<TrackerUser> result = repository.create(user);
        assertTrue(result.isPresent());
        TrackerUser returnedUser = result.get();
        result = repository.delete(returnedUser);
        assertTrue(result.isPresent());
        returnedUser = result.get();
        assertTrue(username.equalsIgnoreCase(returnedUser.getUsername()));
        assertEquals(password, returnedUser.getPassword());
        assertTrue(email.equalsIgnoreCase(returnedUser.getEmail()));
        assertEquals(Role.USER, returnedUser.getRole());
    }

    @Test
    void getAllUsers() {
        String newName = "Alice";
        String newEmail ="alice@mail.ru";
        String newPassword ="456";
        TrackerUser anotherUser =  new TrackerUser().setUsername(newName).setEmail(newEmail).setPassword(newPassword);
        anotherUser.setEnabled(false);
        anotherUser.setRole(Role.ADMIN);
        repository.create(user);
        repository.create(anotherUser);
        List<TrackerUser> result = repository.getAllUsers();
        assertFalse(result.isEmpty());
        assertTrue(result.size()>=2);
    }

    private TrackerUser getByNameTestUser() {
        TrackerUser user = new TrackerUser().setUsername(username).setEmail(email).setPassword(password);
        user.setRole(Role.USER);
        user.setEnabled(true);
        return user;
    }
}