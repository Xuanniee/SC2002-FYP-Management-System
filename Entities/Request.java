package Entities;

public abstract class Request {
    /**
     * Types of Request
     */
    enum RequestType {
        REGISTER, CHANGETITLE, DEREGISTER, TRANSFER
    }

    /**
     * Types of Request Status
     */
    enum RequestStatus {
        PENDING, APPROVED, REJECTED
    }

    private RequestStatus requestStatus;

    public Request() {
    }

    public Request(RequestType requestType) {
        this.requestStatus = RequestStatus.PENDING;
    }

    public RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public abstract void approveRequest(Boolean approve);
}
