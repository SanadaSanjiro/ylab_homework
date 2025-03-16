package website.ylab.financetracker.transactions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import website.ylab.financetracker.out.persistence.TrackerTransactionRepository;
import website.ylab.financetracker.out.persistence.ram.transaction.RamTransactionRepo;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RamTransactionRepoTest {
    long id = 1L;
    TransactionType type = TransactionType.INCOME;
    double amount = 100.0;
    String category = "Travel";
    Date date = new Date();
    String description = "Test";
    TrackerTransaction transaction = createTestTransaction();
    TrackerTransactionRepository repository = new RamTransactionRepo();
    Optional<TrackerTransaction> optional;

    @BeforeEach
    void setUp() {
        optional =repository.create(transaction);
    }

    @Test
    void create() {
        assertTrue(optional.isPresent());
        assertEquals(id, optional.get().getId());
        assertEquals(type, optional.get().getType());
        assertEquals(amount, optional.get().getAmount());
        assertEquals(category, optional.get().getCategory());
        assertEquals(description, optional.get().getDescription());
        assertEquals(date, optional.get().getDate());
    }

    @Test
    void update() {
        double newAmount = 500.0;
        String newCategory = "Food";
        String newDescription = "New Description";
        TrackerTransaction transaction = createTestTransaction();
        transaction.setId(id);
        transaction.setType(type);
        transaction.setDate(date);
        transaction.setAmount(newAmount);
        transaction.setCategory(newCategory);
        transaction.setDescription(newDescription);
        optional = repository.update(transaction);
        assertTrue(optional.isPresent());
        assertEquals(id, optional.get().getId());
        assertEquals(type, optional.get().getType());
        assertEquals(newAmount, optional.get().getAmount());
        assertEquals(newCategory, optional.get().getCategory());
        assertEquals(newDescription, optional.get().getDescription());
        assertEquals(date, optional.get().getDate());
    }

    @Test
    void delete() {
        // Если транзакция отсутствует
        optional = repository.delete(new TrackerTransaction());
        assertFalse(optional.isPresent());

        // Если транзакция найдена и была успешно удалена
        optional = repository.delete(transaction);
        assertTrue(optional.isPresent());
        assertEquals(id, optional.get().getId());
        assertEquals(type, optional.get().getType());
        assertEquals(amount, optional.get().getAmount());
        assertEquals(category, optional.get().getCategory());
        assertEquals(description, optional.get().getDescription());
        assertEquals(date, optional.get().getDate());
    }

    @Test
    void getById() {
        // Если транзакция существует
        optional = repository.getById(id);
        assertTrue(optional.isPresent());
        assertEquals(id, optional.get().getId());
        assertEquals(type, optional.get().getType());
        assertEquals(amount, optional.get().getAmount());
        assertEquals(category, optional.get().getCategory());
        assertEquals(description, optional.get().getDescription());
        assertEquals(date, optional.get().getDate());

        // Если транзакция отсутствует
        optional = repository.getById(100L);
        assertFalse(optional.isPresent());
    }

    @Test
    void getByIdAllTransactions() {
        assertFalse(repository.getAllTransactions().isEmpty());
    }

    private TrackerTransaction createTestTransaction() {
        TrackerTransaction transaction = new TrackerTransaction();
        transaction.setId(id);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setDate(date);
        transaction.setDescription(description);
        return transaction;
    }
}