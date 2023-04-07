package Entities;

import enums.ProjectStatus;

public class Project {
    protected int projectID;
    protected String supervisorName;
    protected String supervisorEmail;
    protected Supervisor supervisor;
    protected Student student;
    protected String projectTitle;
    protected ProjectStatus projectStatus;

    // Constructors
    public Project(int projectID, String supervisorName, String supervisorEmail, String projectTitle) {
        this.projectID = projectID;
        this.supervisorName = supervisorName;
        this.supervisorEmail = supervisorEmail;
        // this.supervisor = null;
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

    public void viewProjectDetails() {
        System.out.println("Title of Project: " + this.projectTitle);
        System.out.println("Faculty In-charge's Name: " + this.getSupervisor().getUserName());
        System.out.println("Faculty In-charge's Email: " + this.getSupervisor().getUserEmail());
        if (this.projectStatus == ProjectStatus.ALLOCATED) {
            System.out.println("This project is allocated to the following student:");
            System.out.println("Student's Name: " + this.getStudent().getUserName());
            System.out.println("Student's Email: " + this.getStudent().getUserEmail());
        } else {
            System.out.println("This project is not yet allocated to any student.");
        }
        System.out.println("");
    }
}
