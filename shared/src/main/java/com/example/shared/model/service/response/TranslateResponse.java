package com.example.shared.model.service.response;

import java.util.Map;
import java.util.Objects;

public class TranslateResponse extends Response {

    private String languageID;
    private String stringToTranslate;
    private Map<String, String> translationPieces;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public TranslateResponse(boolean success, String message) {
        super(success, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     */
    public TranslateResponse(String languageID, String stringToTranslate, Map<String, String> translationPieces) {
        super(true, null);
        this.languageID = languageID;
        this.stringToTranslate = stringToTranslate;
        this.translationPieces = translationPieces;
    }

    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }

    public String getStringToTranslate() {
        return stringToTranslate;
    }

    public void setStringToTranslate(String stringToTranslate) {
        this.stringToTranslate = stringToTranslate;
    }

    public Map<String, String> getTranslationPieces() {
        return translationPieces;
    }

    public void setTranslationPieces(Map<String, String> translationPieces) {
        this.translationPieces = translationPieces;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        TranslateResponse that = (TranslateResponse) param;

        return (Objects.equals(this.languageID, that.languageID) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                Objects.equals(this.stringToTranslate, that.stringToTranslate) &&
                this.isSuccess() == that.isSuccess());
    }
}