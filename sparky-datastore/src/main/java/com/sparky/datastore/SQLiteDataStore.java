package com.sparky.datastore;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import com.sparky.core.SparkyLogger;

/**
 * Реалізація DataStore на основі SQLite.
 *
 * @author Богдан Кравчук
 */
public class SQLiteDataStore implements DataStore {
    private final SparkyLogger logger = SparkyLogger.getLogger(SQLiteDataStore.class);
    
    private final File dbFile;
    private Connection connection;
    
    public SQLiteDataStore(File dbFile) {
        this.dbFile = dbFile;
        initializeDatabase();
    }
    
    @Override
    public void put(String key, Object value) {
        try {
            String sql = "INSERT OR REPLACE INTO data (key, value) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, key);
                stmt.setString(2, value.toString());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Failed to put data in SQLite database: " + key, e);
        }
    }
    
    @Override
    public <T> Optional<T> get(String key, Class<T> type) {
        try {
            String sql = "SELECT value FROM data WHERE key = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, key);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String value = rs.getString("value");
                        return Optional.of(type.cast(convertStringToType(value, type)));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Failed to get data from SQLite database: " + key, e);
        }
        return Optional.empty();
    }
    
    @Override
    public void remove(String key) {
        try {
            String sql = "DELETE FROM data WHERE key = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, key);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Failed to remove data from SQLite database: " + key, e);
        }
    }
    
    @Override
    public boolean contains(String key) {
        try {
            String sql = "SELECT COUNT(*) FROM data WHERE key = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, key);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next() && rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to check if key exists in SQLite database: " + key, e);
            return false;
        }
    }
    
    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Failed to close SQLite database connection", e);
        }
    }
    
    private void initializeDatabase() {
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            
            // Create connection
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
            
            // Create table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS data (" +
                    "key TEXT PRIMARY KEY, " +
                    "value TEXT NOT NULL)";
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(createTableSQL);
            }
        } catch (Exception e) {
            logger.error("Failed to initialize SQLite database: " + dbFile.getAbsolutePath(), e);
        }
    }
    
    private Object convertStringToType(String value, Class<?> type) {
        if (type == String.class) {
            return value;
        } else if (type == Integer.class || type == int.class) {
            return Integer.parseInt(value);
        } else if (type == Long.class || type == long.class) {
            return Long.parseLong(value);
        } else if (type == Double.class || type == double.class) {
            return Double.parseDouble(value);
        } else if (type == Boolean.class || type == boolean.class) {
            return Boolean.parseBoolean(value);
        } else {
            // For other types, return as string
            return value;
        }
    }
}