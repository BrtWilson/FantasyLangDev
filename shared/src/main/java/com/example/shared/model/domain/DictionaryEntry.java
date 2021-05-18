package com.example.shared.model.domain;

import com.example.shared.model.service.response.DictionaryPageResponse;

import java.io.Serializable;
import java.util.Objects;

public class DictionaryEntry implements Comparable<DictionaryEntry>, Serializable {
    String fantasyWord;
    String partOfSpeech;
    String translation;

    public DictionaryEntry(String fantasyWord, String partOfSpeech, String translation) {
        this.fantasyWord = fantasyWord;
        this.partOfSpeech = partOfSpeech;
        this.translation = translation;
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

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        DictionaryEntry that = (DictionaryEntry) param;

        return (Objects.equals(fantasyWord, that.fantasyWord) &&
                Objects.equals(this.partOfSpeech, that.partOfSpeech) &&
                Objects.equals(this.translation, that.translation));
    }

    @Override
    public int compareTo(DictionaryEntry dictionaryEntry) {
        return this.getFantasyWord().compareTo(dictionaryEntry.getFantasyWord());
    }
}
