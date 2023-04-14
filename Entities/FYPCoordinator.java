package Entities;

import java.util.Scanner;

import Boundary.ProjectReportMenu;

public class FYPCoordinator extends Supervisor implements ProjectReportMenu{
    /**
     * Constructor for FYP Coordinator
     * 
     * @param userID
     * @param name
     * @param userEmail
     */
    public FYPCoordinator(String userID, String name, String userEmail) {
        super(userID, name, userEmail);
    }

    // Setters and Getters
    // public String getFYPCoordID() {
    //     return this.userID;
    // }

    // public Boolean setFYPCoordID(String newUserId) {
    //     if (newUserId.isEmpty()) {
    //         return false;
    //     }
    //     this.userID = newUserId;
    //     return true;
    // }

    // public String getFYPCoordName() {
    //     return this.userName;
    // }

    // public Boolean setFYPCoordName(String newName) {
    //     if (newName.isEmpty()) {
    //         return false;
    //     }
    //     this.userName = newName;
    //     return true;
    // }

    // public String getFYPCoordEmail() {
    //     return this.userEmail;
    // }

    // public Boolean setFYPCoordEmail(String newEmail) {
        // if (newEmail.isEmpty()) {
        //     return false;
        // }
    //     this.userEmail = newEmail;
    //     return true;
    // }

    // public String getFYPCoordPassword() {
    //     return this.password;
    // }

    // public Boolean setFYPCoordPassword(String newPassword) {
    //     if (newPassword.isEmpty()) {
    //         return false;
    //     }
    //     this.password = newPassword;
    //     return true;
    // }

    /**
     * Overrides the Supervisor Menu to print FYP Coordinator's Menu
     */
    @Override
    public int printMenuOptions(Scanner scObject) {
        int choice;

        System.out.println(""); // print empty line
        System.out.println("+-------------------------------------------------------+");
        System.out.println("|                 FYP Coordinator Portal                |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("| 1.  View Profile                                      |");
        System.out.println("| 2.  Create a Project                                  |");
        System.out.println("| 3.  View own Project(s)                               |");
        System.out.println("| 4.  Modify the title of your Project(s)               |");
        System.out.println("| 5.  View All Projects                                 |");
        System.out.println("| 6.  Generate Project Report                           |");
        System.out.println("| 7.  Request for Allocating a Project to a Student     |");
        System.out.println("| 8.  Request for Changing Supervisor of Project        |");
        System.out.println("| 9.  Request for Deregister a Student from Project     |");
        System.out.println("| 10. Request the Transfer of a Student                 |");
        System.out.println("| 11. View Request History & Status                     |");
        System.out.println("| 12. Set New Password                                  |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("|             Enter 0 to log out from FYPMS             |");
        System.out.println("+-------------------------------------------------------+");
        System.out.println(""); // print empty line

        System.out.print("Please enter your choice: ");

        choice = scObject.nextInt();

        return choice;
    }

    /**
     * Prints the Menu for Project Report
     */
    public void displayProjectReportMenu() {
        System.out.println(""); // print empty line
        System.out.println("+-------------------------------------------------------+");
        System.out.println("|                  Project Report Portal                |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("| Filters for Project Report Generation                 |");
        System.out.println("| 1. Project Status                                     |");
        System.out.println("| 2. Project's Supervisor                               |");
        System.out.println("| 3. Project's Student                                  |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("|           Enter 0 to go back to Main Menu             |");
        System.out.println("+-------------------------------------------------------+");
        System.out.println(""); // print empty line

        System.out.print("Please enter your choice: ");
    }

}
