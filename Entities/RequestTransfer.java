package Entities;

import enums.RequestType;
import enums.RequestStatus;

public class RequestTransfer extends Request {

    private Project project;
    private Supervisor supervisor;

    public RequestTransfer(Project project, Supervisor supervisor) {
        super(RequestType.TRANSFER);
        this.project = project;
        this.supervisor = supervisor;
    }

    @Override
    public void approveRequest(Boolean approve) {
        this.setRequestStatus(RequestStatus.APPROVED);
        this.project.setSupervisor(this.supervisor);
        // Code to make changes for faculty side
    }

}
