package ru.cod331n.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings("ALL")
public class ConnectionPool {
    private static final String JDBC_URL = "jdbc:postgresql://localhost/petsystem";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final int MAX_CONNECTIONS = 10;

    private static ConnectionPool instance;
    private final BlockingQueue<Connection> connectionQueue;

    private ConnectionPool() throws SQLException {
        connectionQueue = new ArrayBlockingQueue<>(MAX_CONNECTIONS);
        initializeConnections();
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            try {
                instance = new ConnectionPool();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    private void initializeConnections() {
        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            Connection connection;
            try {
                connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            connectionQueue.offer(connection);
        }
    }

    public Connection getConnection() {
        try {
            return connectionQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void releaseConnection(Connection connection) {
        connectionQueue.offer(connection);
    }
}
