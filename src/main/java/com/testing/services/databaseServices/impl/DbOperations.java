package com.testing.services.databaseServices.impl;

import com.testing.appConfig.DatabaseConfig;
import com.testing.appConfig.DatabasePoolConfig;
import com.testing.services.databaseServices.DbInsert;
import com.testing.services.databaseServices.DbQuery;
import com.testing.services.databaseServices.DbUpdate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DbOperations implements DbQuery, DbInsert, DbUpdate {

    private static final Logger LOGGER = Logger.getLogger(DbOperations.class);

    @Autowired
    DatabaseConfig databaseConfig;
    @Autowired
    DatabasePoolConfig databasePoolConfig;

    @Override
    public List<Integer> insert(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        List<Integer> keys = new ArrayList<>();
        while(generatedKeys.next()) {
            keys.add(generatedKeys.getInt(1));
        }
        return keys;
    }

    @Override
    public ResultSet query(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    @Override
    public int update(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeUpdate();
    }
}
