package edu.byu.cs.tweeter.model.domain.status_members;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import edu.byu.cs.tweeter.model.domain.status_members.Status;

public class StatusArray_List {
    protected ArrayList<Status> mStatusesArray;

    public Status[] getStatusesArray() {
        return (Status[]) mStatusesArray.toArray();
    }

    public void setStatusesArray(Status[] statusesArray) {
        mStatusesArray = new ArrayList<>(Arrays.asList(statusesArray));
    }

    public int getSize() {
        return mStatusesArray.size();
    }

    public boolean addStatus(Status newStatus) {
        return mStatusesArray.add(newStatus);
    }


    /*public Status[] loadStatusArray() {
        orderByTime();
        return (Status[]) mStatusesArray.toArray();
    }*/

    //TAs said ordering will be handled later...
    /*private void orderByTime() {
        //Status[] statusesOldList = mStatusesArray.clone();
        //Status[] statusesNewList = mStatusesArray.clone();

        //mStatusesArray.sort(Comparator.comparing(Status::getTimeBytes));

        for (int i = 0; i < mStatusesArray.length; i++) {
            Status curr_earliest = statusesOldList[0];
            for (int j = 0; j < statuses.size(); j++) {

            }
        }

        mStatusesArray = statusesNewList;
    }*/
}
