package edu.byu.cs.tweeter.model.service.response;

/**
 * A base class for server responses.
 */
public class BasicResponse extends Response{

    public BasicResponse(boolean success) {
        super(success);
    }

    public BasicResponse(boolean success, String message) {
        super(success, message);
    }
}
