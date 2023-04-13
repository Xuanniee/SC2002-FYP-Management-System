package Entities;

import enums.RequestType;
import enums.RequestStatus;

public abstract class Request {

    private RequestStatus requestStatus;
    private RequestType requestType;

    /**
     * Constructor 1
     */
    public Request(RequestType requestType) {
        this.requestType = requestType;
        this.requestStatus = RequestStatus.PENDING;
    }

    /**
     * Constructor 2
     */
    public Request(RequestType requestType, RequestStatus requestStatus) {
        this.requestType = requestType;
        this.requestStatus = requestStatus;
    }

    public RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public RequestType getRequestType() {
        return this.requestType;
    }

    //public abstract void approveRequest(Boolean approve);
}
