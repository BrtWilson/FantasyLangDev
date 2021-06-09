package edu.byu.cs.client.model.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.shared.model.domain.Dictionary;
import com.example.shared.model.domain.Language;
import com.example.shared.model.domain.User;
import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.DeleteWordRequest;
import com.example.shared.model.service.request.DictionaryPageRequest;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.NewLanguageRequest;
import com.example.shared.model.service.request.NewWordRequest;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.request.SearchWordRequest;
import com.example.shared.model.service.request.TranslateRequest;
import com.example.shared.model.service.request.UpdateAlphabetRequest;
import com.example.shared.model.service.request.UpdateSyllablesRequest;
import com.example.shared.model.service.response.DictionaryPageResponse;
import com.example.shared.model.service.response.GeneralUpdateResponse;
import com.example.shared.model.service.response.GetLanguageDataResponse;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.NewLanguageResponse;
import com.example.shared.model.service.response.NewWordResponse;
import com.example.shared.model.service.response.RegisterResponse;
import com.example.shared.model.service.response.TranslateResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade implements ServerFacadeInterface {

    // TODO: Set this to the invoke URL of your API. Find it by going to your API in AWS, clicking
    //  on stages in the right-side menu, and clicking on the stage you deployed your API to.
    private static final String SERVER_URL = "https://dedp7pd1sf.execute-api.us-west-2.amazonaws.com/dev";//"https://ubfjbeee2b.execute-api.us-east-1.amazonaws.com/tweeterBeta";

    static final String URL_PATH_LOGIN = "/Login";
    //static final String URL_PATH_LOGOUT = "/logout";
    static final String URL_PATH_REGISTER = "/Register";
    static final String URL_PATH_GETLANGDATA = "/GetLanguageData";
    static final String URL_PATH_NEWLANGUAGE = "/CreateLanguage";
    static final String URL_PATH_UPDATEALPHA = "/UpdateAlphabet";
    static final String URL_PATH_UPDATESYLLABLES = "/UpdateSyllables";
    static final String URL_PATH_NEWWORD = "/InsertNewWord";
    static final String URL_PATH_SEARCHWORD = "/SearchWord";
    static final String URL_PATH_DICTIONARY = "/Dictionary";
    static final String URL_PATH_UPDATEWORD = "/UpdateWord";
    static final String URL_PATH_DELETEWORD = "/DeleteWord";
    static final String URL_PATH_TRANSLATE = "/Translate";

    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) throws IOException, RemoteException  {
        String Url_Path = URL_PATH_LOGIN;
        LoginResponse response = clientCommunicator.doPost(Url_Path, request, null, LoginResponse.class);

        //TEMP RESPONSE
        if (response == null || !response.isSuccess()) {//***This is for show only! Comment out this statement when everything is working!***
            User user = new User(request.getUsername(), request.getUsername(), request.getPassword());
            List<Language> languages = new ArrayList<>();
            languages.add(new Language("elven0",request.getUsername(),"Elven", null));
            languages.add(new Language("dwarven0",request.getUsername(),"Dwarven",null));
            response = new LoginResponse(user,languages);
        }
        //

        return response;
    }

    public RegisterResponse register(RegisterRequest request) throws IOException, RemoteException {
        String Url_Path = URL_PATH_REGISTER;
        RegisterResponse response = clientCommunicator.doPost(Url_Path, request, null, RegisterResponse.class);

        //TEMP RESPONSE
        if (response == null || !response.isSuccess()) {//***This is for show only! Comment out this statement when everything is working!***
            User user = new User(request.getUserName(), request.getName(), request.getPassword());
            List<Language> languages = new ArrayList<>();
            languages.add(new Language("elven0",request.getUserName(),"Elven", null));
            languages.add(new Language("dwarven0",request.getUserName(),"Dwarven",null));
            response = new RegisterResponse(user,languages);
        }
        //

        return response;
    }

    public GetLanguageDataResponse getLanguageData(GetLanguageDataRequest request) throws IOException, RemoteException {//RemoteException, ServerException, RequestException, IOException {
        String Url_Path = URL_PATH_GETLANGDATA;
        GetLanguageDataResponse response = clientCommunicator.doPost(Url_Path, request, null, GetLanguageDataResponse.class);

        //TEMP RESPONSE
        if (response == null || !response.isSuccess()) {//***This is for show only! Comment out this statement when everything is working!***
            List<String> syllables = new ArrayList<>();
            syllables.add("st d");
            syllables.add(null);
            syllables.add("nt");
            response = new GetLanguageDataResponse("TestUser", "Elven", "elven0", "a b c ae", syllables);
        }
        //

        return response;
    }

    public NewLanguageResponse createNewLanguage(NewLanguageRequest request) throws IOException, RemoteException, RuntimeException {//RemoteException, ServerException, RequestException, IOException {
        String Url_Path = URL_PATH_NEWLANGUAGE;
        NewLanguageResponse response = clientCommunicator.doPost(Url_Path, request, null, NewLanguageResponse.class);

        //TEMP RESPONSE
        if (response == null || !response.isSuccess()) {//***This is for show only! Comment out this statement when everything is working!***
            response = new NewLanguageResponse(request.getUserName(),request.getLanguageName(),request.getLanguageName()+"0");
        }
        //

        return response;
    }

    public GeneralUpdateResponse updateAlphabet(UpdateAlphabetRequest request) throws IOException, RemoteException {//RemoteException, ServerException, RequestException, IOException {
        String Url_Path = URL_PATH_UPDATEALPHA;
        GeneralUpdateResponse response = clientCommunicator.doPost(Url_Path, request, null, GeneralUpdateResponse.class);

        //TEMP RESPONSE
        if (response == null || !response.isSuccess()) {//***This is for show only! Comment out this statement when everything is working!***
            response = new GeneralUpdateResponse("elven0");
        }
        //

        return response;
    }

    public GeneralUpdateResponse updateSyllables(UpdateSyllablesRequest request) throws IOException, RemoteException {//RemoteException, ServerException, RequestException, IOException {
        String Url_Path = URL_PATH_UPDATESYLLABLES;
        GeneralUpdateResponse response = clientCommunicator.doPost(Url_Path, request, null, GeneralUpdateResponse.class);

        //TEMP RESPONSE
        if (response == null || !response.isSuccess()) {//***This is for show only! Comment out this statement when everything is working!***
            response = new GeneralUpdateResponse("elven0");
        }
        //

        return response;
    }

    public NewWordResponse newWord(NewWordRequest request) throws IOException, RemoteException {//RemoteException, ServerException, RequestException, IOException {
        String Url_Path = URL_PATH_NEWWORD;
        NewWordResponse response = clientCommunicator.doPost(Url_Path, request, null, NewWordResponse.class);

        //TEMP RESPONSE
        if (response == null || !response.isSuccess() || response.getNeedsConfirmation() == null) {//***This is for show only! Comment out this statement when everything is working!***
            if (request.getFantasyWord().getFantasyWord().equals("Aiya") || request.getFantasyWord().getFantasyWord().equals("Namárië")) response = new NewWordResponse(request.getLanguageID(),request.getFantasyWord().getFantasyWord(),true);
            else response = new NewWordResponse(request.getLanguageID(),request.getFantasyWord().getFantasyWord(), false);
        }
        //

        return response;
    }

    public DictionaryPageResponse searchWord(SearchWordRequest request) throws IOException, RemoteException {//RemoteException, ServerException, RequestException, IOException {
        String Url_Path = URL_PATH_SEARCHWORD;
        DictionaryPageResponse response = clientCommunicator.doPost(Url_Path, request, null, DictionaryPageResponse.class);

        //TEMP RESPONSE
        if (response == null || !response.isSuccess()) {//***This is for show only! Comment out this statement when everything is working!***
            List<Dictionary> dictionaries = new ArrayList<>();
            dictionaries.add(new Dictionary("elven0", "Aiya", "", "hello"));
            response = new DictionaryPageResponse(dictionaries,false,null, request.getLanguageid());
        }
        //

        return response;
    }

    public DictionaryPageResponse dictionary(DictionaryPageRequest request) throws IOException, RemoteException {//RemoteException, ServerException, RequestException, IOException {
        String Url_Path = URL_PATH_DICTIONARY;
        DictionaryPageResponse response = clientCommunicator.doPost(Url_Path, request, null, DictionaryPageResponse.class);

        //TEMP RESPONSE
        if (response == null || !response.isSuccess()) {//***This is for show only! Comment out this statement when everything is working!***
            List<Dictionary> dictionaries = new ArrayList<>();
            dictionaries.add(new Dictionary("elven0", "Aiya", "", "hello"));
            dictionaries.add(new Dictionary("elven0", "Namárië", "", "good bye"));
            response = new DictionaryPageResponse(dictionaries,false,null, request.getLanguageid());
        }
        //

        return response;
    }

    public NewWordResponse updateWord(NewWordRequest request) throws IOException, RemoteException {//RemoteException, ServerException, RequestException, IOException {
        String Url_Path = URL_PATH_UPDATEWORD;
        NewWordResponse response = clientCommunicator.doPost(Url_Path, request, null, NewWordResponse.class);

        //TEMP RESPONSE
        if (response == null || !response.isSuccess()) {//***This is for show only! Comment out this statement when everything is working!***
            response = new NewWordResponse(request.getLanguageID(), request.getFantasyWord().getFantasyWord(), false);
        }
        //

        return response;
    }

    public GeneralUpdateResponse deleteWord(DeleteWordRequest request) throws IOException, RemoteException {//RemoteException, ServerException, RequestException, IOException {
        String Url_Path = URL_PATH_DELETEWORD;
        GeneralUpdateResponse response = clientCommunicator.doPost(Url_Path, request, null, GeneralUpdateResponse.class);

        //TEMP RESPONSE
        if (response == null || !response.isSuccess()) {//***This is for show only! Comment out this statement when everything is working!***
            response = new GeneralUpdateResponse("elven0");
        }
        //

        return response;
    }

    public TranslateResponse translate(TranslateRequest request) throws IOException, RemoteException {//RemoteException, ServerException, RequestException, IOException {
        String Url_Path = URL_PATH_TRANSLATE;
        TranslateResponse response = clientCommunicator.doPost(Url_Path, request, null, TranslateResponse.class);
        return response;
    }

    /*
    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) throws IOException, RemoteException {
        String Url_Path = URL_PATH_GETFOLLOWSTATUS;
        FollowStatusResponse response = clientCommunicator.doPost(Url_Path, request, null, FollowStatusResponse.class);

        if (response.isSuccess())
            return response;
        else
            throw new RuntimeException(response.getMessage());
    }

    public FollowStatusResponse follow(FollowStatusRequest request) throws IOException, RemoteException {
        String Url_Path = URL_PATH_FOLLOW;
        FollowStatusResponse response = clientCommunicator.doPost(Url_Path, request, null, FollowStatusResponse.class);

        if (response.isSuccess())
            return response;
        else
            throw new RuntimeException(response.getMessage());
    }

    public FollowStatusResponse unfollow(FollowStatusRequest request) throws IOException, RemoteException {
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
     *
    public FollowingResponse getFollowees(FollowingRequest request) throws IOException, RemoteException {
        String Url_Path = URL_PATH_GETFOLLOWEES;
        FollowingResponse response = clientCommunicator.doPost(Url_Path, request, null, FollowingResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public FollowingResponse getNumFollowees(FollowingRequest request) throws IOException, RemoteException {
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
     *
    public FollowerResponse getFollowers(FollowerRequest request) throws IOException, RemoteException {
        String Url_Path = URL_PATH_GETFOLLOWERS;
        FollowerResponse response = clientCommunicator.doPost(Url_Path, request, null, FollowerResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public FollowerResponse getNumFollowers(FollowerRequest request) throws IOException, RemoteException {
        String Url_Path = URL_PATH_GETNUMFOLLOWERS;
        FollowerResponse response = clientCommunicator.doPost(Url_Path,request,null,FollowerResponse.class);

        if (response.isSuccess())
            return response;
        else
            throw new RuntimeException(response.getMessage());
    }

    public NewStatusResponse pushNewStatus(NewStatusRequest request) throws IOException, RemoteException {
        String Url_Path = URL_PATH_POSTSTATUS;
        NewStatusResponse response = clientCommunicator.doPost(Url_Path, request, null, NewStatusResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    public StatusArrayResponse getStatusArray(StatusArrayRequest request) throws IOException, RemoteException {
        String Url_Path = URL_PATH_GETSTATUSARRAY;
        StatusArrayResponse response = clientCommunicator.doPost(Url_Path, request, null, StatusArrayResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }


    //GetUser for Status
    public UserResponse getUserByAlias(UserRequest request) throws IOException, RemoteException {
        String Url_Path = URL_PATH_GETUSERBYALIAS;
        UserResponse response = clientCommunicator.doPost(Url_Path, request, null, UserResponse.class);

        if(response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

     */
}
