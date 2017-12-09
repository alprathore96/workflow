package com.testing.services.databaseServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface DbQuery {
    ResultSet query(String query, Map<String, Object> params) throws SQLException;
}
