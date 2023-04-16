package Controller;

import java.io.Console;
import java.util.Scanner;

import Boundary.Menu;
import Entities.*;

/**
 * Represents a Student Manager in the FYP Management System.
 * The Student Manager class processes the Student's choice of action throughout
 * the user session.
 * 
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class StudentManager implements Menu {

    /**
     * Represents the Database of students in the FYP Management System.
     */
    private StudentDB studentDB;

    /**
     * Represents the Database of projects in the FYP Management System.
     */
    private ProjectDB projectDB;

    /**
     * Represents the Database of FYP Coordinators in the FYP Management System.
     */
    private FYPcoordDB fypCoordDB;

    /**
     * Represents the Request Manager required to process requests made by this
     * student.
     */
    private RequestManager requestManager;

    /**
     * Instance of the scanner used to retrieve this student's inputs.
     */
    private Scanner scanner;

    /**
     * Represents the terminal console used in the FYP Management System.
     */
    private Console terminalConsole;

    /**
     * Represents the student using the FYP Management System currently.
     */
    private Student currentStudent;

    /**
     * Constructor
     * Creates a Student Manager.
     * 
     * @param student         Student currently logged in to FYPMS.
     * @param studentDB       Database of all students in FYPMS.
     * @param projectDB       Database of all projects in FYPMS.
     * @param requestManager  Request Manager to process Student's requests.
     * @param fypCoordDB      Database of all FYP coordinators in FYPMS.
     * @param scanner         Scanner used to retrieve student's inputs.
     * @param terminalConsole Terminal console.
     */
    public StudentManager(Student student, StudentDB studentDB, ProjectDB projectDB, RequestManager requestManager,
            FYPcoordDB fypCoordDB, Scanner scanner, Console terminalConsole) {
        this.currentStudent = student;
        this.studentDB = studentDB;
        this.projectDB = projectDB;
        this.fypCoordDB = fypCoordDB;
        this.requestManager = requestManager;
        this.scanner = scanner;
        this.terminalConsole = terminalConsole;
    }

    /**
     * Function that allows Student to interact with the Menu
     * 
     * @param choice Student's selected action.
     */
    public void processStudentChoice(int choice) {

        System.out.println("");

        switch (choice) {
            case 0:
                System.out.println("Logging out...");
                break;

            case 1:
                System.out.println("Viewing Student Profile...");
                studentDB.viewStudentProfile(currentStudent);
                break;

            case 2:
                System.out.println("Viewing All Projects...");
                if (currentStudent.getIsDeregistered()) {
                    System.out.println(
                            "You are not allowed to view any projects because you have already deregistered from FYP.");
                } else if (currentStudent.getIsAssigned()) {
                    System.out.println(
                            "You are currently allocated to a FYP and do not have access to available project list.");
                } else {
                    projectDB.viewAvailableProjects();
                }
                break;

            case 3:
                System.out.println("Viewing Registered Project...");
                if (currentStudent.getIsDeregistered()) {
                    System.out.println(
                            "You are not registered for any projects because you have already deregistered from FYP.");
                }
                else if (!currentStudent.getIsAssigned()) {
                    System.out.println("You have not registered for any projects.");
                } else {
                    studentDB.viewRegisteredProject(currentStudent);
                }
                break;

            case 4:
                System.out.println("Request to Register for a Project...");
                // Student is only allowed to make 1 Register Request at any point in time
                if (currentStudent.getIsDeregistered()) {
                    System.out.println(
                            "You are not allowed to register for any projects because you have already deregistered from FYP.");
                }
                else if (currentStudent.getHasAppliedForProject()) {
                    System.out.println("You have already applied for another project. " +
                            "Please wait for the results of the request before making another.");
                    return;
                } else {
                    projectDB.viewAvailableProjects();
                    requestManager.studentRegister(currentStudent);
                }
                break;

            case 5:
                System.out.println("Request to Change Title of Registered Project...");
                if (currentStudent.getIsDeregistered()) {
                    System.out.println(
                            "You have already deregistered from FYP.");
                }
                else if (!currentStudent.getIsAssigned()) {
                    System.out.println("You do not have a registered project yet.");
                } 
                else if (currentStudent.getAssignedProject().getAwaitingTitleChangeRequest()) {
                    System.out.println("You have already requested for a Title Change. " +
                            "Please wait for the results of the request before making another.");
                } 
                else {
                    System.out.println("Your registered project: ");
                    currentStudent.getAssignedProject().printProjectDetails();
                    requestManager.changeTitle(currentStudent, currentStudent.getAssignedProject());
                }
                break;

            case 6:
                System.out.println("Request to Deregister from Registered Project...");
                if (currentStudent.getIsDeregistered()) {
                    System.out.println(
                            "You have already deregistered from FYP.");
                }
                else if (!currentStudent.getIsAssigned()) {
                    System.out.println("You do not have a registered project yet.");
                    break;
                } else {
                    System.out.println("Your registered project: ");
                    currentStudent.getAssignedProject().printProjectDetails();
                    requestManager.studentDeregister(currentStudent, fypCoordDB.getFypCoordinatorsList().get(0));
                }
                break;

            case 7:
                System.out.println("Viewing all Request History...");
                requestManager.getRequestHistory(currentStudent);
                break;

            case 8:
                // Change password
                this.currentStudent.changeUserPassword(currentStudent, scanner, terminalConsole);
                break;

            default:
                System.out.println("Please enter a valid choice");
                break;
        }
    }

    /**
     * Prints the Main Student Menu Interface and record their choice.
     * 
     * @return the Student's menu selection choice.
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
        // Remove \n from Buffer
        scObject.nextLine();

        return choice;
    }
}
