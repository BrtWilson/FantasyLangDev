package edu.byu.cs.client.model.utils;

public class StringParser {
    public static String[] parseBySpaces(String str) {
        return str.split("\\s+");
    }
}
