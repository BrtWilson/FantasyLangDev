//package edu.byu.cs.client.presenter.oldfiles;
//
//import java.io.IOException;
//import edu.byu.cs.client.model.service.oldfiles.LoginService;
//
//import com.example.shared.model.net.RemoteException;
//import com.example.shared.model.service.request.LoginRequest;
//import com.example.shared.model.service.request.LogoutRequest;
//import com.example.shared.model.service.response.LoginResponse;
//import com.example.shared.model.service.response.Response;
//
///**
// * The presenter for the login functionality of the application.
// */
//public class LoginPresenter {
//
//    private final View view;
//    private LoginService loginService;
//
//    public interface View { }
//
//    public LoginPresenter(View view) {
//        this.view = view;
//    }
//
//    public LoginResponse login(LoginRequest loginRequest) throws IOException, RemoteException {
//        loginService = getLoginService();
//        return loginService.login(loginRequest);
//    }
//
//    public Response logout(LogoutRequest logoutRequest) throws IOException, RemoteException {
//        loginService = LoginService.getInstance();
//        return loginService.logout(logoutRequest);
//    }
//
//    public LoginService getLoginService() {
//        if (loginService == null) {
//            loginService = LoginService.getInstance();
//        }
//        return loginService;
//    }
//
//    public View getView() {
//        return view;
//    }
//}
