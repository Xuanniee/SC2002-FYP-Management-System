package Entities;

import java.util.ArrayList;

public class Faculty extends User {

    private static final UserType userType = UserType.FACULTY;
    private Boolean isCoordinator;
    private int numProjectsAssigned;
    private Boolean isFull;
    private ArrayList<Project> projectsCreated;

    public Faculty(String facultyID, String facultyName, String facultyEmail) {
        super(facultyID, facultyName, facultyEmail);
        this.isCoordinator = false;
        this.numProjectsAssigned = 0;
        this.isFull = false;
        this.projectsCreated = new ArrayList<Project>();
    }

    public UserType getUserType() {
        return Faculty.userType;
    }

    public Boolean getIsCoordinator() {
        return this.isCoordinator;
    }

    public void setIsCoordinator(Boolean isCoordinator) {
        this.isCoordinator = isCoordinator;
    }

    public void setNumProjectsAssigned(int change) {
        this.numProjectsAssigned += change;
        if (this.numProjectsAssigned >= 2) {
            this.isFull = true;
        }
    }

    public Boolean getIsFull() {
        return this.isFull;
    }

    public void getProjectsCreated() {
        if (projectsCreated.isEmpty()) {
            System.out.println("No Projects created.");
        } else {
            for (Project project : projectsCreated) {
                System.out.println(project.getProjectTitle());
            }
        }
    }

}
