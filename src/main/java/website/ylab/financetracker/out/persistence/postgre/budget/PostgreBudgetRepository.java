package website.ylab.financetracker.out.persistence.postgre.budget;

import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.out.persistence.BudgetRepository;
import website.ylab.financetracker.out.persistence.postgre.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PostgreBudgetRepository implements BudgetRepository {
    // The sole purpose of this array is to give names to the values while parsing the values
    // retrieved from the database, making the code more understandable.
    private final String[] dbFields = {"id", "bg_limit", "userid", "uuid"};
    private final ConnectionProvider connectionProvider;

    public PostgreBudgetRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Optional<Double> setBudget(TrackerUser user, double limit) {
        deleteBudget(user);
        BudgetEntity budget = new BudgetEntity();
        budget.setUserId(user.getId());
        budget.setLimit(limit);
        budget.setUuid(UUID.randomUUID().toString());
        budget.setLimit(limit);
        Optional<BudgetEntity> result = createBudget(budget);
        return result.map(BudgetEntity::getLimit);
    }

    @Override
    public Optional<Double> getBudget(TrackerUser user) {
        List<BudgetEntity> list = getBudgetByUserId(user.getId());
        Optional<BudgetEntity> optional = list.stream().findFirst();
        return optional.map(BudgetEntity::getLimit);
    }

    @Override
    public Optional<Double> deleteBudget(TrackerUser user) {
        long userid= user.getId();
        Optional<BudgetEntity> optional = getBudgetByUserId(userid).stream().findFirst();
        if (optional.isEmpty()) {
            return Optional.empty(); }
        BudgetEntity budget = optional.get();
        optional = deleteBudget(budget);
        return optional.map(BudgetEntity::getLimit);
    }

    public Optional<BudgetEntity> createBudget(BudgetEntity budget) {
        String querry = "insert into fin_tracker.budget " +
                "(bg_limit, userid, uuid) " +
                "values (?,?,?);";
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(querry, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, budget.getLimit());
            preparedStatement.setLong(2, budget.getUserId());
            preparedStatement.setString(3, budget.getUuid());
            preparedStatement.executeUpdate();
            return getByUUID(budget.getUuid());
        } catch (SQLException e) {
            System.out.println("Got SQL Exception while creating transaction " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<BudgetEntity> deleteBudget(BudgetEntity budget) {
        String query = "delete from fin_tracker.budget where uuid = ?;";
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, budget.getUuid());
            preparedStatement.executeUpdate();
            return Optional.of(budget);
        } catch (SQLException e) {
            System.out.println("Got SQL Exception while deleting transaction " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<BudgetEntity> getBudgetById(long id) {
        String query = "select * from fin_tracker.budget where id = ?;";
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BudgetEntity> list =  parseRS(resultSet);
            return list.stream().findFirst();
        } catch (SQLException e) {
            System.out.println("Got SQL Exception while getting transaction by id " + e.getMessage());
            return Optional.empty();
        }
    }

    public List<BudgetEntity> getBudgetByUserId(long userid) {
        String query = "select * from fin_tracker.budget where userid = ?;";
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, userid);
            ResultSet resultSet = preparedStatement.executeQuery();
            return parseRS(resultSet);
        } catch (SQLException e) {
            System.out.println("Got SQL Exception while getting transaction by id " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Optional<BudgetEntity> getByUUID(String uuid) {
        String query = "select * from fin_tracker.budget where uuid = ?;";
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BudgetEntity> list =  parseRS(resultSet);
            return list.stream().findFirst();
        } catch (SQLException e) {
            System.out.println("Got SQL Exception while getting transaction by id " + e.getMessage());
            return Optional.empty();
        }
    }

    private List<BudgetEntity> parseRS(ResultSet resultSet) throws SQLException{
        List<BudgetEntity> list = new ArrayList<>();
        while (resultSet.next()) {
            BudgetEntity budget = new BudgetEntity();
            for (int i = 1; i <= dbFields.length; i++) {
                String columnName = dbFields[i - 1];
                switch (columnName) {
                    case "id":
                        budget.setId(resultSet.getLong(i));
                        break;
                    case "bg_limit":
                        budget.setLimit(resultSet.getDouble(i));
                        break;
                    case "userid":
                        budget.setUserId(resultSet.getLong(i));
                        break;
                    case "uuid":
                        budget.setUuid(resultSet.getString(i));
                        break;
                }
            }
            list.add(budget);
        }
        return list;
    }
}