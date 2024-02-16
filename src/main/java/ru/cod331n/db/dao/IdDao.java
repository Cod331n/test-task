package ru.cod331n.db.dao;

import ru.cod331n.db.ConnectionPool;
import ru.cod331n.id.Id;
import ru.cod331n.mapper.ResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class IdDao {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ids (id BIGINT PRIMARY KEY);" +
            "INSERT INTO ids (id) VALUES (0);";
    private static final String SELECT_CURRENT_ID = "SELECT id FROM ids ORDER BY id DESC LIMIT 1";
    private static final String UPDATE_ID = "UPDATE ids SET id=? WHERE id=?";

    public IdDao createTable() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE)) {
            preparedStatement.executeUpdate();
            return this;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Optional<Long> getCurrentId() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CURRENT_ID)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getLong("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void updateCurrentId(long newId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ID)) {

            preparedStatement.setLong(1, newId);
            preparedStatement.setLong(2, getCurrentId().orElse(-1L));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
