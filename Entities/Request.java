package Entities;

import enums.RequestType;
import enums.RequestStatus;

/** 
 * Represents a Request in the FYP Management System.
 * A request can be classified with 1 Request Type, that is either REGISTER, CHANGETITLE, DEREGISTER, or TRANSFER.
 * A request can be classified with 1 Request Status, that is either PENDING, APPROVED, or REJECTED.
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class Request {

    /**
     * Classification of Request Status.
     * A request can only be in 1 of the 3 states: PENDING, APPROVED, REJECTED.
     */
    private RequestStatus requestStatus;

    /** 
     * Classification of Request.
     * Each request can only be 1 of the 4 types: REGISTER, CHANGETITLE, DEREGISTER, TRANSFER.
     */
    private RequestType requestType;

    /**
     * Constructor
     * Creates a request with the given type.
     * 
     * @param requestType Type of request being created.
     */
    public Request(RequestType requestType) {
        this.requestType = requestType;
        this.requestStatus = RequestStatus.PENDING;
    }

    /**
     * Constructor
     * Creates a request with the given type and status.
     * 
     * @param requestType Type of request being created.
     * @param requestStatus Status of request being created. 
     */
    public Request(RequestType requestType, RequestStatus requestStatus) {
        this.requestType = requestType;
        this.requestStatus = requestStatus;
    }

    /**
     * Retrieves the Status of this Request.
     * 
     * @return Status of this request.
     */
    public RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    /**
     * Updates the status of this request.
     * 
     * @param requestStatus This request's new status.
     */
    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    /**
     * Retrieves the Type of this request.
     * 
     * @return Type of this request.
     */
    public RequestType getRequestType() {
        return this.requestType;
    }

}
