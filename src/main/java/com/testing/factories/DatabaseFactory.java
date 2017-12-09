package com.testing.factories;

import com.testing.appConfig.MasterDatabaseConfig;
import com.testing.models.databaseModels.DatabaseConfigEntity;
import com.testing.services.databaseServices.impl.DbOperations;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alpesh Rathore on 12/9/2017.
 */
@Repository
public class DatabaseFactory {

    @Autowired
    private MasterDatabaseConfig masterDatabaseConfig;
    @Autowired
    private DbOperations dbOperations;

    private Map<String, DataSource> namedDatasources;

    @PostConstruct
    public void init() throws SQLException, ClassNotFoundException {
        namedDatasources = new HashMap<>();
        initialize();
    }

    private void initialize() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(masterDatabaseConfig.getUrl(), masterDatabaseConfig.getUser()
                , masterDatabaseConfig.getPassword());
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM database_config;");
        ResultSet resultSet = dbOperations.query(statement);
        List<DatabaseConfigEntity> databaseConfigEntities = resultSetToDatabaseEntities(resultSet);
        for ( DatabaseConfigEntity databaseConfigEntity : databaseConfigEntities ) {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(databaseConfigEntity.getUrl() + "/" + databaseConfigEntity.getDatabaseName());
            hikariConfig.setUsername(databaseConfigEntity.getUserName());
            hikariConfig.setPassword(databaseConfigEntity.getPassword());
            hikariConfig.setMaximumPoolSize(databaseConfigEntity.getPoolSize());
            namedDatasources.put(databaseConfigEntity.getName(), new HikariDataSource(hikariConfig));
        }
        connection.close();
    }

    private static List<DatabaseConfigEntity> resultSetToDatabaseEntities(ResultSet resultSet) throws SQLException {
        List<DatabaseConfigEntity> databaseConfigEntities = new ArrayList<>();
        while (resultSet.next()) {
            DatabaseConfigEntity databaseConfigEntity = new DatabaseConfigEntity();
            databaseConfigEntity.setId(resultSet.getInt("id"));
            databaseConfigEntity.setName(resultSet.getString("name"));
            databaseConfigEntity.setUrl(resultSet.getString("url"));
            databaseConfigEntity.setDatabaseName(resultSet.getString("database_name"));
            databaseConfigEntity.setUserName(resultSet.getString("user_name"));
            databaseConfigEntity.setPassword(resultSet.getString("password"));
            databaseConfigEntities.add(databaseConfigEntity);
        }
        return databaseConfigEntities;
    }

    public Connection getConnection(String name) throws SQLException {
        DataSource dataSource = namedDatasources.get(name);
        if ( dataSource == null ) {
            return null;
        }
        return dataSource.getConnection();
    }
}
