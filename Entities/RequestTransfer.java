package Entities;

import enums.RequestType;
import enums.RequestStatus;

/**
 * Represents a Transfer Request in the FYP Management System.
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class RequestTransfer extends Request {

    /**
     * The Project whose supervisor is being replaced.
     */
    private Project project;

    /**
     * The current supervising faculty member that made the Transfer Request.
     */
    private Supervisor currentSupervisor;

    /**
     * The replacement supervising faculty member that is replacing the requester.
     */
    private Supervisor repSupervisor;

    /**
     * Constructor to create Transfer Request Actually
     * @param project
     * @param currentSupervisor
     * @param repSupervisor
     */
    public RequestTransfer(Project project, Supervisor currentSupervisor, Supervisor repSupervisor) {
        super(RequestType.TRANSFER);
        this.project = project;
        this.currentSupervisor = currentSupervisor;
        this.repSupervisor = repSupervisor;
    }

    /**
     * Constructor for Reading in Transfer Request
     * @param project
     * @param currentSupervisor
     * @param repSupervisor
     * @param requestStatus
     */
    public RequestTransfer(Project project, Supervisor currentSupervisor, Supervisor repSupervisor,
            RequestStatus requestStatus) {
        super(RequestType.TRANSFER, requestStatus);
        this.project = project;
        this.currentSupervisor = currentSupervisor;
        this.repSupervisor = repSupervisor;
    }

    /**
     * Retrieves the Current Supervisor who made the Transfer Request.
     * 
     * @return the Current Supervisor.
     */
    public Supervisor getCurSupervisor() {
        return this.currentSupervisor;
    }

    /**
     * Retrieves the Replacement Supervisor who is replacing the Requester.
     * 
     * @return the Replacement Supervisor.
     */
    public Supervisor getRepSupervisor() {
        return this.repSupervisor;
    }

    /**
     * Retrieves the Project in the Transfer Request.
     * 
     * @return the Transfer Request Project.
     */
    public Project getProject() {
        return this.project;
    }
}
