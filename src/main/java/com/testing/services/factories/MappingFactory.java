package com.testing.services.factories;

import com.testing.appConfig.DatabaseConfig;
import com.testing.appConfig.MappingConfig;
import com.testing.models.databaseModels.MappingEntity;
import com.testing.services.databaseServices.DbQuery;
import com.testing.services.databaseServices.impl.DbOperations;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MappingFactory {

    private static final Logger LOGGER = Logger.getLogger(MappingFactory.class);
    private volatile boolean isInitialized = false;

    private static Map<String, List<MappingEntity>> workflowMappings;
    private static Map<Integer, MappingEntity> idMappings;
    private DbQuery dbQuery;

    @Autowired
    DatabaseConfig databaseConfig;
    @Autowired
    MappingConfig mappingConfig;


    static {
        workflowMappings = new HashMap<>();
        idMappings = new HashMap<>();
    }


//    @PostConstruct
    public void initialize() {
        if ( !isInitialized ) {
            synchronized (MappingFactory.class) {
                if ( !isInitialized ) {
                    try {
                        dbQuery = new DbOperations();
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection connection = DriverManager.getConnection(databaseConfig.getUrl(), databaseConfig.getUser()
                                , databaseConfig.getPassword());
                        PreparedStatement statement = connection.prepareStatement(String.format("select * from %s.%s;",
                                mappingConfig.getDatabase(), mappingConfig.getTable()));
                        ResultSet mappingResultSet = dbQuery.query(statement);
                        List<MappingEntity> mappingEntities = resultSetToEntity(mappingResultSet);
                        mappingEntities.forEach(mappingEntity -> {
                            idMappings.put(mappingEntity.getId(), mappingEntity);
                            workflowMappings.putIfAbsent(mappingEntity.getWorkflowId(), new ArrayList<>());
                            workflowMappings.get(mappingEntity.getWorkflowId()).add(mappingEntity);
                        });
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
            mappingEntity.setWorkflowId(resultSet.getString("workflow_id"));
            mappingEntity.setMapping(resultSet.getString("mapping"));
            mappingEntities.add(mappingEntity);
        }
        return mappingEntities;
    }

    public static String getMappingForWorkflow(String id) {
        List<MappingEntity> mappingEntities = workflowMappings.get(id);
        if ( mappingEntities == null || mappingEntities.size() != 1 || mappingEntities.get(0) == null ) {
            throw new IllegalArgumentException(String.format("Could not find default mapping for workflow id %s", id));
        }
        return mappingEntities.get(0).getMapping();
    }

    public static String getMappingForId(int id) {
        MappingEntity mappingEntity = idMappings.get(id);
        if ( mappingEntity == null || mappingEntity.getMapping() == null ) {
            throw new IllegalArgumentException(String.format("Could not find mapping for id %s.", id));
        }
        return mappingEntity.getMapping();
    }

}
