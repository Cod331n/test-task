package ru.cod331n.db.dao;

import ru.cod331n.db.ConnectionPool;
import ru.cod331n.mapper.ResultSetMapper;
import ru.cod331n.pet.dto.PetDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PetDao implements Dao<PetDTO> {
    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS pets (" +
                    "id BIGINT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "age INT NOT NULL," +
                    "aliases VARCHAR(255) ARRAY NOT NULL," +
                    "type VARCHAR(255) NOT NULL" +
                    ")";
    private static final String SELECT_BY_ID = "SELECT * FROM pets WHERE id=?";
    private static final String SELECT_ALL = "SELECT * FROM pets";
    private static final String INSERT_PET = "INSERT INTO pets (id, name, age, aliases, type) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_PET = "UPDATE pets SET name=?, age=?, aliases=?, type=? WHERE id=?";
    private static final String DELETE_PET = "DELETE FROM pets WHERE id=?";

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
    public Optional<PetDTO> get(long id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(ResultSetMapper.toPetDTO(resultSet));
                }
            }

            ConnectionPool.getInstance().releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<PetDTO> getAll() {
        List<PetDTO> pets = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                pets.add(ResultSetMapper.toPetDTO(resultSet));
            }

            ConnectionPool.getInstance().releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pets;
    }

    @Override
    public void save(PetDTO petDTO) {
//        Thread.ofVirtual().start(() -> {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PET)) {

            preparedStatement.setLong(1, petDTO.id());
            preparedStatement.setString(2, petDTO.name());
            preparedStatement.setInt(3, petDTO.age());
            preparedStatement.setArray(4, convertSetToArray(connection, petDTO.aliases()));
            preparedStatement.setString(5, petDTO.type().name());

            preparedStatement.executeUpdate();

            ConnectionPool.getInstance().releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        });

    }

    @Override
    public void update(long id, PetDTO petDTO) {
//        Thread.ofVirtual().start(() -> {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PET)) {

            preparedStatement.setString(1, petDTO.name());
            preparedStatement.setInt(2, petDTO.age());
            preparedStatement.setArray(3, convertSetToArray(connection, petDTO.aliases()));
            preparedStatement.setString(4, petDTO.type().name());
            preparedStatement.setLong(5, id);

            preparedStatement.executeUpdate();

            ConnectionPool.getInstance().releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        });
    }

    @Override
    public void delete(PetDTO petDTO) {
//        Thread.ofVirtual().start(() -> {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PET)) {

            preparedStatement.setLong(1, petDTO.id());

            preparedStatement.executeUpdate();

            ConnectionPool.getInstance().releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        });
    }

    private Array convertSetToArray(Connection connection, Set<String> set) throws SQLException {
        String[] arrayValues = set.toArray(new String[0]);
        return connection.createArrayOf("VARCHAR", arrayValues);
    }
}
