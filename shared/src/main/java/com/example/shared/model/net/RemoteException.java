package com.example.shared.model.net;

import java.util.List;

public class RemoteException extends Exception {

    private final String remoteExceptionType;
    private final List<String> remoteStakeTrace;

    protected RemoteException(String message, String remoteExceptionType, List<String> remoteStakeTrace) {
        super(message);
        this.remoteExceptionType = remoteExceptionType;
        this.remoteStakeTrace = remoteStakeTrace;
    }

    public String getRemoteExceptionType() {
        return remoteExceptionType;
    }

    public List<String> getRemoteStackTrace() {
        return remoteStakeTrace;
    }
}
