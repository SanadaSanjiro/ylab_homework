package website.ylab.financetracker.out.persistence.postgre.auth;

import website.ylab.financetracker.auth.Role;
import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.out.persistence.TrackerUserRepository;
import website.ylab.financetracker.out.persistence.postgre.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PostgreUserRepository implements TrackerUserRepository {
    // The sole purpose of this array is to give names to the values while parsing the values
    // retrieved from the database, making the code more understandable.
    private final String[] dbFields = {"id", "name", "email", "password", "role", "enabled"};
    private final ConnectionProvider connectionProvider;

    public PostgreUserRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        addTestUser();
    }

    @Override
    public Optional<TrackerUser> create(TrackerUser user) {
        String querry = "insert into fin_tracker.user (name, email, password, role, enabled) " +
                "values(?,?,?,?, ?)";
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(querry, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername().toLowerCase());
            preparedStatement.setString(2, user.getEmail().toLowerCase());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getRole().toString());
            preparedStatement.setBoolean(5, user.isEnabled());
            preparedStatement.executeUpdate();
            return getByName(user.getUsername());
        } catch (SQLException e) {
            System.out.println("Got SQL Exception in transaction " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<TrackerUser> getByName(String username) {
        String query = "select * from fin_tracker.user where name = ?;";
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username.toLowerCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TrackerUser> users =  parseRS(resultSet);
            Optional<TrackerUser> optional = users.stream().findFirst();
            return optional;
        } catch (SQLException e) {
                System.out.println("Got SQL Exception in transaction " + e.getMessage());
                return Optional.empty();
        }
    }

    @Override
    public Optional<TrackerUser> getById(long id) {
        String query = "select * from fin_tracker.user where id = ?;";
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TrackerUser> users =  parseRS(resultSet);
            return users.stream().findFirst();
        } catch (SQLException e) {
            System.out.println("Got SQL Exception in transaction " + e.getMessage());
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
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, storedUser.getUsername().toLowerCase());
            preparedStatement.setString(2, storedUser.getEmail().toLowerCase());
            preparedStatement.setString(3, storedUser.getPassword());
            preparedStatement.setString(4, storedUser.getRole().toString());
            preparedStatement.setBoolean(5, user.isEnabled());
            preparedStatement.setLong(6, user.getId());
            preparedStatement.executeUpdate();
            return getById(user.getId());
        } catch (SQLException e) {
            System.out.println("Got SQL Exception in transaction " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<TrackerUser> delete(TrackerUser user) {
        Optional<TrackerUser> optional = getById(user.getId());
        if (optional.isEmpty()) { return optional; }
        String query = "delete from fin_tracker.user where id = ?;";
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
            return optional;
        } catch (SQLException e) {
            System.out.println("Got SQL Exception in transaction " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<TrackerUser> getAllUsers() {
        String query = "select * from fin_tracker.user;";
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.executeQuery();
            return parseRS(resultSet);
        } catch (SQLException e) {
            System.out.println("Got SQL Exception in transaction " + e.getMessage());
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

    private void addTestUser() {
        if (getByName("admin").isEmpty()) {
            TrackerUser admin = new TrackerUser("admin", "admin@admin.com", "123");
            admin.setRole(Role.ADMIN);
            admin.setEnabled(true);
            create(admin);
        }
    }
}