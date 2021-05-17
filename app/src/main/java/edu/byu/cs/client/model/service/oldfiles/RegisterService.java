package edu.byu.cs.client.model.service.oldfiles;

import java.io.IOException;
import com.example.shared.model.domain.User;
import edu.byu.cs.client.model.net.ServerFacade;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.IRegisterService;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;
import edu.byu.cs.client.util.ByteArrayUtils;

public class RegisterService implements IRegisterService {

    public static ServerFacade serverFacade;

    public RegisterService() {
    }

    public RegisterResponse register(RegisterRequest request) throws IOException, RemoteException {
        serverFacade = getServerFacade();
        RegisterResponse registerResponse = serverFacade.register(request);

        if(registerResponse.isSuccess()) {
            loadImage(registerResponse.getUser());
        }

        return registerResponse;
    }

    private void loadImage(User user) throws IOException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
        user.setImageBytes(bytes);
    }

    ServerFacade getServerFacade() {
        if(serverFacade == null) {
            serverFacade = new ServerFacade();
        }
        return serverFacade;
    }
}