package Entities;

import java.util.ArrayList;

import enums.*;

public class Student extends User {

    private static final UserType userType = UserType.STUDENT;
    private Project project;
    private Boolean isAssigned;
    private ArrayList<Request> studentHistory;
    private ArrayList<Project> deregisteredProjects;

    /**
     * Constructor for Student
     * 
     * @param userID    Login ID of user
     * @param userName
     * @param userEmail
     */
    public Student(String userID, String userName, String userEmail) {
        super(userID, "password");
        this.project = null;
        this.isAssigned = false;
        this.studentHistory = new ArrayList<Request>();
        this.deregisteredProjects = new ArrayList<Project>();
    }

    public UserType getUserType() {
        return Student.userType;
    }

    public String getStudentName() {
        return this.name;
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

    public void addHistory(Request request) {
        this.studentHistory.add(request);
    }

    public void addDeregisteredProjects(Project project) {
        this.deregisteredProjects.add(project);
    }

    public ArrayList<Project> getDeregisteredProjects() {
        return this.deregisteredProjects;
    }

    public ArrayList<Request> getRequestHistory() {
        return this.studentHistory;
    }

}
