package com.example.shared.model.service.request;

import com.example.shared.model.domain.User;

/**
 * Contains all the information needed to make a login request.
 */
public class NewLanguageRequest {

    private String userName;
    private String languageName;

    /**
     * Creates an instance.
     */
    public NewLanguageRequest(String userName, String languageName) {
        this.userName = userName;
        this.languageName = languageName;
    }

    public NewLanguageRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
}
