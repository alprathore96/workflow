package com.testing.services.databaseServices;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DbUpdate {
    int update(PreparedStatement preparedStatement) throws SQLException;
}
