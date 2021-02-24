package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.StatusArrayService;
import edu.byu.cs.tweeter.model.service.request.StatusArrayRequest;
import edu.byu.cs.tweeter.model.service.response.StatusArrayResponse;

public class StatusArrayPresenterTest {

    private StatusArrayRequest request;
    private StatusArrayResponse response;
    private StatusArrayService mockStatusArrayService;
    private StatusArrayPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        Status resultStatus1 = new Status("Message 1", "TimeStamp1", resultUser1);
        Status resultStatus2 = new Status("Message 2", "TimeStamp2", resultUser2);
        Status resultStatus3 = new Status("Message 3", "TimeStamp3", resultUser3);

        request = new StatusArrayRequest(resultUser1.getAlias(), 3, null);
        response = new StatusArrayResponse( new StatusArrayResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), false);

        // Create a mock StatusArrayService
        mockStatusArrayService = Mockito.mock(StatusArrayService.class);
        Mockito.when(mockStatusArrayService.getStatusArray(request)).thenReturn(response);

        // Wrap a StatusArrayPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new StatusArrayPresenter(new StatusArrayPresenter.View() {}));
        Mockito.when(presenter.getStatusArrayService()).thenReturn(mockStatusArrayService);
    }

    @Test
    public void testGetStatusArray_returnsServiceResult() throws IOException {
        Mockito.when(mockStatusArrayService.getStatusArray(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getStatusArray(request));
    }

    @Test
    public void testGetStatusArray_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockStatusArrayService.getStatusArray(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getStatusArrays(request);
        });
    }
}
