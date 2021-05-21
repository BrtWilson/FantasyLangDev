//package edu.byu.cs.client.presenter.oldfiles;
//
//import java.io.IOException;
//import edu.byu.cs.client.model.service.oldfiles.NewStatusService;
//
//import com.example.shared.model.net.RemoteException;
//import com.example.shared.model.service.request.NewLanguageRequest;
//import com.example.shared.model.service.response.NewLanguageResponse;
//
//public class NewStatusPresenter {
//
//    private final  LoginPresenter.View view;
//    private NewStatusService newStatusService;
//
//    public interface View { }
//
//    public NewStatusPresenter( LoginPresenter.View view) { this.view = view; }
//
//    public NewLanguageResponse newStatus(NewLanguageRequest newLanguageRequest) throws IOException, RemoteException {
//        newStatusService = getNewStatusService();
//        return newStatusService.postNewStatus(newLanguageRequest);
//    }
//
//    public NewStatusService getNewStatusService() {
//        if (newStatusService == null) {
//            newStatusService = new NewStatusService();
//        }
//        return newStatusService;
//    }
//}