package Entities;

import enums.RequestStatus;
import enums.RequestType;
//import enums.ProjectStatus;

public class RequestDeregister extends Request {

    private Project project;
    private Student student;
    private FYPCoordinator fypCoordinator;

    public RequestDeregister(Student student, Project project, FYPCoordinator fypCoordinator) {
        super(RequestType.DEREGISTER);
        this.student = student;
        this.project = project;
        this.fypCoordinator = fypCoordinator;
    }

    public RequestDeregister(Student student, Project project, RequestStatus requestStatus,
            FYPCoordinator fypCoordinator) {
        super(RequestType.DEREGISTER, requestStatus);
        this.student = student;
        this.project = project;
        this.fypCoordinator = fypCoordinator;
    }

    /*@Override
    public void approveRequest(Boolean approve) {
        if (approve) {
            this.setRequestStatus(RequestStatus.APPROVED);
            this.project.setProjectStatus(ProjectStatus.AVAILABLE);
        } else {
            this.setRequestStatus(RequestStatus.REJECTED);
        }
    }*/

    public Supervisor getRequestCoordinator() {
        return this.fypCoordinator;
    }

    public Boolean setRequestCoordinator(FYPCoordinator approvalFypCoordinator) {
        if (approvalFypCoordinator == null) {
            System.out.println("Missing approval supervisor.");
            return false;
        }
        this.fypCoordinator = approvalFypCoordinator;

        return true;
    }

    public Student getStudent() {
        return this.student;
    }

    public Project getProject() {
        return this.project;
    }

}
