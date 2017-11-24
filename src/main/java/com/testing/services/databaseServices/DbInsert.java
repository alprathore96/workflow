package com.testing.services.databaseServices;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface DbInsert {
    List<Integer> insert(PreparedStatement preparedStatement) throws SQLException;
}
