package Entities;

import java.util.ArrayList;

import enums.*;

public class Student extends User {

    private static final UserType userType = UserType.STUDENT;
    private Project project;
    private Boolean isAssigned;
    private Boolean hasAppliedForProject = false;
    private Boolean isDeregistered = false;
    private ArrayList<Project> deregisteredProjects;

    /**
     * Constructor for Student
     * 
     * @param userID    Login ID of user
     * @param userName
     * @param userEmail
     */
    public Student(String userID, String userName, String userEmail) {
        super(userID, userName, userEmail);
        this.project = null;
        this.isAssigned = false;
        this.deregisteredProjects = new ArrayList<Project>();
        this.password = "password";
    }

    public Boolean getHasAppliedForProject() {
        return this.hasAppliedForProject;
    }

    public Boolean setHasAppliedForProject(Boolean status) {
        // Input Validation
        if (status == null) {
            return false;
        }

        this.hasAppliedForProject = status;
        return true;
    }

    public UserType getUserType() {
        return Student.userType;
    }

    public String getStudentName() {
        return this.userName;
    }

    public void setAssignedProject(Project project) {
        this.project = project;
        this.isAssigned = true;
    }

    public Boolean getIsAssigned() {
        return this.isAssigned;
    }

    public void setIsAssigned(Boolean assign) {
        this.isAssigned = assign;
    }

    public Project getAssignedProject() {
        return this.project;
    }

    public Boolean getIsDeregistered() {
        return this.isDeregistered;
    }

    public void setIsDeregistered(Boolean status) {
        this.isDeregistered = status;
    }

    public void addDeregisteredProjects(Project project) {
        this.deregisteredProjects.add(project);
    }

    public ArrayList<Project> getDeregisteredProjects() {
        return this.deregisteredProjects;
    }

    public Boolean deregisterProject() {
        if (this.isAssigned == false) {
            System.out.println("Student " + this.userName + " does not have a project assigned to them.");
            return false;
        }

        // Update Students' Particulars
        this.isAssigned = false;
        addDeregisteredProjects(this.project);
        this.project = null;

        return true;
    }

}
