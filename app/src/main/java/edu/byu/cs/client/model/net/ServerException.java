package edu.byu.cs.client.model.net;

import java.util.List;

public class ServerException extends Throwable {
    String errorMessage;
    String errorType;
    List<String> stackTrace;

    public ServerException(String errorMessage, String errorType, List<String> stackTrace) {
        this.errorMessage = errorMessage;
        this.errorType = errorType;
        this.stackTrace = stackTrace;
    }
}
