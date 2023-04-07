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

    public RequestDeregister(Student student, Project project, RequestStatus requestStatus) {
        super(RequestType.DEREGISTER, requestStatus);
        this.student = student;
        this.project = project;
    }

    @Override
    public void approveRequest(Boolean approve) {
        if (approve) {
            this.setRequestStatus(RequestStatus.APPROVED);
            this.student.addHistory(this);
            this.student.addDeregisteredProjects(this.project);
            this.project.setProjectStatus(ProjectStatus.AVAILABLE);
        } else {
            this.setRequestStatus(RequestStatus.REJECTED);
        }
    }

    public Student getStudent() {
        return this.student;
    }

    public int getProjectID() {
        return this.project.getProjectID();
    }

}
