package website.ylab.financetracker.out.persistence.postgre.target;

import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.out.persistence.TargetRepository;
import website.ylab.financetracker.out.persistence.postgre.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PostgresTargetRepository implements TargetRepository {
    // The sole purpose of this array is to give names to the values while parsing the values
    // retrieved from the database, making the code more understandable.
    private final String[] dbFields = {"id", "amount", "userid", "uuid"};
    private final ConnectionProvider connectionProvider;

    public PostgresTargetRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Optional<Double> setTarget(TrackerUser user, double amount) {
        deleteTarget(user);
        TargetEntity target = new TargetEntity();
        target.setUserId(user.getId());
        target.setAmount(amount);
        target.setUuid(UUID.randomUUID().toString());
        Optional<TargetEntity> result = createTarget(target);
        return result.map(TargetEntity::getAmount);
    }

    @Override
    public Optional<Double> getTarget(TrackerUser user) {
        List<TargetEntity> list = getTargetByUserId(user.getId());
        Optional<TargetEntity> optional = list.stream().findFirst();
        return optional.map(TargetEntity::getAmount);
    }

    @Override
    public Optional<Double> deleteTarget(TrackerUser user) {
        long userid= user.getId();
        Optional<TargetEntity> optional = getTargetByUserId(userid).stream().findFirst();
        if (optional.isEmpty()) {
            System.out.println("Цель для пользователя " + userid + " не найдена" );
            return Optional.empty(); }
        TargetEntity target = optional.get();
        System.out.println("Цель для удаления: " + target);
        optional = deleteTarget(target);
        System.out.println(optional.isPresent());
        return optional.map(TargetEntity::getAmount);
    }

    public Optional<TargetEntity> createTarget(TargetEntity target) {
        String querry = "insert into fin_tracker.target " +
                "(amount, userid, uuid) " +
                "values (?,?,?);";
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(querry, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, target.getAmount());
            preparedStatement.setLong(2, target.getUserId());
            preparedStatement.setString(3, target.getUuid());
            preparedStatement.executeUpdate();
            return getByUUID(target.getUuid());
        } catch (SQLException e) {
            System.out.println("Got SQL Exception while creating transaction " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<TargetEntity> deleteTarget(TargetEntity target) {
        String query = "delete from fin_tracker.target where uuid = ?;";
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, target.getUuid());
            preparedStatement.executeUpdate();
            return Optional.of(target);
        } catch (SQLException e) {
            System.out.println("Got SQL Exception while deleting transaction " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<TargetEntity> getTargetById(long id) {
        String query = "select * from fin_tracker.target where id = ?;";
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TargetEntity> list =  parseRS(resultSet);
            return list.stream().findFirst();
        } catch (SQLException e) {
            System.out.println("Got SQL Exception while getting transaction by id " + e.getMessage());
            return Optional.empty();
        }
    }

    public List<TargetEntity> getTargetByUserId(long userid) {
        String query = "select * from fin_tracker.target where userid = ?;";
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

    public Optional<TargetEntity> getByUUID(String uuid) {
        String query = "select * from fin_tracker.target where uuid = ?;";
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TargetEntity> list =  parseRS(resultSet);
            return list.stream().findFirst();
        } catch (SQLException e) {
            System.out.println("Got SQL Exception while getting transaction by id " + e.getMessage());
            return Optional.empty();
        }
    }

    private List<TargetEntity> parseRS(ResultSet resultSet) throws SQLException{
        List<TargetEntity> list = new ArrayList<>();
        while (resultSet.next()) {
            TargetEntity target = new TargetEntity();
            for (int i = 1; i <= dbFields.length; i++) {
                String columnName = dbFields[i - 1];
                switch (columnName) {
                    case "id":
                        target.setId(resultSet.getLong(i));
                        break;
                    case "amount":
                        target.setAmount(resultSet.getDouble(i));
                        break;
                    case "userid":
                        target.setUserId(resultSet.getLong(i));
                        break;
                    case "uuid":
                        target.setUuid(resultSet.getString(i));
                        break;
                }
            }
            list.add(target);
        }
        return list;
    }
}
