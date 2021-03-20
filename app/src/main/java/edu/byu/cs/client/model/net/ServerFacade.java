package edu.byu.cs.client.model.net;

import java.io.IOException;

import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.FollowStatusRequest;
import com.example.shared.model.service.request.FollowerRequest;
import com.example.shared.model.service.request.FollowingRequest;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.FollowStatusResponse;
import com.example.shared.model.service.response.FollowingResponse;
import com.example.shared.model.service.response.FollowerResponse;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.NewStatusResponse;
import com.example.shared.model.service.response.StatusArrayResponse;
import com.example.shared.model.service.response.UserResponse;
import com.example.shared.model.service.response.LogoutResponse;
import com.example.shared.model.service.response.RegisterResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    // TODO: Set this to the invoke URL of your API. Find it by going to your API in AWS, clicking
    //  on stages in the right-side menu, and clicking on the stage you deployed your API to.
    private static final String SERVER_URL = "Insert your API invoke URL here";

    static final String URL_PATH_LOGIN = "/login";
    static final String URL_PATH_LOGOUT = "/logout";
    static final String URL_PATH_REGISTER = "/register";
    static final String URL_PATH_GETFOLLOWSTATUS = "/getfollowstatus";
    static final String URL_PATH_FOLLOW = "/follow";
    static final String URL_PATH_UNFOLLOW = "/unfollow";
    static final String URL_PATH_GETFOLLOWEES = "/getfollowees";
    static final String URL_PATH_GETNUMFOLLOWEES = "/numfollowees";
    static final String URL_PATH_GETFOLLOWERS = "/getfollowers";
    static final String URL_PATH_GETNUMFOLLOWERS = "/numfollowers";
    static final String URL_PATH_POSTSTATUS = "/poststatus";
    static final String URL_PATH_GETSTATUSARRAY = "/getstatusarray";
    static final String URL_PATH_GETUSERBYALIAS = "/getuserbyalias";

    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException {
        String Url_Path = URL_PATH_LOGIN;
        LoginResponse response = clientCommunicator.doPost(Url_Path, request, null, LoginResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public RegisterResponse register(RegisterRequest request) throws IOException, TweeterRemoteException {
        String Url_Path = URL_PATH_REGISTER;
        RegisterResponse response = clientCommunicator.doPost(Url_Path, request, null, RegisterResponse.class);
        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException {
        String Url_Path = URL_PATH_LOGOUT;
        LogoutResponse response = clientCommunicator.doPost(Url_Path, request, null, LogoutResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) throws IOException, TweeterRemoteException {
        String Url_Path = URL_PATH_GETFOLLOWSTATUS;
        FollowStatusResponse response = clientCommunicator.doPost(Url_Path, request, null, FollowStatusResponse.class);

        if (response.isSuccess())
            return response;
        else
            throw new RuntimeException(response.getMessage());
    }

    public FollowStatusResponse follow(FollowStatusRequest request) throws IOException, TweeterRemoteException {
        String Url_Path = URL_PATH_FOLLOW;
        FollowStatusResponse response = clientCommunicator.doPost(Url_Path, request, null, FollowStatusResponse.class);

        if (response.isSuccess())
            return response;
        else
            throw new RuntimeException(response.getMessage());
    }

    public FollowStatusResponse unfollow(FollowStatusRequest request) throws IOException, TweeterRemoteException {
        String Url_Path = URL_PATH_UNFOLLOW;
        FollowStatusResponse response = clientCommunicator.doPost(Url_Path, request, null, FollowStatusResponse.class);

        if (response.isSuccess())
            return response;
        else
            throw new RuntimeException(response.getMessage());
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the following response.
     */
    public FollowingResponse getFollowees(FollowingRequest request) throws IOException, TweeterRemoteException {
        String Url_Path = URL_PATH_GETFOLLOWEES;
        FollowingResponse response = clientCommunicator.doPost(Url_Path, request, null, FollowingResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public FollowingResponse getNumFollowees(FollowingRequest request) throws IOException, TweeterRemoteException {
        String Url_Path = URL_PATH_GETNUMFOLLOWEES;
        FollowingResponse response = clientCommunicator.doPost(Url_Path,request,null,FollowingResponse.class);

        if (response.isSuccess())
            return response;
        else
            throw new RuntimeException(response.getMessage());
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followers are to be returned and any
     *                other information required to satisfy the request.
     * @return the following response.
     */
    public FollowerResponse getFollowers(FollowerRequest request) throws IOException, TweeterRemoteException {
        String Url_Path = URL_PATH_GETFOLLOWERS;
        FollowerResponse response = clientCommunicator.doPost(Url_Path, request, null, FollowerResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public FollowerResponse getNumFollowers(FollowerRequest request) throws IOException, TweeterRemoteException {
        String Url_Path = URL_PATH_GETNUMFOLLOWERS;
        FollowerResponse response = clientCommunicator.doPost(Url_Path,request,null,FollowerResponse.class);

        if (response.isSuccess())
            return response;
        else
            throw new RuntimeException(response.getMessage());
    }

    public NewStatusResponse pushNewStatus(NewStatusRequest request) throws IOException, TweeterRemoteException {
        String Url_Path = URL_PATH_POSTSTATUS;
        NewStatusResponse response = clientCommunicator.doPost(Url_Path, request, null, NewStatusResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public StatusArrayResponse getStatusArray(StatusArrayRequest request) throws IOException, TweeterRemoteException {
        String Url_Path = URL_PATH_GETSTATUSARRAY;
        StatusArrayResponse response = clientCommunicator.doPost(Url_Path, request, null, StatusArrayResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }


    //GetUser for Status
    public UserResponse getUserByAlias(UserRequest request) throws IOException, TweeterRemoteException {
        String Url_Path = URL_PATH_GETUSERBYALIAS;
        UserResponse response = clientCommunicator.doPost(Url_Path, request, null, UserResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }
}
