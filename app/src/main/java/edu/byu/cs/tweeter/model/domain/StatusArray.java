package edu.byu.cs.tweeter.model.domain;


import java.util.ArrayList;

public class StatusArray {
    private ArrayList<Status> statuses;

    public Status[] getStatuses() {
        return statuses.toArray(new Status[statuses.size()]);
    }

    public void addStatus(Status newStatus) {
        statuses.add(newStatus);
    }

    public int getSize() {
        return statuses.size();
    }
}