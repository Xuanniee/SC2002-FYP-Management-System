package Entities;

import enums.RequestType;
import enums.RequestStatus;

public class RequestRegister extends Request {

    private Student student;
    private Supervisor supervisor;
    private Project project;

    public RequestRegister(Student student, Project project) {
        super(RequestType.REGISTER);
        this.student = student;
        this.supervisor = project.getSupervisor();
        this.project = project;
    }

    public RequestRegister(Student student, Supervisor supervisor, Project project, RequestStatus requestStatus) {
        super(RequestType.REGISTER, requestStatus);
        this.student = student;
        this.supervisor = supervisor;
        this.project = project;
    }

    @Override
    public void approveRequest(Boolean approve) {
        if (approve) {
            this.setRequestStatus(RequestStatus.APPROVED);
            student.setAssignedProject(this.project);
            project.setStudent(this.student);
        } else {
            this.setRequestStatus(RequestStatus.REJECTED);
        }
    }

    public Student getStudent() {
        return this.student;
    }

    public Supervisor getSupervisor() {
        return this.supervisor;
    }

    public int getProjectID() {
        return this.project.getProjectID();
    }
}
