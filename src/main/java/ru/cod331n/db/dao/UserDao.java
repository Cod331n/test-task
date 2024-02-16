package ru.cod331n.db.dao;

import ru.cod331n.db.ConnectionPool;
import ru.cod331n.mapper.ResultSetMapper;
import ru.cod331n.user.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<UserDTO> {
    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "age INT NOT NULL," +
                    "sex VARCHAR(255) NOT NULL" +
                    ")";
    private static final String SELECT_BY_ID = "SELECT * FROM users WHERE id=?";
    private static final String SELECT_ALL = "SELECT * FROM users";
    private static final String INSERT_USER = "INSERT INTO users (id, name, age, sex) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE users SET name=?, age=?, sex=? WHERE id=?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id=?";

    @Override
    public void createTable() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE)) {
            preparedStatement.executeUpdate();

            ConnectionPool.getInstance().releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<UserDTO> get(long id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(ResultSetMapper.toUserDTO(resultSet));
                }
            }

            ConnectionPool.getInstance().releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<UserDTO> getAll() {
        List<UserDTO> users = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                users.add(ResultSetMapper.toUserDTO(resultSet));
            }

            ConnectionPool.getInstance().releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void save(UserDTO userDTO) {
//        Thread.ofVirtual().start(() -> {
            try (Connection connection = ConnectionPool.getInstance().getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {

                preparedStatement.setLong(1, userDTO.id());
                preparedStatement.setString(2, userDTO.name());
                preparedStatement.setInt(3, userDTO.age());
                preparedStatement.setString(4, userDTO.sex().name());

                preparedStatement.executeUpdate();

                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
//        });
    }

    @Override
    public void update(long id, UserDTO userDTO) {
//        Thread.ofVirtual().start(() -> {
            try (Connection connection = ConnectionPool.getInstance().getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {

                preparedStatement.setString(1, userDTO.name());
                preparedStatement.setInt(2, userDTO.age());
                preparedStatement.setString(3, userDTO.sex().name());
                preparedStatement.setLong(4, id);

                preparedStatement.executeUpdate();

                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
//        });
    }

    @Override
    public void delete(UserDTO userDTO) {
//        Thread.ofVirtual().start(() -> {
            try (Connection connection = ConnectionPool.getInstance().getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {

                preparedStatement.setLong(1, userDTO.id());

                preparedStatement.executeUpdate();

                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
//        });
    }
}
