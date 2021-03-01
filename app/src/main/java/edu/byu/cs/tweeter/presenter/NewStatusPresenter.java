package edu.byu.cs.tweeter.presenter;

import java.io.IOException;
import edu.byu.cs.tweeter.model.service.NewStatusService;
import edu.byu.cs.tweeter.model.service.request.NewStatusRequest;
import edu.byu.cs.tweeter.model.service.response.NewStatusResponse;

public class NewStatusPresenter {

    private final  LoginPresenter.View view;
    private NewStatusService newStatusService;

    public interface View { }

    public NewStatusPresenter( LoginPresenter.View view) { this.view = view; }

    public NewStatusResponse newStatus(NewStatusRequest newStatusRequest) throws IOException {
        newStatusService = getNewStatusService();
        return newStatusService.postNewStatus(newStatusRequest);
    }

    public NewStatusService getNewStatusService() {
        if (newStatusService == null) {
            newStatusService = new NewStatusService();
        }
        return newStatusService;
    }
}