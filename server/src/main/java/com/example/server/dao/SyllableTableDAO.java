package com.example.server.dao;

import com.example.server.dao.dbstrategies.AWS_RDBStrategy;
import com.example.server.dao.dbstrategies.DBStrategyInterface;
import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.server.dao.dbstrategies.ResultsPage;
import com.example.server.dao.util.ListTypeItemTransformer;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.request.UpdateSyllablesRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;

public class SyllableTableDAO {
    private DBStrategyInterface databaseInteractor = getDatabaseInteractor();

    private static final String tableName = "Syllables";
    private static final String attributeLangID = "LanguageID";
    private static final String attributeTemplate = "SyllableTemplate";
    private static final String attributeDigit = "Digit";
    private static final String attributePossibleChars = "PossibleChars";

    private static final String SERVER_SIDE_ERROR = "[Server Error]";

    /**
     *
     * @param request provides languageID
     * @return a List of String containing the syllable data corresponding to the languageID if any exists
     */
    public List<String> getSyllableData(GetLanguageDataRequest request) throws Exception {
        Map<String, String> syllableQuery = new HashMap<>();
        syllableQuery.put(attributeLangID, request.getLanguageID());

        List<Map<String, String>> returnedItems = databaseInteractor.queryListItems(tableName, attributeLangID, syllableQuery);
        if (returnedItems.size() != 0) {
            Map<Integer, String> returnedSyllablePairs = parseItemsForSyllableMap(returnedItems);
            List<String> syllablesRetrieved = parseMapForSyllables(returnedSyllablePairs);

            return syllablesRetrieved;
        }
        return null;
    }

    private Map<Integer, String> parseItemsForSyllableMap(List<Map<String, String>> returnedItems) {
        Map<Integer, String> returnedSyllablePairs = new HashMap<>();
        for (int i = 0; i < returnedItems.size(); i++) {
            Integer tmpInt = Integer.parseInt(returnedItems.get(i).get(attributeDigit));
            String tmpStr = returnedItems.get(i).get(attributePossibleChars);
            returnedSyllablePairs.put(tmpInt, tmpStr);
        }
        return returnedSyllablePairs;
    }

    /**
     * COMPLICATED enough, should be tested individually
     */
    private List<String> parseMapForSyllables(Map<Integer, String> returnedSyllablePairs) {
        List<Integer> pairOrder = new ArrayList<>();
        List<String> syllablesRetrieved = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : returnedSyllablePairs.entrySet()) {
            pairOrder.add(entry.getKey());
        }
        if (pairOrder.size() != returnedSyllablePairs.size()) {
            System.out.println("Flaw in parseMapForSyllables method");
        }
        for (Map.Entry<Integer, String> entry : returnedSyllablePairs.entrySet()) {
            Integer smallest = 0;
            int index = 0;
            for (int i = 0; i < pairOrder.size(); i++) {
                Integer tmp = pairOrder.get(i);
                if (tmp < smallest) {
                    smallest = tmp;
                    index = i;
                }
            }
            if (pairOrder.size() > 0) {
                syllablesRetrieved.add(returnedSyllablePairs.get(smallest));
                pairOrder.remove(index);
            }
        }

        return syllablesRetrieved;
    }

    public GeneralUpdateResponse updateSyllables(UpdateSyllablesRequest request) {
        Map<Integer, String> syllableItemsToInsert = request.getSyllables();

        try {
            for (Map.Entry<Integer, String> entry : syllableItemsToInsert.entrySet()) {
                Map<String, String> syllableItem = new HashMap<>();
                //syllableItem.put(attributeLangID, request.getLanguageID());
                syllableItem.put(attributeTemplate, request.getSyllableTemplate());
                syllableItem.put(attributeDigit, entry.getKey().toString());
                syllableItem.put(attributePossibleChars, entry.getValue());
                databaseInteractor.insertItem(tableName, attributeLangID, request.getLanguageID(), syllableItem);
            }
        } catch (Exception e) {
            return new GeneralUpdateResponse(false, e.getMessage());
        }

        return new GeneralUpdateResponse(request.getLanguageID());
    }

    private DBStrategyInterface getDatabaseInteractor() {
        //return new AWS_RDBStrategy();
        return new DynamoDBStrategy();
    }
}
