package Entities;

import enums.RequestType;
import enums.RequestStatus;

public class RequestTransfer extends Request {

    private Project project;
    private Supervisor currentSupervisor;
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

    /*@Override
    public void approveRequest(Boolean approve) {
        this.setRequestStatus(RequestStatus.APPROVED);
        this.project.setSupervisor(this.repSupervisor);
        this.repSupervisor.editNumProjects(1);
        this.currentSupervisor.editNumProjects(-1);
        // Code to make changes for faculty sides
    }*/

    public Supervisor getCurSupervisor() {
        return this.currentSupervisor;
    }

    public Supervisor getRepSupervisor() {
        return this.repSupervisor;
    }

    public Project getProject() {
        return this.project;
    }

}
