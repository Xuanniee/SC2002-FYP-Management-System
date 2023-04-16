package Controller;

import java.io.Console;
import java.util.Scanner;

import Boundary.Menu;
import Entities.Supervisor;
import enums.RequestStatus;
import Entities.Project;
import Entities.RequestChangeTitle;

/**
 * Represents a Supervisor Manager in the FYP Management System.
 * The Student Manager class processes the Supervisor's choice of action
 * throughout the user session.
 * 
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class SupervisorManager implements Menu {

    /**
     * Represents the Supervisor using the FYPMS currently.
     */
    private Supervisor managedSupervisor;
    /**
     * Representst the Database of faculties in the FYPMS.
     */
    private FacultyDB facultyDB;
    /**
     * Represents the Database of projects in the FYPMS.
     */
    private ProjectDB projectDB;
    /**
     * Represents the Database of Change Title Requests in FYPMS.
     */
    private RequestChangeTitleDB requestChangeTitleDB;
    /**
     * Represents the Request Manager required to process Supervisor's requests.
     */
    private RequestManager requestManager;
    /**
     * Instance of the scanner used to retrieve Supervisor's inputs.
     */
    private Scanner scanner;
    /**
     * Represents the terminal console used in the FYPMS.
     */
    private Console terminaConsole;

    /**
     * Constructor
     * Creates a Supervisor Manager.
     * 
     * @param supervisor           Current Supervisor that is logged in.
     * @param facultyDB            Database of faculties in FYPMS.
     * @param projectDB            Database of projects in FYPMS.
     * @param requestManager       Request Manager used to process requests of
     *                             current Supervisor.
     * @param requestChangeTitleDB Database of Requests to Change Title in FYPMS.
     * @param scanner              Scanner used to process inputs of Supervisor.
     * @param terminalConsole      Terminal console.
     */
    public SupervisorManager(Supervisor supervisor, FacultyDB facultyDB, ProjectDB projectDB,
            RequestManager requestManager, RequestChangeTitleDB requestChangeTitleDB,
            Scanner scanner, Console terminalConsole) {
        this.managedSupervisor = supervisor;
        this.facultyDB = facultyDB;
        this.projectDB = projectDB;
        this.requestChangeTitleDB = requestChangeTitleDB;
        this.requestManager = requestManager;
        this.scanner = scanner;
        this.terminaConsole = terminalConsole;
    }

    /**
     * Function that allows Supervisor to interact with the Menu
     * 
     * @param userInput Supervisor's selected action.
     */
    public void processSupervisorChoice(int userInput) {

        // Call the Menu for the respective users
        switch (userInput) {
            case 0:
                System.out.println("Logging out...");
                break;

            case 1:
                facultyDB.createProject(projectDB, managedSupervisor, scanner);
                break;

            case 2:
                facultyDB.viewOwnProject(projectDB.retrieveSupervisorProjects(managedSupervisor.getUserID()));
                break;

            case 3:
                facultyDB.modifyTitle(projectDB, managedSupervisor, scanner);
                break;

            case 4:
                // View Pending Requests for Approval
                int selectedTitleChangeRequest;
                int approvalResult;
                int noTitleChangeRequest;
                do {
                    // View Title Change Requests
                    noTitleChangeRequest = requestChangeTitleDB.viewAllTitleChangeRequestSupervisor(managedSupervisor);
                    // Input Validation to return to previous Menu if there are no Requests
                    if (noTitleChangeRequest == 1) {
                        break;
                    }
                    System.out.println("Please indicate which Request you would like to look at: ");
                    System.out.println("Alternatively, Enter 0 to return to the previous menu.");
                    // Get Index of Title Change Request to look at in detail
                    System.out.println("");
                    System.out.print("Your choice is: ");
                    selectedTitleChangeRequest = scanner.nextInt();

                    if (selectedTitleChangeRequest == 0) {
                        // After Request has been approved or rejected, to return to main menu.
                        continue;
                    }

                    while(selectedTitleChangeRequest < 0 || selectedTitleChangeRequest > requestChangeTitleDB.getNumPenReq()){
                        System.out.println("You have selected an invalid input, indicate a valid request: ");
                        selectedTitleChangeRequest = scanner.nextInt();
                    }

                    RequestChangeTitle targetRequest = requestChangeTitleDB
                            .viewTitleChangeRequestDetailedSupervisor(selectedTitleChangeRequest);
                    approvalResult = scanner.nextInt();

                    while(approvalResult != 1 && approvalResult != 0){
                        System.out.println("You have selected an invalid input, indicate a valid one: ");
                        approvalResult = scanner.nextInt();
                    }

                    Project updateProject = targetRequest.getProject();
                    if (approvalResult == 1) {
                        requestChangeTitleDB.approveTitleChangeRequest(targetRequest);
                        targetRequest.setRequestStatus(RequestStatus.APPROVED);
                    } else {
                        // Reject Request
                        updateProject.setAwaitingTitleChangeRequest(false);
                        targetRequest.setRequestStatus(RequestStatus.REJECTED);
                        requestChangeTitleDB.setNumPenReq(-1);
                    }
                    // Update title change status of project
                    updateProject.setAwaitingTitleChangeRequest(false);
                    System.out.println("Returning to previous menu...");

                } while (selectedTitleChangeRequest != 0);
                System.out.println("Returning to main menu...");
                break;

            case 5:
                // View Pending History & Status
                requestManager.getRequestHistoryAndStatus(managedSupervisor);
                break;

            case 6:
                // Transfer Supervisor
                Boolean test = this.managedSupervisor.viewSupervisingProjectList();
                if(test){
                    requestManager.changeSupervisorRequest(managedSupervisor);
                }
                break;

            case 7:
                // Change Password
                this.managedSupervisor.changeUserPassword(managedSupervisor, scanner, terminaConsole);
                break;

            default:
                System.out.println("Please enter a valid choice");
                break;
        }

        // System.out.println("Thank you for using FYPMS. You have been logged out.");
    }

    /**
     * Implements the User Menu to print the Supervisor Menu
     * 
     * @return integer that represents User's choice.
     */
    public int printMenuOptions(Scanner scObject) {
        // Supervisor Menu
        int choice;

        System.out.println(""); // print empty line
        System.out.println("+-------------------------------------------------------+");
        System.out.println("|                   Faculty Portal                      |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("| 1. Create a Project                                   |");
        System.out.println("| 2. View own Project(s)                                |");
        System.out.println("| 3. Modify the title of your Project(s)                |");
        System.out.println("| 4. View Pending Title Change Requests                 |");
        System.out.println("| 5. View Request History & Status                      |");
        System.out.println("| 6. Request the Transfer of a Student                  |");
        System.out.println("| 7. Set New Password                                   |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("|              Enter 0 to Log out of FYPMS              |");
        System.out.println("+-------------------------------------------------------+");
        System.out.println(""); // print empty line

        // Check if there are any new pending requests for the Supervisor
        if (requestChangeTitleDB.anyPendingChangeTitleRequestsForUser(managedSupervisor)) {
            System.out.println("Message: New Change Title Requests from students!");
            System.out.println(""); // print empty line
        }

        System.out.print("Please enter your choice: ");

        choice = scObject.nextInt();
        // Remove \n from Buffer
        scObject.nextLine();

        return choice;

    }

}
