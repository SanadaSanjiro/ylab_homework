package website.ylab.financetracker.service.transactions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.in.cli.auth.UserAuthService;
import website.ylab.financetracker.out.repository.TrackerTransactionRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class TransactionServiceTest {
    TrackerTransactionRepository repository = Mockito.mock(TrackerTransactionRepository.class);
    long id = 1L;
    TransactionType type = TransactionType.INCOME;
    double amount = 100.0;
    String category = "Travel";
    Date date = new Date();
    String description = "Test";
    TrackerUser user = new TrackerUser();
    TrackerUser otherUser = new TrackerUser();
    TrackerTransaction transaction = getTransaction();
    TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService(repository);
    }

    @Test
    void addNewTransactionSuccess() {
        Mockito.when(repository.create(Mockito.any())).thenReturn(Optional.of(transaction));
        TrackerTransaction newTransaction = new TrackerTransaction();
        newTransaction
                .setType(type)
                .setAmount(amount)
                .setCategory(category)
                .setDate(date)
                .setDescription(description);
        TransactionResponse response = transactionService.addNewTransaction(newTransaction);
        assertNotNull(response);
        assertEquals(type.toString(), response.getType());
        assertEquals(amount, response.getAmount());
        assertEquals(category, response.getCategory());
        assertEquals(date, response.getDate());
        assertEquals(description, response.getDescription());
    }

    @Test
    void addNewTransactionFail() {
        Mockito.when(repository.create(Mockito.any())).thenReturn(Optional.empty());
        TrackerTransaction newTransaction = new TrackerTransaction();
        newTransaction
                    .setType(type)
                    .setAmount(amount)
                    .setCategory(category)
                    .setDate(date)
                    .setDescription(description);
        assertNull(transactionService.addNewTransaction(newTransaction));
    }

    @Test
    void changeTransactionFail() {
        double newAmount = amount * 2;
        String newCategory = "new category";
        String newDescription = "new description";
        Mockito.when(repository.update(Mockito.any())).thenReturn(Optional.empty());
        TrackerTransaction newTransaction = new TrackerTransaction();
        newTransaction
                .setId(id)
                .setAmount(newAmount)
                .setCategory(newCategory)
                .setDescription(newDescription);
        assertNull(transactionService.changeTransaction(newTransaction));
    }

    @Test
    void changeTransactionSuccess() {
        double newAmount = amount * 2;
        String newCategory = "new category";
        String newDescription = "new description";
        TrackerTransaction newTransaction = new TrackerTransaction();
        newTransaction.setId(id)
                    .setAmount(newAmount)
                    .setCategory(newCategory)
                    .setDescription(newDescription);
        Mockito.when(repository.getById(Mockito.anyLong())).thenReturn(Optional.of(transaction));
        Mockito.when(repository.update(Mockito.any())).thenReturn(Optional.of(newTransaction));
        TransactionResponse response = transactionService.changeTransaction(newTransaction);
        assertNotNull(response);
        assertEquals(newAmount, response.getAmount());
        assertEquals(newCategory, response.getCategory());
        assertEquals(newDescription, response.getDescription());
    }

    @Test
    void deleteTransactionFail() {
        Mockito.when(repository.create(Mockito.any())).thenReturn(Optional.of(transaction));
        Mockito.when(repository.getById(id)).thenReturn(Optional.empty());
        assertNull(transactionService.deleteTransaction(id));
    }

    @Test
    void deleteTransactionSuccess() {
        Mockito.when(repository.getById(id)).thenReturn(Optional.of(transaction));
        Mockito.when(repository.delete(Mockito.any())).thenReturn(Optional.of(transaction));
        System.out.println(transactionService.deleteTransaction(id));
    }

    @Test
    void getAllTransactions() {
        transaction = getTransaction();
        Mockito.when(repository.getAllTransactions()).thenReturn(List.of(transaction));
        assertFalse(transactionService.getAllTransactions().isEmpty());
    }

    @Test
    void deleteUserTransactions() {
        Mockito.when(repository.getByUserId(Mockito.anyLong())).thenReturn(List.of(transaction));
        Mockito.when(repository.delete(Mockito.any())).thenReturn(Optional.of(transaction));
        transactionService.deleteUserTransactions(user.getId());
        verify(repository, times(1)).delete(transaction);
    }

    private TrackerTransaction getTransaction() {
        TrackerTransaction transaction = new TrackerTransaction();
        transaction.setId(id);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setDescription(description);
        transaction.setDate(date);
        transaction.setCategory(category);
        transaction.setUserId(user.getId());
        return transaction;
    }
}