package website.ylab.financetracker.out.repository.postgre.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import website.ylab.financetracker.service.auth.Role;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.out.repository.TrackerUserRepository;
import website.ylab.financetracker.service.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class PostgresUserRepository implements TrackerUserRepository {
    private final String[] dbFields = {"id", "name", "email", "password", "role", "enabled"};
    private final ConnectionProvider connectionProvider;
    Logger logger = LogManager.getLogger(PostgresUserRepository.class);

    @Autowired
    public PostgresUserRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Optional<TrackerUser> create(TrackerUser user) {
        String query = "insert into fin_tracker.user (name, email, password, role, enabled) " +
                "values(?,?,?,?, ?)";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUsername().toLowerCase());
            preparedStatement.setString(2, user.getEmail().toLowerCase());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getRole().toString());
            preparedStatement.setBoolean(5, user.isEnabled());
            preparedStatement.executeUpdate();
            return getByName(user.getUsername());
        } catch (SQLException e) {
            logger.error("Error creating user: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<TrackerUser> getByName(String username) {
        String query = "select * from fin_tracker.user where name = ?;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, username.toLowerCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TrackerUser> users =  parseRS(resultSet);
            return users.stream().findFirst();
        } catch (SQLException e) {
            logger.error("Error getting user by name: {}", e.getMessage());
                return Optional.empty();
        }
    }

    @Override
    public Optional<TrackerUser> getById(long id) {
        String query = "select * from fin_tracker.user where id = ?;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TrackerUser> users =  parseRS(resultSet);
            return users.stream().findFirst();
        } catch (SQLException e) {
            logger.error("Error getting user by id: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<TrackerUser> update(TrackerUser user) {
        String query = "update fin_tracker.user set name = ?, " +
                "email = ?, password = ?, role = ?, enabled = ? where id = ?;";
        Optional<TrackerUser> optional = getById(user.getId());
        if (optional.isEmpty()) { return optional; }
        TrackerUser storedUser = optional.get();
        copyUserData(storedUser, user);
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, storedUser.getUsername().toLowerCase());
            preparedStatement.setString(2, storedUser.getEmail().toLowerCase());
            preparedStatement.setString(3, storedUser.getPassword());
            preparedStatement.setString(4, storedUser.getRole().toString());
            preparedStatement.setBoolean(5, user.isEnabled());
            preparedStatement.setLong(6, user.getId());
            preparedStatement.executeUpdate();
            return getById(user.getId());
        } catch (SQLException e) {
            logger.error("Error updating user {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<TrackerUser> delete(TrackerUser user) {
        Optional<TrackerUser> optional = getById(user.getId());
        if (optional.isEmpty()) { return optional; }
        String deleteTransactionsQuery = "delete from fin_tracker.ft_transaction where userid = ?;";
        String deleteTargetQuery = "delete from fin_tracker.target where userid = ?;";
        String deleteBudgetQuery = "delete from fin_tracker.budget where userid = ?;";
        String query = "delete from fin_tracker.user where id = ?;";
        try (Connection connection = connectionProvider.getConnection()) {
            connection.setAutoCommit(false);
            try (
            PreparedStatement userStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement transactionStatement
                     = connection.prepareStatement(deleteTransactionsQuery, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement targetStatement
                     = connection.prepareStatement(deleteTargetQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement budgetStatement
                     = connection.prepareStatement(deleteBudgetQuery, Statement.RETURN_GENERATED_KEYS);
             ) {
                connection.setAutoCommit(false);
                userStatement.setLong(1, user.getId());
                userStatement.executeUpdate();
                transactionStatement.setLong(1, user.getId());
                transactionStatement.executeUpdate();
                targetStatement.setLong(1, user.getId());
                targetStatement.executeUpdate();
                budgetStatement.setLong(1, user.getId());
                budgetStatement.executeUpdate();
                connection.commit();
                return optional;
            }
            catch (SQLException e) {
                connection.rollback();
                logger.error("Error deleting user {}", e.getMessage());
            }
        } catch (SQLException e) {
            logger.error("Error obtaining connection while deleting user {}", e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<TrackerUser> getAllUsers() {
        String query = "select * from fin_tracker.user;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return parseRS(resultSet);
        } catch (SQLException e) {
            logger.error("Error while getting user's list: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<TrackerUser> parseRS(ResultSet resultSet) throws SQLException {
        List<TrackerUser> list = new ArrayList<>();
        while (resultSet.next()) {
            TrackerUser user = new TrackerUser();
            for (int i = 1; i <= dbFields.length; i++) {
                String columnName = dbFields[i - 1];
                String value = resultSet.getString(i);
                switch (columnName) {
                    case "id":
                        user.setId(Long.parseLong(value));
                        break;
                    case "name":
                        user.setUsername(value);
                        break;
                    case "email":
                        user.setEmail(value);
                        break;
                    case "password":
                        user.setPassword(value);
                        break;
                    case "role": {
                        Optional<Role> optional = (Role.fromString(value));
                        if (optional.isPresent()) {
                            user.setRole(optional.get());
                        } else {
                            throw new RuntimeException("Database integrity broken. Cannot translate " +
                                    value + " to Role");
                        }
                        break;
                    }
                    case "enabled": {
                        user.setEnabled(sqlToBoolean(value));
                        break;
                    }
                }
            }
            list.add(user);
        }
        return list;
    }

    private void copyUserData(TrackerUser storedUser, TrackerUser newUser) {
        if (Objects.nonNull(newUser.getUsername())) {
            storedUser.setUsername(newUser.getUsername().toLowerCase());
        }
        if (Objects.nonNull(newUser.getPassword())) {
            storedUser.setPassword(newUser.getPassword());
        }
        if (Objects.nonNull(newUser.getEmail())) {
            storedUser.setEmail(newUser.getEmail().toLowerCase());
        }
        if (Objects.nonNull(newUser.getRole())) {
            storedUser.setRole(newUser.getRole());
        }
        storedUser.setEnabled(newUser.isEnabled());
    }

    private Boolean sqlToBoolean(String sql) {
        if (sql.equalsIgnoreCase("t")) return Boolean.TRUE;
        if (sql.equalsIgnoreCase("f")) return Boolean.FALSE;
        return null;
    }
}