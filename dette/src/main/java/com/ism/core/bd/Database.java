package com.ism.core.bd;

import java.sql.*;

public interface Database {
    void OpenConnection();
    void CloseConnection();
    ResultSet executeSelect() throws SQLException;
    int executeUpdate() throws SQLException;
    void initPreparedStatement(String sql) throws SQLException;
} 
