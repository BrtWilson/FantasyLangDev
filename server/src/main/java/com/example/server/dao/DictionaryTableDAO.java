package com.example.server.dao;

import com.example.server.dao.dbstrategies.AWS_RDBStrategy;
import com.example.server.dao.dbstrategies.DBStrategyInterface;
import com.example.server.dao.dbstrategies.ResultsPage;
import com.example.shared.model.domain.Dictionary;
import com.example.shared.model.service.request.DeleteWordRequest;
import com.example.shared.model.service.request.DictionaryPageRequest;
import com.example.shared.model.service.request.NewWordRequest;
import com.example.shared.model.service.request.SearchWordRequest;
import com.example.shared.model.service.response.DictionaryPageResponse;
import com.example.shared.model.service.response.GeneralUpdateResponse;
import com.example.shared.model.service.response.NewWordResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryTableDAO {
    private DBStrategyInterface databaseInteractor = getDatabaseInteractor();

    private static final String tableName = "Dictionary";
    private static final String attributeLangID = "LanguageID";
    private static final String attributeFantasyWord = "FantasyWord";
    private static final String attributePartOfSpeech = "PartOfSpeech";
    private static final String attributeTranslation = "Translation";

    private static final Integer PAGE_SIZE_DEFAULT = 10;
    private static Integer pageSize;

    public DictionaryPageResponse getWordArray(DictionaryPageRequest request) {
        pageSize = request.getLimit();
        verifyLimit(request.getLimit());
        String langID = request.getLanguageID();
        verifyLanguageID(langID);

        ResultsPage pageOfWords = databaseInteractor.keyAndCompositePageQuery(tableName, attributeLangID, langID, pageSize, attributeFantasyWord, request.getLastWord().getFantasyWord());
        return convertToDictionaryResponse(pageOfWords, langID);
    }

    private DictionaryPageResponse convertToDictionaryResponse(ResultsPage pageOfWords, String langID) {
        List<Dictionary> wordsList = new ArrayList<>();
        List<Map<String,String>> wordsListRaw = pageOfWords.getValues();
        for (int i = 0; i < wordsListRaw.size(); i++) {
            wordsList.add(convertWord(langID, wordsListRaw.get(i)));
        }
        return new DictionaryPageResponse(wordsList, pageOfWords.hasLastKey(), pageOfWords.getLastKey(), langID);
    }

    private Dictionary convertWord(String langID, Map<String, String> rawWordMap) {
        String fantasyWord = rawWordMap.get(attributeFantasyWord);
        String partOfSpeech = rawWordMap.get(attributePartOfSpeech);
        String translation = rawWordMap.get(attributeTranslation);
        return new Dictionary(langID, fantasyWord, partOfSpeech, translation);
    }

    /**
     *  Acts as if "filtered" version of Dictionary's getWordArray
     *      TODO: LESS OF A PRIORITY AT THIS TIME. WILL FINISH LATER
     * @param request ...
     * @return ...
     */
    public DictionaryPageResponse searchWord(SearchWordRequest request) {
        pageSize = request.getLimit();
        verifyLimit(request.getLimit());
        verifyLanguageID(request.getLanguageID());

        if (request.getTypeOfData().equals(SearchWordRequest.str_BY_FANTASYWORD)) {
            //search by key and *like composite
        } else if (request.getTypeOfData().equals(SearchWordRequest.str_BY_TRANSLATION)) {
            //search by key and *like attribute
        } else if (request.getTypeOfData().equals(SearchWordRequest.str_BY_UNSPECIFIED)) {
            //search by both
        }
        return null; //retrieveStory(request.getUserAlias(), request.getLastStatusDate());
    }

    private void verifyLimit(int limit) {
        if (limit <= 0) {
            pageSize = PAGE_SIZE_DEFAULT;
        }
    }

    private void verifyLanguageID(String languageID) {
        if (languageID == null) {
            throw new AssertionError();
        }
    }

    /**
     * Used in "Create New Word" feature
     */
    public Boolean checkWordExists(NewWordRequest request) {
        Map<String, String> queryAttributes = new HashMap<>();
        queryAttributes.put(attributeLangID, request.getLanguageID());
        Dictionary fantasyWordData = request.getFantasyWord();
        queryAttributes.put(attributeFantasyWord, fantasyWordData.getFantasyWord());
        //queryAttributes.put(attributePartOfSpeech, fantasyWordData.getPartOfSpeech());
        //queryAttributes.put(attributeTranslation, fantasyWordData.getTranslation());
        return (databaseInteractor.getItem(tableName, queryAttributes) != null);
    }

    /**
     * Used in "Create New Word" feature
     */
    public NewWordResponse insertNewWord(NewWordRequest request) {
        Map<String, String> newWordAttributes = new HashMap<>();
        newWordAttributes.put(attributeLangID, request.getLanguageID());
        Dictionary fantasyWordData = request.getFantasyWord();
        newWordAttributes.put(attributeFantasyWord, fantasyWordData.getFantasyWord());
        newWordAttributes.put(attributePartOfSpeech, fantasyWordData.getPartOfSpeech());
        newWordAttributes.put(attributeTranslation, fantasyWordData.getTranslation());
        databaseInteractor.insertItem(tableName, newWordAttributes);
        return new NewWordResponse(request.getLanguageID(), fantasyWordData.getFantasyWord(), false);
    }

    /**
     * Used in "Create New Word" feature, I believe
     *     TODO**, lower priority
     */
    public NewWordResponse updateWordAddToAttributes(NewWordRequest request) {
        //get word
        //add new data on non-basic attributes
        //update item
        return null;
    }

    /**
     * Used in "Create New Word" feature, and
     *     "Edit Word" feature in dictionary (for which the service class will have to adapt
     *     the Response object class)
     *     TODO**, lower priority
     */
    public NewWordResponse updateWord(NewWordRequest request) {
        //update and replace item data
        return null;
    }

    public GeneralUpdateResponse deleteWord(DeleteWordRequest request) {
        Map<String, String> deletionItemAttributes = new HashMap<>();
        deletionItemAttributes.put(attributeLangID, request.getLanguageID());
        Dictionary fantasyWordData = request.getFantasyWord();
        deletionItemAttributes.put(attributeFantasyWord, fantasyWordData.getFantasyWord());
        deletionItemAttributes.put(attributePartOfSpeech, fantasyWordData.getPartOfSpeech());
        deletionItemAttributes.put(attributeTranslation, fantasyWordData.getTranslation());
        boolean success = databaseInteractor.deleteItem(tableName, deletionItemAttributes);
        if (success) {
            return new GeneralUpdateResponse(request.getLanguageID());
        }
        return new GeneralUpdateResponse(false, "[Error] Failed to delete word.");
    }

    private DBStrategyInterface getDatabaseInteractor() {
        return new AWS_RDBStrategy();
        //return new DynamoDBStrategy();
    }
}
