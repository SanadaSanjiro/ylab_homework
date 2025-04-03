package website.ylab.financetracker.out.repository.postgre.target;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import website.ylab.financetracker.service.ConnectionProvider;
import website.ylab.financetracker.service.DbSchemaCreator;
import website.ylab.financetracker.service.targets.TrackerTarget;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PostgresTargetRepositoryTest {
    String uuid = "c6aac47f-64b5-47d0-97bc-4974cbdd93f4";
    double amount = 100.0;
    long userid = 1L;

    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("ylabft_db")
            .withUsername("ft_admin")
            .withPassword("MyP@ss4DB");
    static ConnectionProvider connectionProvider;
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
        };
        System.out.println("Creating schema");
        DbSchemaCreator schemaCreator = new DbSchemaCreator(connectionProvider);
        schemaCreator.createDbSchema();
        repository = new PostgresTargetRepository(connectionProvider);
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Test
    void setTarget() {
        repository = new PostgresTargetRepository(connectionProvider);
        Optional<TrackerTarget> result = repository.setTarget(
                new TrackerTarget().setUserId(userid).setAmount(amount).setUuid(uuid));
        assertTrue(result.isPresent());
        TrackerTarget target = result.get();
        assertEquals(amount, target.getAmount());
        Optional<TrackerTarget> check = repository.getById(target.getId());
        assertTrue(check.isPresent());
        assertEquals(amount, check.get().getAmount());
    }

    @Test
    void getById() {
        TargetEntity target = getTestTarget();
        repository = new PostgresTargetRepository(connectionProvider);
        Optional<TargetEntity> optional = repository.createTarget(target);
        assertTrue(optional.isPresent());
        Optional<TrackerTarget> result = repository.getById(optional.get().getId());
        assertTrue(result.isPresent());
        assertEquals(amount, result.get().getAmount());
    }

    @Test
    void deleteTargetById() {
        TargetEntity target = getTestTarget();
        repository = new PostgresTargetRepository(connectionProvider);
        Optional<TargetEntity> optional = repository.createTarget(target);
        assertTrue(optional.isPresent());
        Optional<TrackerTarget> result = repository.deleteTarget(optional.get().getId());
        assertTrue(result.isPresent());
        assertEquals(amount, result.get().getAmount());
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
    void deleteTarget() {
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
    void getByIdById() {
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
    void getByUserId() {
        repository = new PostgresTargetRepository(connectionProvider);
        TargetEntity target = getTestTarget();
        repository.createTarget(target);
        Optional<TrackerTarget> optional = repository.getByUserId(userid);
        assertFalse(optional.isEmpty());
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
}