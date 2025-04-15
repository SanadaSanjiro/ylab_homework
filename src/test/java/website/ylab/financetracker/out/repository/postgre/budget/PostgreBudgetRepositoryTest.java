package website.ylab.financetracker.out.repository.postgre.budget;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.ConnectionProvider;
import website.ylab.financetracker.service.DbSchemaCreator;
import website.ylab.financetracker.service.budget.TrackerBudget;
import website.ylab.financetracker.service.LiquibaseStarter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PostgreBudgetRepositoryTest {
    String uuid = "c6aac47f-64b5-47d0-97bc-4974cbdd93f4";
    double limit = 100.0;
    long userid = 1L;
    TrackerUser user = new TrackerUser().setId(userid);


    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("ylabft_db")
            .withUsername("ft_admin")
            .withPassword("MyP@ss4DB");
    static ConnectionProvider connectionProvider;
    static LiquibaseStarter liquibaseStarter;
    static PostgreBudgetRepository repository;

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
        System.out.println("Applying liquibase migrations");
        liquibaseStarter = new LiquibaseStarter(connectionProvider);
        ReflectionTestUtils.setField(liquibaseStarter, "changelog",
                "db/changelog/db.changelog-master.yml");
        liquibaseStarter.applyMigrations();
        repository = new PostgreBudgetRepository(connectionProvider);
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Test
    void setBudget() {
        repository = new PostgreBudgetRepository(connectionProvider);
        Optional<TrackerBudget> result = repository.setBudget(getTrackerBudget());
        assertTrue(result.isPresent());
        TrackerBudget budget = result.get();
        assertEquals(limit, budget.getLimit());
        Optional<TrackerBudget> check = repository.getById(budget.getId());
        assertTrue(check.isPresent());
        assertEquals(limit, check.get().getLimit());
    }

    @Test
    void getById() {
        BudgetEntity budget = getBudgetEntity();
        repository = new PostgreBudgetRepository(connectionProvider);
        Optional<BudgetEntity> optional = repository.createBudget(budget);
        assertTrue(optional.isPresent());
        Optional<BudgetEntity> result = repository.getBudgetById(optional.get().getId());
        assertTrue(result.isPresent());
        assertEquals(limit, result.get().getLimit());
    }

    @Test
    void deleteBudgetById() {
        BudgetEntity budget = getBudgetEntity();
        repository = new PostgreBudgetRepository(connectionProvider);
        Optional<BudgetEntity> optional = repository.createBudget(budget);
        assertTrue(optional.isPresent());
        Optional<TrackerBudget> result = repository.deleteBudget(optional.get().getId());
        assertTrue(result.isPresent());
        assertEquals(limit, result.get().getLimit());
    }

    @Test
    void createBudget() {
        repository = new PostgreBudgetRepository(connectionProvider);
        BudgetEntity budget = getBudgetEntity();
        repository.createBudget(budget);
        Optional<BudgetEntity> optional = repository.getByUUID(uuid);
        assertTrue(optional.isPresent());
    }

    @Test
    void deleteBudget() {
        repository = new PostgreBudgetRepository(connectionProvider);
        BudgetEntity budget = getBudgetEntity();
        repository.createBudget(budget);
        Optional<BudgetEntity> optional = repository.getByUUID(uuid);
        assertTrue(optional.isPresent());
        repository.deleteBudget(budget);
        optional = repository.getByUUID(uuid);
        assertFalse(optional.isPresent());
    }

    @Test
    void testGetById() {
        repository = new PostgreBudgetRepository(connectionProvider);
        BudgetEntity budget = getBudgetEntity();
        repository.createBudget(budget);
        Optional<BudgetEntity> optional = repository.getByUUID(uuid);
        assertFalse(optional.isEmpty());
        long id = optional.get().getId();
        optional=repository.getBudgetById(id);
        assertTrue(optional.isPresent());
    }

    @Test
    void testGetByUserId() {
        repository = new PostgreBudgetRepository(connectionProvider);
        BudgetEntity budget = getBudgetEntity();
        repository.createBudget(budget);
        List<BudgetEntity> list = repository.getBudgetByUserId(user.getId());
        assertFalse(list.isEmpty());
    }

    @Test
    void testGetByUUID() {
        repository = new PostgreBudgetRepository(connectionProvider);
        BudgetEntity budget = getBudgetEntity();
        repository.createBudget(budget);
        Optional<BudgetEntity> optional = repository.getByUUID(uuid);
        assertTrue(optional.isPresent());
    }

    private BudgetEntity getBudgetEntity() {
        return new BudgetEntity().setUuid(uuid).setLimit(limit).setUserId(userid);
    }

    private TrackerBudget getTrackerBudget() {
        return new TrackerBudget().setUserId(userid).setLimit(limit).setUuid(uuid);
    }
}