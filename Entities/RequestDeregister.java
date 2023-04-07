package Entities;

import enums.RequestStatus;
import enums.RequestType;
import enums.ProjectStatus;

public class RequestDeregister extends Request {

    private Project project;
    private Student student;

    public RequestDeregister(Student student, Project project) {
        super(RequestType.DEREGISTER);
        this.student = student;
        this.project = project;
    }

    @Override
    public void approveRequest(Boolean approve) {
        // Approve this request
        this.setRequestStatus(RequestStatus.APPROVED);
        this.student.addHistory(this);
        this.student.addDeregisteredProjects(this.project);
        this.project.setProjectStatus(ProjectStatus.AVAILABLE);
    }

}
