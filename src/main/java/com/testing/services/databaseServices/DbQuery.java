package com.testing.services.databaseServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DbQuery {
    ResultSet query(PreparedStatement preparedStatement) throws SQLException;
}
