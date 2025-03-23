package website.ylab.financetracker.out.repository.postgre.transacion;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import website.ylab.financetracker.util.ConnectionProvider;
import website.ylab.financetracker.util.DbSchemaCreator;
import website.ylab.financetracker.util.LiquibaseStarter;
import website.ylab.financetracker.service.transactions.TrackerTransaction;
import website.ylab.financetracker.service.transactions.TransactionType;
import website.ylab.financetracker.util.DateConvertor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PostgreTransactionRepositoryTest {
    long id = 1L;
    long userid = 1L;
    TransactionType type = TransactionType.INCOME;
    double amount = 100.0;
    String category = "Travel";
    Date date = DateConvertor.SqlStringToJavaUtilDate("2025-03-03");
    String description = "Test";
    String uuid = "c6aac47f-64b5-47d0-97bc-4974cbdd93f4";
    TrackerTransaction transaction = createTestTransaction();
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("ylabft_db")
            .withUsername("ft_admin")
            .withPassword("MyP@ss4DB");
    static ConnectionProvider connectionProvider;
    static LiquibaseStarter liquibaseStarter;
    static PostgreTransactionRepository repository;

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
        repository = new PostgreTransactionRepository(connectionProvider);
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @BeforeEach
    void beforeEach() {
        transaction = createTestTransaction();
    }
    @Test
    void create() {
        Optional<TrackerTransaction> result = repository.create(transaction);
        assertTrue(result.isPresent());
        TrackerTransaction returnedTransaction = result.get();
        assertEquals(type, returnedTransaction.getType());
        assertEquals(amount, returnedTransaction.getAmount());
        assertEquals(category.toLowerCase(), returnedTransaction.getCategory());
        assertEquals(date, returnedTransaction.getDate());
        assertEquals(description, returnedTransaction.getDescription());
        assertEquals(uuid, returnedTransaction.getUuid());
        repository.delete(transaction);
    }

    @Test
    void getByUserId() {
        Optional<TrackerTransaction> result = repository.create(transaction);
        assertTrue(result.isPresent());
        List<TrackerTransaction> list = repository.getByUserId(userid);
        assertFalse(list.isEmpty());
        TrackerTransaction returnedTransaction = list.stream()
                        .filter(t->t.getUuid().equals(uuid))
                        .findFirst()
                        .orElse(null);
        assertEquals(type, returnedTransaction.getType());
        assertEquals(amount, returnedTransaction.getAmount());
        assertEquals(category.toLowerCase(), returnedTransaction.getCategory());
        assertEquals(date, returnedTransaction.getDate());
        assertEquals(description, returnedTransaction.getDescription());
        assertEquals(uuid, returnedTransaction.getUuid());
        repository.delete(transaction);
    }

    @Test
    void update() {
        double newAmount = 50.0;
        String newCategory ="shopping";
        String newDescription ="new description";
        TrackerTransaction newTransaction = createTestTransaction();
        newTransaction.setAmount(newAmount);
        newTransaction.setCategory(newCategory);
        newTransaction.setDescription(newDescription);
        Optional<TrackerTransaction> result = repository.create(newTransaction);
        assertTrue(result.isPresent());
        TrackerTransaction returnedTransaction = result.get();
        long id = returnedTransaction.getId();
        assertTrue(id>0L);
        newTransaction.setId(id);
        newTransaction.setUuid(returnedTransaction.getUuid());
        result = repository.update(newTransaction);
        assertTrue(result.isPresent());
        TrackerTransaction transaction1 = result.get();
        assertEquals(type, transaction1.getType());
        assertEquals(newAmount, transaction1.getAmount());
        assertEquals(newCategory.toLowerCase(), transaction1.getCategory());
        assertEquals(date, transaction1.getDate());
        assertEquals(newDescription, transaction1.getDescription());
        assertEquals(uuid, transaction1.getUuid());
        repository.delete(newTransaction);
        repository.delete(transaction);
        repository.delete(transaction1);
    }

    @Test
    void delete() {
        Optional<TrackerTransaction> result = repository.create(transaction);
        assertTrue(result.isPresent());
        long id = result.get().getId();
        TrackerTransaction transaction1 = result.get();
        result = repository.delete(transaction1);
        assertTrue(result.isPresent());
        TrackerTransaction returnedTransaction = result.get();
        assertEquals(type, returnedTransaction.getType());
        assertEquals(amount, returnedTransaction.getAmount());
        assertEquals(category.toLowerCase(), returnedTransaction.getCategory());
        assertEquals(date, returnedTransaction.getDate());
        assertEquals(description, returnedTransaction.getDescription());
        assertEquals(uuid, returnedTransaction.getUuid());
        assertTrue(repository.getById(id).isEmpty());
    }

    @Test
    void getById() {
        repository.create(transaction);
        Optional<TrackerTransaction> result;
        result = repository.getByUUID(uuid);
        assertTrue(result.isPresent());
        TrackerTransaction returnedTransaction = result.get();
        assertEquals(type, returnedTransaction.getType());
        assertEquals(amount, returnedTransaction.getAmount());
        assertEquals(category.toLowerCase(), returnedTransaction.getCategory());
        assertEquals(date, returnedTransaction.getDate());
        assertEquals(description, returnedTransaction.getDescription());
        assertEquals(uuid, returnedTransaction.getUuid());
        repository.delete(returnedTransaction);
    }

    @Test
    void getAllTransactions() {
        double newAmount = 50.0;
        String newCategory ="shopping";
        String newDescription ="new description";
        TrackerTransaction newTransaction = createTestTransaction();
        newTransaction.setUuid("uuid");
        newTransaction.setDate(new Date());
        newTransaction.setType(TransactionType.INCOME);
        newTransaction.setUserId(2L);
        newTransaction.setAmount(newAmount);
        newTransaction.setCategory(newCategory);
        newTransaction.setDescription(newDescription);
        repository.create(transaction);
        repository.create(newTransaction);
        List<TrackerTransaction> result = repository.getAllTransactions();
        assertFalse(result.isEmpty());
        assertTrue(result.size()>=2);
        repository.delete(newTransaction);
        repository.delete(transaction);
    }

    private TrackerTransaction createTestTransaction() {
        TrackerTransaction transaction = new TrackerTransaction();
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setDate(date);
        transaction.setDescription(description);
        transaction.setUserId(userid);
        transaction.setUuid(uuid);
        return transaction;
    }
}