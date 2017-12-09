package com.testing.models.databaseModels;

/**
 * Created by Alpesh Rathore on 12/9/2017.
 */
public class DatabaseConfigEntity {
    private int id;
    private String name;
    private String url;
    private String databaseName;
    private String userName;
    private String password;
    private int poolSize;

    public DatabaseConfigEntity() {
    }

    public DatabaseConfigEntity(int id, String name, String url, String databaseName, String userName, String password, int poolSize) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
        this.poolSize = poolSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = "jdbc:mysql://" + url;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }
}
