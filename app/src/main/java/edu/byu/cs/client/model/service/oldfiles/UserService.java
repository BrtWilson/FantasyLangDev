package edu.byu.cs.client.model.service.oldfiles;

import java.io.IOException;

import com.example.shared.model.domain.User;
import edu.byu.cs.client.model.net.ServerFacade;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.IUserService;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.UserResponse;
import edu.byu.cs.client.util.ByteArrayUtils;

/**
 * Contains the business logic to support the login operation.
 */
public class UserService implements IUserService {


    public UserResponse getUserByAlias(UserRequest request) throws IOException, RemoteException {
        ServerFacade serverFacade = getServerFacade();
        UserResponse response = serverFacade.getUserByAlias(request);

        if(response.isSuccess()) {
            loadImage(response);
        }
        return response;
    }

    /**
     * Loads the profile image data for each follower included in the response.
     *
     * @param response the response from the follower request.
     */
    private void loadImage(UserResponse response) throws IOException {
        User user = response.getUser();
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
        user.setImageBytes(bytes);
    }

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}