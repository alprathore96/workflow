package com.testing.services.factories;

import com.testing.appConfig.MasterDatabaseConfig;
import com.testing.appConfig.TablesConfig;
import com.testing.commons.Constants;
import com.testing.factories.DatabaseFactory;
import com.testing.models.databaseModels.MappingEntity;
import com.testing.services.databaseServices.DbQuery;
import com.testing.services.databaseServices.impl.DbOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MappingFactory {

    private static final Logger LOGGER = Logger.getLogger(MappingFactory.class);
    private volatile boolean isInitialized = false;

    @Autowired
    private DbQuery dbQuery;
    @Autowired
    private DatabaseFactory databaseFactory;
    @Autowired
    private TablesConfig tablesConfig;

    private List<MappingEntity> resultSetToEntity(ResultSet resultSet) throws SQLException {
        List<MappingEntity> mappingEntities = new ArrayList<>();
        while(resultSet.next()) {
            MappingEntity mappingEntity = new MappingEntity();
            mappingEntity.setId(resultSet.getInt("id"));
            mappingEntity.setMapping(resultSet.getString("mapping"));
            mappingEntity.setGroup(resultSet.getString("group"));
            mappingEntities.add(mappingEntity);
        }
        return mappingEntities;
    }

    public List<String> getMappingsForGroup(String group) {
        try {
            Connection connection = databaseFactory.getConnection(Constants.WORKFLOW_DATABASE_NAME);
            PreparedStatement preparedStatement = connection.prepareStatement("");
        } catch (SQLException e) {
            LOGGER.warn(String.format("Could not fetch mappings for group %s due to exception: ", group), e);
        }
    }

}
