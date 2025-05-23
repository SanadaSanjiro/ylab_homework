package website.ylab.financetracker.out.repository.postgre.target;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import website.ylab.financetracker.out.repository.TargetRepository;
import website.ylab.financetracker.service.ConnectionProvider;
import website.ylab.financetracker.service.targets.TrackerTarget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostgresTargetRepository implements TargetRepository {
    private final String[] dbFields = {"id", "amount", "userid", "uuid"};
    private final ConnectionProvider connectionProvider;
    private final TargetEntityMapper mapper = TargetEntityMapper.INSTANCE;
    Logger logger = LogManager.getLogger(PostgresTargetRepository.class);

    @Autowired
    public PostgresTargetRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Optional<TrackerTarget> setTarget(TrackerTarget target) {
        long userId = target.getUserId();
        Optional<TrackerTarget> optional = getByUserId(userId);
        optional.ifPresent(trackerTarget -> deleteTarget(trackerTarget.getId()));
        TargetEntity entity = new TargetEntity();
        entity.setUserId(target.getUserId());
        entity.setAmount(target.getAmount());
        entity.setUuid(target.getUuid());
        Optional<TargetEntity> optionalEntity = createTarget(entity);
        return optionalEntity.map(mapper::toTarget);
    }

    @Override
    public Optional<TrackerTarget> getById(long id) {
        Optional<TargetEntity> optional = getTargetById(id);
        return optional.map(mapper::toTarget);
    }

    @Override
    public Optional<TrackerTarget> deleteTarget(long id) {
        Optional<TargetEntity> optional = getTargetById(id);
        if (optional.isEmpty()) {
            return Optional.empty(); }
        TargetEntity target = optional.get();
        optional = deleteTarget(target);
        return optional.map(mapper::toTarget);
    }

    @Override
    public Optional<TrackerTarget> getByUserId(long userid) {
        String query = "select * from fin_tracker.target where userid = ?;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, userid);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TargetEntity> entities = parseRS(resultSet);
            if (entities.isEmpty()) return Optional.empty();
            return (Optional.of(mapper.toTarget(entities.get(0))));
        } catch (SQLException e) {
            logger.error("Error getting target by user id: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<TargetEntity> createTarget(TargetEntity target) {
        String query = "insert into fin_tracker.target " +
                "(amount, userid, uuid) " +
                "values (?,?,?);";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, target.getAmount());
            preparedStatement.setLong(2, target.getUserId());
            preparedStatement.setString(3, target.getUuid());
            preparedStatement.executeUpdate();
            return getByUUID(target.getUuid());
        } catch (SQLException e) {
            logger.error("Error creating target: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<TargetEntity> deleteTarget(TargetEntity target) {
        String query = "delete from fin_tracker.target where uuid = ?;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, target.getUuid());
            preparedStatement.executeUpdate();
            return Optional.of(target);
        } catch (SQLException e) {
            logger.error("Error deleting target: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<TargetEntity> getTargetById(long id) {
        String query = "select * from fin_tracker.target where id = ?;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TargetEntity> list =  parseRS(resultSet);
            return list.stream().findFirst();
        } catch (SQLException e) {
            logger.error("Error getting target by id: {}", e.getMessage());
            return Optional.empty();
        }
    }


    public Optional<TargetEntity> getByUUID(String uuid) {
        String query = "select * from fin_tracker.target where uuid = ?;";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TargetEntity> list =  parseRS(resultSet);
            return list.stream().findFirst();
        } catch (SQLException e) {
            logger.error("Error getting target by uuid: {}", e.getMessage());
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