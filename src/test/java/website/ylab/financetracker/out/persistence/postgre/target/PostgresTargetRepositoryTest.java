package website.ylab.financetracker.out.persistence.postgre.target;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PostgresTargetRepositoryTest {
    String uuid = "c6aac47f-64b5-47d0-97bc-4974cbdd93f4";
    double amount = 100.0;

    long userid = 1L;
    String username = "Bob";
    String password = "123456";
    String email = "bob@gmail.com";

    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("ylabft_db")
            .withUsername("ft_admin")
            .withPassword("MyP@ss4DB");
    static ConnectionProvider connectionProvider;
    static LiquibaseStarter liquibaseStarter;
    static PostgresTargetRepository repository;

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
        repository = new PostgresTargetRepository(connectionProvider);
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Test
    void setTarget() {
        TrackerUser user = getTestUser();
        TargetEntity target = getTestTarget();
        repository = new PostgresTargetRepository(connectionProvider);
        Optional<Double> result = repository.setTarget(user, amount);
        assertTrue(result.isPresent());
        assertEquals(amount, result.get());
        Optional<Double> check = repository.getTarget(user);
        assertTrue(check.isPresent());
        assertEquals(amount, check.get());
    }

    @Test
    void getTarget() {
        TrackerUser user = getTestUser();
        TargetEntity target = getTestTarget();
        repository = new PostgresTargetRepository(connectionProvider);
        Optional<TargetEntity> optional = repository.createTarget(target);
        assertTrue(optional.isPresent());
        Optional<Double> result = repository.getTarget(user);
        assertTrue(result.isPresent());
        assertEquals(amount, result.get());
    }

    @Test
    void deleteTargetByUser() {
        TrackerUser user = getTestUser();
        TargetEntity target = getTestTarget();
        repository = new PostgresTargetRepository(connectionProvider);
        Optional<TargetEntity> optional = repository.createTarget(target);
        assertTrue(optional.isPresent());
        Optional<Double> result = repository.deleteTarget(user);
        assertTrue(result.isPresent());
        assertEquals(amount, result.get());
    }

    @Test
    void createTarget() {
        repository = new PostgresTargetRepository(connectionProvider);
        TargetEntity target = getTestTarget();
        repository.createTarget(target);
        Optional<TargetEntity> optional = repository.getByUUID(uuid);
        assertTrue(optional.isPresent());
    }

    @Test
    void testDeleteTarget() {
        repository = new PostgresTargetRepository(connectionProvider);
        TargetEntity target = getTestTarget();
        repository.createTarget(target);
        Optional<TargetEntity> optional = repository.getByUUID(uuid);
        assertTrue(optional.isPresent());
        repository.deleteTarget(target);
        optional = repository.getByUUID(uuid);
        assertFalse(optional.isPresent());
    }

    @Test
    void getTargetById() {
        repository = new PostgresTargetRepository(connectionProvider);
        TargetEntity target = getTestTarget();
        repository.createTarget(target);
        Optional<TargetEntity> optional = repository.getByUUID(uuid);
        assertFalse(optional.isEmpty());
        long id = optional.get().getId();
        optional=repository.getTargetById(id);
        assertTrue(optional.isPresent());
    }

    @Test
    void getTargetByUserId() {
        repository = new PostgresTargetRepository(connectionProvider);
        TargetEntity target = getTestTarget();
        repository.createTarget(target);
        List<TargetEntity> list = repository.getTargetByUserId(userid);
        assertFalse(list.isEmpty());
    }

    @Test
    void getByUUID() {
        repository = new PostgresTargetRepository(connectionProvider);
        TargetEntity target = getTestTarget();
        repository.createTarget(target);
        Optional<TargetEntity> optional = repository.getByUUID(uuid);
        assertTrue(optional.isPresent());
    }

    private TargetEntity getTestTarget() {
        TargetEntity target = new TargetEntity();
        target.setUuid(uuid);
        target.setAmount(amount);
        target.setUserId(userid);
        return target;
    }

    private TrackerUser getTestUser() {
        TrackerUser user = new TrackerUser(username, email, password);
        user.setRole(Role.USER);
        user.setEnabled(true);
        user.setId(userid);
        return user;
    }
}