package com.example.shared.model.service.request;

import com.example.shared.model.domain.Dictionary;

/**
 * Contains all the information needed to make a login request.
 */
public class NewWordRequest {

    private String languageID;
    private Dictionary fantasyWord;
    private Boolean checkedAboutDuplicate;

    /**
     * Creates an instance.
     */
    public NewWordRequest(String languageID, Dictionary fantasyWord) {
        this.languageID = languageID;
        this.fantasyWord = fantasyWord;
        checkedAboutDuplicate = false;
    }

    /**
     * Creates a new NewWordRequest with user input about confirmation when words was a duplicate to update
     * @param languageID corresponding user name
     * @param fantasyWord corresponding language name
     * @param checkedAboutDuplicate if false, returns a question for user whether they are sure they want to add the meaning
     */
    public NewWordRequest(String languageID, Dictionary fantasyWord, Boolean checkedAboutDuplicate) {
        this.languageID = languageID;
        this.fantasyWord = fantasyWord;
        this.checkedAboutDuplicate = checkedAboutDuplicate;
    }

    /**
     * Creates a new NewWordRequest with user input about confirmation when words was a duplicate to update
     * @ userName drawn from previous request object
     * @ languageName drawn from previous request object
     * @param checked if false, returns a question for user whether they are sure they want to add the meaning
     */
    public NewWordRequest(NewWordRequest newWordRequest, Boolean checked) {
        this.languageID = newWordRequest.languageID;
        this.fantasyWord = newWordRequest.fantasyWord;
        this.checkedAboutDuplicate = checked;
    }

    public NewWordRequest() {
    }

    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }

    public Dictionary getFantasyWord() {
        return fantasyWord;
    }

    public void setFantasyWord(Dictionary fantasyWord) {
        this.fantasyWord = fantasyWord;
    }

    public Boolean getCheckedAboutDuplicate() {
        return checkedAboutDuplicate;
    }

    public void setCheckedAboutDuplicate(Boolean checkedAboutDuplicate) {
        this.checkedAboutDuplicate = checkedAboutDuplicate;
    }
}
