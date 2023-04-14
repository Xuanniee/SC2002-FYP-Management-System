package Entities;

import enums.RequestType;
import enums.RequestStatus;

public class RequestChangeTitle extends Request {

    private Student student;
    private Supervisor supervisor;
    private Project project;
    private String prevTitle;
    private String newTitle;

    public RequestChangeTitle(Student student, String newTitle) {
        super(RequestType.CHANGETITLE);
        this.project = student.getAssignedProject();
        this.student = student;
        this.supervisor = this.project.getSupervisor();
        this.prevTitle = student.getAssignedProject().getProjectTitle();
        this.newTitle = newTitle;
    }

    public RequestChangeTitle(Student student,
            Supervisor supervisor,
            Project project,
            String prevTitle,
            String newTitle,
            RequestStatus requestStatus) {
        super(RequestType.CHANGETITLE, requestStatus);
        this.student = student;
        this.supervisor = supervisor;
        this.project = project;
        this.prevTitle = prevTitle;
        this.newTitle = newTitle;
    }

    /**
     * Method to approve or reject the request
     */
    /*@Override
    public void approveRequest(Boolean approve) {
        if (approve) {
            this.setRequestStatus(RequestStatus.APPROVED);
            this.project.setProjectTitle(this.newTitle);
        } else {
            this.setRequestStatus(RequestStatus.REJECTED);
        }
    }*/

    public Student getStudent() {
        return this.student;
    }

    public Supervisor getSupervisor() {
        return this.supervisor;
    }

    public int getProjectID() {
        return this.project.getProjectID();
    }

    public Project getProject() {
        return this.project;
    }

    public String getPrevTitle() {
        return this.prevTitle;
    }

    public String getNewTitle() {
        return this.newTitle;
    }

}
