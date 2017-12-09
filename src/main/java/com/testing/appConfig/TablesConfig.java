package com.testing.appConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * Created by Alpesh Rathore on 12/9/2017.
 */
@Service
@PropertySource("classpath:application.properties")
public class TablesConfig {

    @Value("${tables.database_config}")
    private String databaseConfig;

    @Value("${tables.mappings}")
    private String mappings;

    public String getDatabaseConfig() {
        return databaseConfig;
    }

    public void setDatabaseConfig(String databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public String getMappings() {
        return mappings;
    }

    public void setMappings(String mappings) {
        this.mappings = mappings;
    }
}
