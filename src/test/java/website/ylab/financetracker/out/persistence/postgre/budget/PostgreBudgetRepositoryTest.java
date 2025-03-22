package website.ylab.financetracker.out.persistence.postgre.budget;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import website.ylab.financetracker.auth.Role;
import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.out.persistence.postgre.ConnectionProvider;
import website.ylab.financetracker.out.persistence.postgre.DbSchemaCreator;
import website.ylab.financetracker.out.persistence.postgre.auth.PostgreUserRepository;
import website.ylab.financetracker.out.persistence.postgre.liquibase.LiquibaseStarter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PostgreBudgetRepositoryTest {
    static String uuid = "c6aac47f-64b5-47d0-97bc-4974cbdd93f4";
    static double limit = 100.0;

    static String username = "Bob";
    static String password = "123456";
    static String email = "bob@gmail.com";
    static TrackerUser user;


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
        repository = new PostgreBudgetRepository(connectionProvider);
        PostgreUserRepository userRepository = new PostgreUserRepository(connectionProvider);
        Optional<TrackerUser> optional = userRepository.create(getTestUser());
        assertTrue(optional.isPresent());
        user = optional.get();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }


    @Test
    void testSetBudget() {
        BudgetEntity budget = getTestBudget();
        repository = new PostgreBudgetRepository(connectionProvider);
        Optional<Double> result = repository.setBudget(user, limit);
        assertTrue(result.isPresent());
        assertEquals(limit, result.get());
        Optional<Double> check = repository.getBudget(user);
        assertTrue(check.isPresent());
        assertEquals(limit, check.get());
        repository.deleteBudget(user);
    }

    @Test
    void testGetBudget() {
        BudgetEntity budget = getTestBudget();
        repository = new PostgreBudgetRepository(connectionProvider);
        Optional<BudgetEntity> optional = repository.createBudget(budget);
        assertTrue(optional.isPresent());
        Optional<Double> result = repository.getBudget(user);
        assertTrue(result.isPresent());
        assertEquals(limit, result.get());
        repository.deleteBudget(user);
    }

    @Test
    void testDeleteBudgetByUser() {
        BudgetEntity budget = getTestBudget();
        repository = new PostgreBudgetRepository(connectionProvider);
        Optional<BudgetEntity> optional = repository.createBudget(budget);
        assertTrue(optional.isPresent());
        Optional<Double> result = repository.deleteBudget(user);
        assertTrue(result.isPresent());
        assertEquals(limit, result.get());
    }

    @Test
    void createBudget() {
        repository = new PostgreBudgetRepository(connectionProvider);
        BudgetEntity budget = getTestBudget();
        repository.createBudget(budget);
        Optional<BudgetEntity> optional = repository.getByUUID(uuid);
        assertTrue(optional.isPresent());
        repository.deleteBudget(optional.get());
    }

    @Test
    void testDeleteBudget() {
        repository = new PostgreBudgetRepository(connectionProvider);
        BudgetEntity budget = getTestBudget();
        repository.createBudget(budget);
        Optional<BudgetEntity> optional = repository.getByUUID(uuid);
        assertTrue(optional.isPresent());
        repository.deleteBudget(budget);
        optional = repository.getByUUID(uuid);
        assertFalse(optional.isPresent());
    }

    @Test
    void testGetBudgetById() {
        repository = new PostgreBudgetRepository(connectionProvider);
        BudgetEntity budget = getTestBudget();
        repository.createBudget(budget);
        Optional<BudgetEntity> optional = repository.getByUUID(uuid);
        assertFalse(optional.isEmpty());
        long id = optional.get().getId();
        optional=repository.getBudgetById(id);
        assertTrue(optional.isPresent());
        repository.deleteBudget(optional.get());
    }

    @Test
    void testGetBudgetByUserId() {
        repository = new PostgreBudgetRepository(connectionProvider);
        BudgetEntity budget = getTestBudget();
        repository.createBudget(budget);
        List<BudgetEntity> list = repository.getBudgetByUserId(user.getId());
        assertFalse(list.isEmpty());
        repository.deleteBudget(budget);
    }

    @Test
    void testGetByUUID() {
        repository = new PostgreBudgetRepository(connectionProvider);
        BudgetEntity budget = getTestBudget();
        repository.createBudget(budget);
        Optional<BudgetEntity> optional = repository.getByUUID(uuid);
        assertTrue(optional.isPresent());
        repository.deleteBudget(optional.get());
    }
    private BudgetEntity getTestBudget() {
        BudgetEntity budget = new BudgetEntity();
        budget.setUuid(uuid);
        budget.setLimit(limit);
        budget.setUserId(user.getId());
        return budget;
    }

    private static TrackerUser getTestUser() {
        TrackerUser user = new TrackerUser(username, email, password);
        user.setRole(Role.USER);
        user.setEnabled(true);
        return user;
    }
}