package com.example.server.dao.dbstrategies;

import java.util.List;
import java.util.Map;

public interface DBStrategyInterface {

    boolean insertItem(String tableName, Map<String, String> attributesToInsert);
    // ::> Add identifier of key(s)?

    Map<String, String> getItem(String tableName, String attributeName, String attributeValue);
    // ::> this will be either by key or index

    /**
     * For queries that know the list won't be huge
     * @param tableName which table being queried
     * @param queryAttributes the column names and corresponding values being queried
     * @return list of items (each row returned from the query matches an item)
     */
    List<Map<String, String>> queryListItems(String tableName, Map<String, String> queryAttributes);
    //::> differentiate query/getItem by key, composite key, or index: have a String indicating this type
        // Probably boolean "isByIndex"

    /**
     * An ordered query according to a specified attribute, requesting a certain number of items at a time
     * @param tableName which table being queried
     * @param primaryKey any key attributes to match
     * @param keyValue additional attributes to match in query;
     * @param pageSize how many items to return
     * @param byAttribute attribute in table to order the query by, *Assumed to be the second part of the composite key
     *                     by this method.
     * @param lastRetrieved what was the last value of the ordered attribute, in order to pick up from there
     * @return ResultsPage contains a List of Strings and a String entry of the next lastRetrieved value
     */
    ResultsPage keyAndCompositePageQuery(String tableName, String primaryKey, String keyValue, int pageSize, String byAttribute, String lastRetrieved);


    /**
     * An ordered query according to a specified attribute, requesting a certain number of items at a time
     * @param tableName which table being queried
     * @param primaryKey any key attributes to match
     * @param keyValue additional attributes to match in query;
     * @param queryAttributes the attribute names and values to include in the query
     * @param includesKey boolean to indicate whether the first of the queryAttributes is a secondary-key
     * @param pageSize how many items to return
     * @param byAttribute attribute in table to order the query by, *Assumed to be the second part of the composite key
     *                     by this method.
     * @param lastRetrieved what was the last value of the ordered attribute, in order to pick up from there
     * @return ResultsPage contains a List of Strings and a String entry of the next lastRetrieved value
     */
    ResultsPage keyAndAttributesPageQuery(String tableName, String primaryKey, String keyValue, Map<String, String> queryAttributes, boolean includesKey, int pageSize, String byAttribute, String lastRetrieved);

    Map<String, String> querySingleItem(String tableName, Map<String, String> queryAttributes);
    //::> differentiate query/getItem by key, composite key, or index

    boolean updateItem(String tableName, Map<String, String> queryAttributes, Map<String, String> updateAttributes);
    //::> this will be either key or composite key: check map size for this

    Map<String, String> getItem(String tableName, Map<String, String> queryAttributes);
    //::> this will be either key or composite key: check map size for this
        // Probably Composite key (see other getItem)

    boolean deleteItem(String tableName, Map<String, String> deletionItemAttributes);
    //::> this will be either key or composite key: check map size for this
}
