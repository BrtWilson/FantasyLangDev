//package edu.byu.cs.client.model.service.oldfiles;
//
//import java.io.IOException;
//
//import com.example.shared.model.domain.User;
//import edu.byu.cs.client.model.net.ServerFacade;
//
//import com.example.shared.model.net.RemoteException;
//
//import edu.byu.cs.client.util.ByteArrayUtils;
//
///**
// * Contains the business logic for getting the users a user is following.
// */
//public class FollowingService implements IFollowingService {
//
//    /**
//     * Returns the users that the user specified in the request is following. Uses information in
//     * the request object to limit the number of followees returned and to return the next set of
//     * followees after any that were returned in a previous request. Uses the {@link ServerFacade} to
//     * get the followees from the server.
//     *
//     * @param request contains the data required to fulfill the request.
//     * @return the followees.
//     */
//    public FollowingResponse getFollowees(FollowingRequest request) throws IOException, RemoteException {
//
//        if (request.isNumFollowingRequest())
//            return getServerFacade().getNumFollowees(request);
//
//        FollowingResponse response = getServerFacade().getFollowees(request);
//        if(response.isSuccess()) {
//            loadImages(response);
//        }
//
//        return response;
//    }
//
//    /*@Override
//    public IListResponse getList(IListRequest listRequest) {
//        return getFollowees((FollowingRequest) listRequest);
//    }*/
//
//    /**
//     * Loads the profile image data for each followee included in the response.
//     *
//     * @param response the response from the followee request.
//     */
//    private void loadImages(FollowingResponse response) throws IOException {
//        for(User user : response.getFollowees()) {
//            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
//            user.setImageBytes(bytes);
//        }
//    }
//
//    /**
//     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
//     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
//     * method to allow for proper mocking.
//     *
//     * @return the instance.
//     */
//    ServerFacade getServerFacade() {
//        return new ServerFacade();
//    }
//}
