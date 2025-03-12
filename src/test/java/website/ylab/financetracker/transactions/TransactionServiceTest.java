package website.ylab.financetracker.transactions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.auth.UserAuthService;

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
    TrackerTransaction transaction = getTransaction();
    TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService(repository);
    }

    @Test
    void addNewTransaction() {
        Mockito.when(repository.create(Mockito.any())).thenReturn(Optional.of(transaction));
        try (MockedStatic<UserAuthService> authMock = Mockito.mockStatic(UserAuthService.class)) {
            authMock.when(UserAuthService::getCurrentUser).thenReturn(user);
            assertEquals("Transaction added successfully", transactionService
                    .addNewTransaction(type, amount, category, date, description));
        }

        Mockito.when(repository.create(Mockito.any())).thenReturn(Optional.empty());
        try (MockedStatic<UserAuthService> authMock = Mockito.mockStatic(UserAuthService.class)) {
            assertEquals("Transaction creation error", transactionService
                    .addNewTransaction(type, amount, category, date, description));
        }
    }

    @Test
    void changeTransaction() {
        double newAmount = amount*2;
        String newCategory = "new category";
        String newDescription = "new description";

        Mockito.when(repository.get(id)).thenReturn(Optional.empty());
        assertEquals("Transaction not found", transactionService
                .changeTransaction(id, newAmount, newCategory, newDescription));

        Mockito.when(repository.get(id)).thenReturn(Optional.of(transaction));
        try (MockedStatic<UserAuthService> authMock = Mockito.mockStatic(UserAuthService.class)) {
            authMock.when(UserAuthService::getCurrentUser).thenReturn(new TrackerUser());
            assertEquals("You do not have permission to change this transaction", transactionService
                    .changeTransaction(id, newAmount, newCategory, newDescription));
        }
        try (MockedStatic<UserAuthService> authMock = Mockito.mockStatic(UserAuthService.class)) {
            authMock.when(UserAuthService::getCurrentUser).thenReturn(user);
            assertEquals("Transaction data successfully changed", transactionService
                    .changeTransaction(id, newAmount, newCategory, newDescription));
        }

    }

    @Test
    void deleteTransaction() {
        Mockito.when(repository.create(Mockito.any())).thenReturn(Optional.of(transaction));
        try (MockedStatic<UserAuthService> authMock = Mockito.mockStatic(UserAuthService.class)) {
            authMock.when(UserAuthService::getCurrentUser).thenReturn(user);
            transactionService
                    .addNewTransaction(type, amount, category, date, description);
        }

        Mockito.when(repository.get(id)).thenReturn(Optional.empty());
        assertEquals("Transaction not found", transactionService.deleteTransaction(id));

        Mockito.when(repository.get(id)).thenReturn(Optional.of(transaction));
        try (MockedStatic<UserAuthService> authMock = Mockito.mockStatic(UserAuthService.class)) {
            authMock.when(UserAuthService::getCurrentUser).thenReturn(new TrackerUser());
            assertEquals("You do not have permission to delete this transaction", transactionService
                    .deleteTransaction(id));
        }

        Mockito.when(repository.get(id)).thenReturn(Optional.of(transaction));
        try (MockedStatic<UserAuthService> authMock = Mockito.mockStatic(UserAuthService.class)) {
            authMock.when(UserAuthService::getCurrentUser).thenReturn(user);
            assertEquals("Transaction successfully deleted", transactionService
                    .deleteTransaction(id));
        }
    }

    @Test
    void getAllTransactions() {
        Mockito.when(repository.getAllTransactions()).thenReturn(List.of(transaction));
        try (MockedStatic<UserAuthService> authMock = Mockito.mockStatic(UserAuthService.class)) {
            authMock.when(UserAuthService::getCurrentUser).thenReturn(user);
            assertFalse(transactionService.getAllTransactions().isEmpty());
        }
    }

    @Test
    void deleteUserTransactions() {
        Mockito.when(repository.getAllTransactions()).thenReturn(List.of(transaction));
        Mockito.when(repository.delete(Mockito.any())).thenReturn(Optional.of(transaction));
        try (MockedStatic<UserAuthService> authMock = Mockito.mockStatic(UserAuthService.class)) {
            authMock.when(UserAuthService::getCurrentUser).thenReturn(user);
            transactionService.deleteUserTransactions(user);
            verify(repository, times(1)).delete(transaction);
        }
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
        transaction.setUser(user);
        return transaction;
    }
}