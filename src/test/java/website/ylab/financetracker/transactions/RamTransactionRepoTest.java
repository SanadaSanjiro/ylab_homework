package website.ylab.financetracker.transactions;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.out.persistence.TrackerTransactionRepository;
import website.ylab.financetracker.out.persistence.ram.transaction.RamTransactionRepo;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RamTransactionRepoTest {
    long id = 100L;
    TransactionType type = TransactionType.INCOME;
    double amount = 100.0;
    String category = "Travel";
    Date date = new Date();
    String description = "Test";
    TrackerTransaction transaction;
    TrackerTransactionRepository repository;

    @Test
    void create() {
        repository = new RamTransactionRepo();
        transaction = createTestTransaction();
        Optional<TrackerTransaction> optional = repository.create(transaction);
        assertTrue(optional.isPresent());
        assertEquals(type, optional.get().getType());
        assertEquals(amount, optional.get().getAmount());
        assertEquals(category, optional.get().getCategory());
        assertEquals(description, optional.get().getDescription());
        assertEquals(date, optional.get().getDate());
    }

    @Test
    void update() {
        repository = new RamTransactionRepo();
        transaction = createTestTransaction();
        double newAmount = 500.0;
        String newCategory = "Food";
        String newDescription = "New Description";
        Optional<TrackerTransaction> optional = repository.create(transaction);
        assertTrue(optional.isPresent());
        transaction = new TrackerTransaction();
        transaction.setId(optional.get().getId());
        transaction.setType(type);
        transaction.setDate(date);
        transaction.setAmount(newAmount);
        transaction.setCategory(newCategory);
        transaction.setDescription(newDescription);
        optional = repository.update(transaction);
        assertTrue(optional.isPresent());
        assertEquals(type, optional.get().getType());
        assertEquals(newAmount, optional.get().getAmount());
        assertEquals(newCategory, optional.get().getCategory());
        assertEquals(newDescription, optional.get().getDescription());
        assertEquals(date, optional.get().getDate());
    }

    @Test
    void delete() {
        transaction = createTestTransaction();
        repository = new RamTransactionRepo();
        // Если транзакция отсутствует
        Optional<TrackerTransaction> optional = repository.delete(new TrackerTransaction());
        assertFalse(optional.isPresent());

        // Если транзакция найдена и была успешно удалена
        repository.create(transaction);
        optional = repository.delete(transaction);
        assertTrue(optional.isPresent());
        assertEquals(type, optional.get().getType());
        assertEquals(amount, optional.get().getAmount());
        assertEquals(category, optional.get().getCategory());
        assertEquals(description, optional.get().getDescription());
        assertEquals(date, optional.get().getDate());
    }

    @Test
    void getById() {
        transaction = createTestTransaction();
        repository = new RamTransactionRepo();
        Optional<TrackerTransaction> optional = repository.create(transaction);
        assertTrue(optional.isPresent());
        long id = optional.get().getId();
        // Если транзакция существует
        optional = repository.getById(id);
        assertTrue(optional.isPresent());
        assertEquals(type, optional.get().getType());
        assertEquals(amount, optional.get().getAmount());
        assertEquals(category, optional.get().getCategory());
        assertEquals(description, optional.get().getDescription());
        assertEquals(date, optional.get().getDate());

        // Если транзакция отсутствует
        optional = repository.getById(500L);
        assertFalse(optional.isPresent());
    }

    @Test
    void getByIdAllTransactions() {
        repository = new RamTransactionRepo();
        assertTrue(repository.getAllTransactions().isEmpty());
    }

    private TrackerTransaction createTestTransaction() {
        repository = new RamTransactionRepo();
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