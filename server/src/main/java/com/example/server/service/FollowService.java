package com.example.server.service;

import com.example.server.dao.FollowsTableDAO;
import com.example.server.dao.UsersTableDAO;
import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.IFollowService;
import com.example.shared.model.service.request.FollowRequest;
import com.example.shared.model.service.response.FollowResponse;

import java.io.IOException;

public class FollowService implements IFollowService{
    public FollowsTableDAO getFollowsDao = new FollowsTableDAO();
    public UsersTableDAO getUserTableDao = new UsersTableDAO();

    @Override
    public FollowResponse followResponse(FollowRequest request) throws IOException, TweeterRemoteException {
        UsersTableDAO userDAo = new UsersTableDAO();
        //how to authenitcate

        boolean success = false;
        boolean Following;

        if (request.isFollow()) {
            success = getFollowsDao.follow(request.getCurrentUser(), request.getUserToFollow());
            Following = true;

        }
        else{
            Following = false; //could broaden this out to unfollow a user then follow user.
        }

        return new FollowResponse(success, "Finished", Following);
    }
}
