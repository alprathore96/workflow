package com.testing.appConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MappingConfig {

    @Value(value = "${mapping.db}")
    private String database;

    @Value(value = "${mapping.table}")
    private String table;

    @Value(value = "${mapping.column}")
    private String column;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }
}
