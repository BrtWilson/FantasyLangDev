package com.example.shared.model.domain;

import java.io.Serializable;
import java.util.Objects;

public class Dictionary implements Comparable<Dictionary>, Serializable {

    private String languageID;
    private String fantasyWord;
    private String partOfSpeech;
    private String translation;

    private Dictionary() {}

    public Dictionary(String languageID, String fantasyWord, String partOfSpeech, String translation) {
        this.languageID = languageID;
        this.fantasyWord = fantasyWord;
        this.partOfSpeech = partOfSpeech;
        this.translation = translation;
    }

    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }

    public String getFantasyWord() {
        return fantasyWord;
    }

    public void setFantasyWord(String fantasyWord) {
        this.fantasyWord = fantasyWord;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    @Override
    public int compareTo(Dictionary o) {
        return this.getFantasyWord().compareTo(o.getFantasyWord());
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
