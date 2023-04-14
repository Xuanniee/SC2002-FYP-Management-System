package Entities;

import java.util.Scanner;

import Boundary.Menu;
import enums.*;

public class Student extends User implements Menu{

    private static final UserType userType = UserType.STUDENT;
    private Project project;
    private Boolean isAssigned = false;
    private Boolean hasAppliedForProject = false;
    private Boolean isDeregistered = false;

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

    // public UserType getUserType() {
    //     return Student.userType;
    // }

    // public String getStudentName() {
    //     return this.userName;
    // }

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

    /**
     * Implements the User Menu to print out the Student's Menu
     */
    public int printMenuOptions(Scanner scObject) {
        int choice;

        System.out.println(""); // print empty line
        System.out.println("+-------------------------------------------------------+");
        System.out.println("|                   Student Portal                      |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("| 1. View Profile                                       |");
        System.out.println("| 2. View All Projects                                  |");
        System.out.println("| 3. View Registered Project                            |");
        System.out.println("| 4. Request to Register for a Project                  |");
        System.out.println("| 5. Request to Change Title of Project                 |");
        System.out.println("| 6. Request to Deregister from Project                 |");
        System.out.println("| 7. View Request History                               |");
        System.out.println("| 8. Set New Password                                   |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("|              Enter 0 to Log out of FYPMS              |");
        System.out.println("+-------------------------------------------------------+");
        System.out.println(""); // print empty line

        System.out.print("Please enter your choice: ");

        choice = scObject.nextInt();

        return choice;
    }
}
