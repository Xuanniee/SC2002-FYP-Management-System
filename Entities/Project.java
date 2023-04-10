package Entities;

import enums.ProjectStatus;

public class Project {
    protected int projectID;
    // protected String supervisorName;
    // protected String supervisorEmail;
    protected Supervisor supervisor;
    protected Student student;
    protected String projectTitle;
    protected ProjectStatus projectStatus;

    public Project(int projectID, Supervisor supervisor, String projectTitle) {
        this.projectID = projectID;
        this.supervisor = supervisor;
        this.student = null;
        this.projectTitle = projectTitle;
        this.projectStatus = ProjectStatus.AVAILABLE;
    }

    public int getProjectID() {
        return projectID;
    }

    public Supervisor getSupervisor() {
        return this.supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setProjectTitle(String newProjectTitle) {
        this.projectTitle = newProjectTitle;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public ProjectStatus getProjectStatus() {
        return this.projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public void printProjectDetails() {
        System.out.println("ProjectID: " + this.projectID);
        System.out.println("Title of Project: " + this.projectTitle);
        System.out.println("Faculty In-charge's Name: " + this.getSupervisor().getUserName());
        System.out.println("Faculty In-charge's Email: " + this.getSupervisor().getUserEmail());
        if (this.projectStatus == ProjectStatus.ALLOCATED) {
            System.out.println("This project is allocated to the following student:");
            System.out.println("Student's Name: " + this.getStudent().getUserName());
            System.out.println("Student's Email: " + this.getStudent().getUserEmail());
        }
        System.out.println("");
    }

    public Boolean deregisterStudent() {
        System.out.println("Student " + this.student.userName + " has been deregistered from the Project " + this.projectID);
        this.student = null;
        this.projectStatus = ProjectStatus.AVAILABLE;

        return true;
    }
}
