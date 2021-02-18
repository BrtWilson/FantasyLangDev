package edu.byu.cs.tweeter.util;

public class StringParser {
    public static String[] parseBySpaces(String str) {
        return str.split("\\s+");
    }
}
