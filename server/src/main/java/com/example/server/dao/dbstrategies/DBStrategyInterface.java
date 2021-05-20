package com.example.server.dao.dbstrategies;

import java.util.List;
import java.util.Map;

public interface DBStrategyInterface {

    boolean insertItem(String tableName, Map<String, String> attributesToInsert);

    Map<String, String> getItem(String tableName, String attributeName, String attributeValue);

    List<Map<String, String>> queryListItems(String tableName, Map<String, String> queryAttributes);

    Map<String, String> querySingleItem(String tableName, Map<String, String> queryAttributes);

    boolean updateItem(String tableName, Map<String, String> queryAttributes, Map<String, String> updateAttributes);
}
