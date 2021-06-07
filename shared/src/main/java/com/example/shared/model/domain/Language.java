package com.example.shared.model.domain;

import java.io.Serializable;
import java.util.List;

public class Language implements Comparable<Language>, Serializable {

    private String languageID;
    private String username;
    private String languageName;
    private String alphabet;

    private Language() {}

    public Language(String languageID, String username, String languageName, String alphabet) {
        this.languageID = languageID;
        this.username = username;
        this.languageName = languageName;
        this.alphabet = alphabet;
    }

    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    @Override
    public int compareTo(Language o) {
        return this.getUsername().compareTo(o.getUsername());
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }
}
