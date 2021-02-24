package edu.byu.cs.tweeter.model.domain;


public class StatusArray {
    private ArrayList<Status> statuses;

    public Status[] getStatuses() {
        return statuses;
    }

    public void addStatus(Status newStatus) {
        statuses.add(newStatus)
    }

    public int getSize() {
        return statuses.size();
    }
}