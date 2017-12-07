package com.testing.commons;

public class Utils {
    public static String extractFirstKey(String from, String sequence) {
        return from.substring(0, from.indexOf(sequence));
    }
}
