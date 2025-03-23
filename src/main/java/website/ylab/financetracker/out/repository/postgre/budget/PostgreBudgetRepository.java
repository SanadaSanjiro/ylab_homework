package website.ylab.financetracker.out.repository.postgre.budget;

import website.ylab.financetracker.out.repository.BudgetRepository;
import website.ylab.financetracker.util.ConnectionProvider;
import website.ylab.financetracker.service.budget.TrackerBudget;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgreBudgetRepository implements BudgetRepository {
    // The sole purpose of this array is to give names to the values while parsing the values
    // retrieved from the database, making the code more understandable.
    private final String[] dbFields = {"id", "bg_limit", "userid", "uuid"};
    private final ConnectionProvider connectionProvider;
    private final BudgetEntityMapper mapper = BudgetEntityMapper.INSTANCE;

    public PostgreBudgetRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Optional<TrackerBudget> setBudget(TrackerBudget budget) {
        deleteBudget(budget.getId());
        BudgetEntity entity = new BudgetEntity();
        entity.setUserId(budget.getUserId());
        entity.setLimit(budget.getLimit());
        entity.setUuid(budget.getUuid());
        Optional<BudgetEntity> optional = createBudget(entity);
        return optional.map(mapper::toBudget);
    }

    @Override
    public Optional<TrackerBudget> getById(long id) {
        Optional<BudgetEntity> optional = getBudgetById(id);
        return optional.map(mapper::toBudget);
    }

    @Override
    public Optional<TrackerBudget> deleteBudget(long id) {
        Optional<BudgetEntity> optional = getBudgetById(id);
        if (optional.isEmpty()) {
            return Optional.empty(); }
        BudgetEntity budget = optional.get();
        optional = deleteBudget(budget);
        return optional.map(mapper::toBudget);
    }

    @Override
    public Optional<TrackerBudget> getByUserId(long userId) {
        String query = "select * from fin_tracker.budget where userid = ?;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BudgetEntity> entities = parseRS(resultSet);
            if (entities.isEmpty()) return Optional.empty();
            return (Optional.of(mapper.toBudget(entities.get(1))));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    public Optional<BudgetEntity> createBudget(BudgetEntity budget) {
        String querry = "insert into fin_tracker.budget " +
                "(bg_limit, userid, uuid) " +
                "values (?,?,?);";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(querry, Statement.RETURN_GENERATED_KEYS);) {
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
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
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
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
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
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
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
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
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