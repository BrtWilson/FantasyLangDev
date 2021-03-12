package edu.byu.cs.client.model.net;
import com.example.shared.model.net.TweeterRemoteException;

import java.util.List;

public class TweeterRequestException extends TweeterRemoteException {

    public TweeterRequestException(String message, String remoteExceptionType, List<String> remoteStakeTrace) {
        super(message, remoteExceptionType, remoteStakeTrace);
    }
}