package edu.byu.cs.tweeter.model.domain.status_members;

public interface IStatusArray {
    abstract public Status[] getStatusesArray();
    abstract public void setStatusesArray(Status[] statusesArray);
    abstract public int getSize();
    abstract public Status[] loadStatusArray();
    abstract public void addStatus(Status newStatus);
}
