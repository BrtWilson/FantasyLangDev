package com.example.shared.model.service.response;

import com.example.shared.model.domain.DictionaryEntry;

import java.util.List;
import java.util.Objects;

public class DictionaryPageResponse extends PagedResponse{

    private String languageID;
    private String lastWord;
    private List<DictionaryEntry> words;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful. Sets the
     * success and more pages indicators to false.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public DictionaryPageResponse(String message) {
        super(false, message, false);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     * This is used for the base dictionary and for the search words feature
     *
     * @param hasMorePages an indicator of whether more data is available for the request.
     */
    public DictionaryPageResponse(List<DictionaryEntry> words, boolean hasMorePages, String lastWord, String languageID) {
        super(true, hasMorePages);
        this.words = words;
        this.lastWord = lastWord;
        this.languageID = languageID;
    }


    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }

    public String getLastWord() {
        return lastWord;
    }

    public void setLastWord(String lastWord) {
        this.lastWord = lastWord;
    }

    public List<DictionaryEntry> getWords() {
        return words;
    }

    public void setWords(List<DictionaryEntry> words) {
        this.words = words;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        DictionaryPageResponse that = (DictionaryPageResponse) param;

        return (Objects.equals(words, that.words) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                Objects.equals(this.getLanguageID(), that.getLanguageID()) &&
                Objects.equals(this.getLastWord(), that.getLastWord()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(words);
    }
}
