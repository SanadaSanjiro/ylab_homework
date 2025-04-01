package website.ylab.financetracker.out.repository.postgre.transacion;

import website.ylab.financetracker.out.repository.TrackerTransactionRepository;
import website.ylab.financetracker.util.ConnectionProvider;
import website.ylab.financetracker.service.transactions.TrackerTransaction;
import website.ylab.financetracker.service.transactions.TransactionType;
import website.ylab.financetracker.util.DateConvertor;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PostgreTransactionRepository implements TrackerTransactionRepository {
    // The sole purpose of this array is to give names to the values while parsing the values
    // retrieved from the database, making the code more understandable.
    private final String[] dbFields = {"id", "category", "description", "type", "amount", "date", "userid", "uuid"};
    private final ConnectionProvider connectionProvider;


    public PostgreTransactionRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Optional<TrackerTransaction> create(TrackerTransaction transaction) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String querry = "insert into fin_tracker.ft_transaction " +
                "(category, description, type, amount, date, userid, uuid) " +
                "values (?,?,?,?,?,?,?);";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(querry, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, transaction.getCategory().toLowerCase());
            preparedStatement.setString(2, transaction.getDescription());
            preparedStatement.setString(3, transaction.getType().toString());
            preparedStatement.setDouble(4, transaction.getAmount());
            preparedStatement.setDate(5, DateConvertor.JavaDateToSQLDate(transaction.getDate()));
            preparedStatement.setLong(6, transaction.getUserId());
            preparedStatement.setString(7, transaction.getUuid());
            preparedStatement.executeUpdate();
            return getByUUID(transaction.getUuid());
        } catch (SQLException e) {
            System.out.println("Got SQL Exception while creating transaction " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<TrackerTransaction> getByUserId(long userId) {
        String query = "select * from fin_tracker.ft_transaction where userid = ?;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return parseRS(resultSet);
        } catch (SQLException e) {
            System.out.println("Got SQL Exception while getting transaction by user id " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<TrackerTransaction> update(TrackerTransaction transaction) {
        String query = "update fin_tracker.ft_transaction set category = ?, " +
                "description = ?, type = ?, amount = ?, date = ?, userid = ? where id = ?;";
        Optional<TrackerTransaction> optional = getById(transaction.getId());
        if (optional.isEmpty()) { return optional; }
        TrackerTransaction storedTransaction = optional.get();
        copyTransactionData(storedTransaction, transaction);
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, storedTransaction.getCategory().toLowerCase());
            preparedStatement.setString(2, storedTransaction.getDescription());
            preparedStatement.setString(3, storedTransaction.getType().toString());
            preparedStatement.setDouble(4, storedTransaction.getAmount());
            preparedStatement.setDate( 5, DateConvertor.JavaDateToSQLDate(storedTransaction.getDate()));
            preparedStatement.setLong(6, storedTransaction.getUserId());
            preparedStatement.setLong(7, storedTransaction.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Got SQL Exception while updating transaction " + e.getMessage());
            return Optional.empty();
        }
        return getByUUID(storedTransaction.getUuid());
    }

    @Override
    public Optional<TrackerTransaction> delete(TrackerTransaction transaction) {
        Optional<TrackerTransaction> optional = getById(transaction.getId());
        if (optional.isEmpty()) { return optional; }
        String query = "delete from fin_tracker.ft_transaction where id = ?;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setLong(1, optional.get().getId());
            preparedStatement.executeUpdate();
            return optional;
        } catch (SQLException e) {
            System.out.println("Got SQL Exception while deleting transaction " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<TrackerTransaction> getById(long id) {
        String query = "select * from fin_tracker.ft_transaction where id = ?;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TrackerTransaction> transactions =  parseRS(resultSet);
            return transactions.stream().findFirst();
        } catch (SQLException e) {
            System.out.println("Got SQL Exception while getting transaction by id " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<TrackerTransaction> getAllTransactions() {
        String query = "select * from fin_tracker.ft_transaction;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return parseRS(resultSet);
        } catch (SQLException e) {
            System.out.println("Got SQL Exception while getting all transactions " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Optional<TrackerTransaction> getByUUID(String uuid) {
        String query = "select * from fin_tracker.ft_transaction where uuid = ?;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TrackerTransaction> transactions =  parseRS(resultSet);
            return transactions.stream().findFirst();
        } catch (SQLException e) {
            System.out.println("Got SQL Exception while getting transaction by uuid " + e.getMessage() + " "
                    + e.getStackTrace());
            return Optional.empty();
        }
    }

    private void copyTransactionData(TrackerTransaction storedTransaction, TrackerTransaction newTransaction) {
        if (Objects.nonNull(newTransaction.getAmount())) {
            storedTransaction.setAmount(newTransaction.getAmount());
        }
        if (Objects.nonNull(newTransaction.getType())) {
            storedTransaction.setType(newTransaction.getType());
        }
        if (Objects.nonNull(newTransaction.getCategory())) {
            storedTransaction.setCategory(newTransaction.getCategory());
        }
        if (Objects.nonNull(newTransaction.getDescription())) {
            storedTransaction.setDescription(newTransaction.getDescription());
        }
        if (Objects.nonNull(newTransaction.getDate())) {
            storedTransaction.setDate(newTransaction.getDate());
        }
        if (Objects.nonNull(newTransaction.getUserId())) {
            storedTransaction.setUserId(newTransaction.getUserId());
        }
    }

    private List<TrackerTransaction> parseRS(ResultSet resultSet) throws SQLException{
        List<TrackerTransaction> list = new ArrayList<>();
        while (resultSet.next()) {
            TrackerTransaction transaction = new TrackerTransaction();
            for (int i = 1; i <= dbFields.length; i++) {
                String columnName = dbFields[i - 1];
                switch (columnName) {
                    case "id":
                        transaction.setId(resultSet.getLong(i));
                        break;
                    case "category":
                        transaction.setCategory(resultSet.getString(i));
                        break;
                    case "description":
                        transaction.setDescription(resultSet.getString(i));
                        break;
                    case "type":
                        String value = resultSet.getString(i);
                        Optional<TransactionType> optional = TransactionType.fromString(value);
                        if (optional.isPresent()) {
                            transaction.setType(optional.get());
                        } else {
                            throw new RuntimeException("Database integrity broken. Cannot translate " +
                                    value + " to TransactionType");
                        }
                        break;
                    case "amount": {
                        transaction.setAmount(resultSet.getDouble(i));
                        break;
                    }
                    case "date": {
                        transaction.setDate(resultSet.getDate(i));
                        break;
                    }
                    case "userid":
                        transaction.setUserId(resultSet.getLong(i));
                        break;
                    case "uuid":
                        transaction.setUuid(resultSet.getString(i));
                        break;
                }
            }
            list.add(transaction);
        }
        return list;
    }
}