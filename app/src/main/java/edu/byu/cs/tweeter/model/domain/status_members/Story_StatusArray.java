package edu.byu.cs.tweeter.model.domain.status_members;

import edu.byu.cs.tweeter.model.domain.User;

public class Story_StatusArray extends StatusArray_List {
    private User correspondingUser;

    public Story_StatusArray(User correspondingUser) {
        this.correspondingUser = correspondingUser;
    }

    @Override
    public boolean addStatus(Status newStatus) {
        if (newStatus.getCorrespondingUser() == correspondingUser) {
            return false;
        }
        mStatusesArray.add(newStatus);
        return true;
    }
}
