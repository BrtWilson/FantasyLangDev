package edu.byu.cs.client.model.net;

import java.util.List;

public class RequestException extends Throwable {
    String errorMessage;
    String errorType;
    List<String> stackTrace;

    public RequestException(String errorMessage, String errorType, List<String> stackTrace) {
        this.errorMessage = errorMessage;
        this.errorType = errorType;
        this.stackTrace = stackTrace;
    }
}
