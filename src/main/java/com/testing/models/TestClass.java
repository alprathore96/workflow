package com.testing.models;

import java.util.Map;

/**
 * Created by Alpesh Rathore on 12/8/2017.
 */
public class TestClass {
    private int x;
    private String y;
    private Map<String, Map<String, TestClass>> map;

    public String getStr(String msg) {
        return "hello " + msg;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public Map<String, Map<String, TestClass>> getMap() {
        return map;
    }

    public void setMap(Map<String, Map<String, TestClass>> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "x=" + x +
                ", y='" + y + '\'' +
                ", map=" + map +
                '}';
    }
}
