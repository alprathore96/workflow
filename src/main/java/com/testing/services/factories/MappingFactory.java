package com.testing.services.factories;

import com.testing.appConfig.MasterDatabaseConfig;
import com.testing.appConfig.MappingConfig;
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
    private Map<Integer, MappingEntity> idMappings;

    @PostConstruct
    public void initialize() {
        if ( !isInitialized ) {
            synchronized (MappingFactory.class) {
                if ( !isInitialized ) {
                    idMappings = new HashMap<>();
                    try {
                        Connection connection = databaseFactory.getConnection("workflow_data");
                        PreparedStatement statement = connection.prepareStatement(String.format("select * from %s.%s;",
                                mappingConfig.getDatabase(), mappingConfig.getTable()));
                        ResultSet mappingResultSet = dbQuery.query(statement);
                        List<MappingEntity> mappingEntities = resultSetToEntity(mappingResultSet);
                        mappingEntities.forEach(mappingEntity -> idMappings.put(mappingEntity.getId(), mappingEntity));
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

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

    public static List<MappingEntity> getMappingsForGroup(String group) {
        return null;
    }

    public static String getMappingForId(int id) {
        MappingEntity mappingEntity = idMappings.get(id);
        if ( mappingEntity == null || mappingEntity.getMapping() == null ) {
            throw new IllegalArgumentException(String.format("Could not find mapping for id %s.", id));
        }
        return mappingEntity.getMapping();
    }

}
