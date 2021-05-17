package com.example.shared.model.service.response;

import java.util.List;
import java.util.Objects;

public class StatusArrayResponse extends PagedResponse implements IListResponse{

    private List<Status> statuses;
    private String lastDate;

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }


    /**
     * Creates a response indicating that the corresponding request was unsuccessful. Sets the
     * success and more pages indicators to false.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public StatusArrayResponse(String message) {
        super(false, message, false);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param statuses the followees to be included in the result.
     * @param hasMorePages an indicator of whether more data is available for the request.
     */
    public StatusArrayResponse(List<Status> statuses, boolean hasMorePages, String lastDate) {
        super(true, hasMorePages);
        this.statuses = statuses;
        this.lastDate = lastDate;
    }

    /**
     * Returns the statuses for the corresponding request.
     *
     * @return the statuses.
     */
    public List<Status> getStatuses() {
        return statuses;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        StatusArrayResponse that = (StatusArrayResponse) param;

        return (Objects.equals(statuses, that.statuses) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(statuses);
    }
}
