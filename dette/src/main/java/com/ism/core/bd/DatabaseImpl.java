package com.ism.core.bd;

import java.sql.*;

public class DatabaseImpl implements Database {

    private static final String url = "jdbc:postgresql://localhost:5432/awa";
    private static final String username = "postgres"; 
    private static final String password = "awa1"; 

    protected Connection connection;
    public PreparedStatement statement;

    @Override
    public void OpenConnection() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            this.connection = DriverManager.getConnection(url,username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void CloseConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet executeSelect() throws SQLException {
        return statement.executeQuery();
    }

    @Override
    public int executeUpdate() throws SQLException {
        return statement.executeUpdate();
    }

    @Override
    public void initPreparedStatement(String sql) throws SQLException {
        this.OpenConnection();
        if (sql.trim().toLowerCase().startsWith("insert")) {
            statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        }else{
        statement = connection.prepareStatement(sql);
        }

    }

}
