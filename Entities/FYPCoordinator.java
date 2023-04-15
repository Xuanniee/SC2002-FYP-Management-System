package Entities;

import Boundary.ProjectReportMenu;

/**
 * Represents a Database of all FYP Coordinators in the FYP Management System.
 * 
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class FYPCoordinator extends Supervisor implements ProjectReportMenu {
    /**
     * Constructor
     * Creates a FYP Coordinator object with the given userID, legal name, and email
     * address.
     * 
     * @param userID    FYP Coordinator's UserID
     * @param name      FYP Coordinator's Name
     * @param userEmail FYP Coordinator's Email
     */
    public FYPCoordinator(String userID, String name, String userEmail) {
        super(userID, name, userEmail);
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
