package Controller;

import java.io.Console;
import java.util.Scanner;

import Boundary.Menu;
import Entities.FYPCoordinator;
<<<<<<< Updated upstream
import Entities.Supervisor;
=======
>>>>>>> Stashed changes
import Entities.Student;
import Entities.Project;
import Entities.RequestChangeTitle;
import Entities.RequestDeregister;
import Entities.RequestRegister;
import Entities.RequestTransfer;
import enums.ProjectStatus;
import enums.RequestStatus;

/**
 * Represents a FYP Coordinator Manager in FYP Management System.
 * The FYPCoordinatorManager class processes the Student's choice of action
 * throughout the user session.
 * 
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class FYPCoordinatorManager implements Menu {
    /**
     * Instance of the scanner used to retrieve this student's inputs.
     */
    private Scanner scanner;

    /**
     * Represents the terminal console used in the FYP Management System.
     */
    private Console terminaConsole;

    /**
     * Represents the FYP Coordinator using the FYP Management System currently.
     */
    private FYPCoordinator currentFypCoordinator;

    /**
     * Represents the Database of projects in the FYP Management System.
     */
    private ProjectDB projectDB;

    /**
     * Represents the Database of faculties in the FYP Management System.
     */
    private FacultyDB facultyDB;

    /**
     * Represents the Database of Register Requests in the FYP Management System.
     */
    private RequestRegisterDB requestRegisterDB;

    /**
     * Represents the Database of Transfer Requests in the FYP Management System.
     */
    private RequestTransferDB requestTransferDB;

    /**
     * Represents the Database of Deregister Requests in the FYP Management System.
     */
    private RequestDeregisterDB requestDeregisterDB;

    /**
     * Represents the Database of Deregister Requests in the FYP Management System.
     */
    private RequestChangeTitleDB requestChangeTitleDB;

    /**
     * Represents the Request Manager that processes various Requests.
     */
    private RequestManager requestManager;

    /**
     * Constructor for FYP Manager
     * Creates a FYP Coordinator Manager Object with the given inputs that manages a
     * specific FYP Coordinator.
     * 
     * @param currentFypCoordinator Selected FYP Coordinator
     */
    public FYPCoordinatorManager(FYPCoordinator currentFypCoordinator, ProjectDB projectDB,
            FacultyDB facultyDB, RequestRegisterDB requestRegisterDB, RequestTransferDB requestTransferDB,
            RequestDeregisterDB requestDeregisterDB, RequestChangeTitleDB requestChangeTitleDB,
            RequestManager requestManager, Scanner scanner, Console terminaConsole) {
        this.currentFypCoordinator = currentFypCoordinator;
        this.projectDB = projectDB;
        this.facultyDB = facultyDB;
        this.requestRegisterDB = requestRegisterDB;
        this.requestTransferDB = requestTransferDB;
        this.requestDeregisterDB = requestDeregisterDB;
        this.requestChangeTitleDB = requestChangeTitleDB;
        this.requestManager = requestManager;
        this.scanner = scanner;
        this.terminaConsole = terminaConsole;
    }

    /**
     * Processes the FYP Coordinator Input from the Main Menu
     * 
     * @param choice Selected Action by FYP Coordinator
     */
    public void processFypCoordinatorChoice(int choice) {
        switch (choice) {
            case 0:
                System.out.println("Logging off...");
                // Newlines
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                break;

            case 1:
                // Viewing Profile
                System.out.println("Viewing your profile...");
                currentFypCoordinator.printProfile();
                break;

            case 2:
                // Create Project
                facultyDB.createProject(projectDB, currentFypCoordinator, scanner);
                break;

            case 3:
                // View own Project(s)
                facultyDB.viewOwnProject(projectDB.retrieveSupervisorProjects(currentFypCoordinator.getUserID()));
                break;

            case 4:
                // Modify the Title of your Project(s)
                facultyDB.modifyTitle(projectDB, currentFypCoordinator, scanner);
                break;

            case 5:
                // View All Projects
                System.out.println("Viewing every Project in FYPMS...");
                projectDB.viewAllProjectsFYPCoord();
                break;

            case 6:
                // Generate Project Report
                System.out.println("Opening Project Report Menu...");
                Boolean invalidProjectMenuOption = true;
                do {
                    // Main Project Menu
                    this.currentFypCoordinator.displayProjectReportMenu();

                    // Open the Relevant Filtered Menu for each of the Option
                    int reportFilterOption = scanner.nextInt();
                    // scanner.nextLine();

                    switch (reportFilterOption) {
                        case 0:
                            // Quit to Previous Menu
                            invalidProjectMenuOption = false;
                            break;

                        case 1:
                            // Filter based on Project Status - ProjectDB
                            projectDB.projectStatusFilteredMenu(scanner);
                            invalidProjectMenuOption = false;
                            break;

                        case 2:
                            // Filter based on Project Supervisor
                            projectDB.projectSupervisorFilteredMenu(scanner);
                            invalidProjectMenuOption = false;
                            break;

                        case 3:
                            // Filter based on Allocated Student
                            projectDB.projectStudentFilteredMenu(scanner);
                            invalidProjectMenuOption = false;
                            break;

                        default:
                            System.out.println("Invalid Choice. Please choose again.");
                            break;
                    }
                } while (invalidProjectMenuOption == true);
                break;

            case 7:
                // View Title Change Requests
                int selectedTitleChangeRequest;
                int approvalTitleChangeResult;
                int noTitleChangeRequest;
                do {
                    // View Title Change Requests
                    noTitleChangeRequest = requestChangeTitleDB
                            .viewAllTitleChangeRequestSupervisor(currentFypCoordinator);
                    // Input Validation to return to previous Menu if there are no Requests
                    if (noTitleChangeRequest == 1) {
                        break;
                    }
                    System.out.println("> Please indicate which Request you would like to look at: ");
                    System.out.println("> Alternatively, enter 0 to return to the previous menu.");
                    // Get Index of Title Change Request to look at in detail
                    selectedTitleChangeRequest = scanner.nextInt();

                    if (selectedTitleChangeRequest == 0) {
                        // After Request has been approved or rejected, to return to main menu.
                        continue;
                    }

                    RequestChangeTitle targetRequest = requestChangeTitleDB
                            .viewTitleChangeRequestDetailedSupervisor(selectedTitleChangeRequest);
                    approvalTitleChangeResult = scanner.nextInt();

                    Project updateProject = targetRequest.getProject();
                    if (approvalTitleChangeResult == 1) {
                        requestChangeTitleDB.approveTitleChangeRequest(targetRequest);
                        targetRequest.setRequestStatus(RequestStatus.APPROVED);
                    } else {
                        // Reject Request
                        updateProject.setAwaitingTitleChangeRequest(false);
                        targetRequest.setRequestStatus(RequestStatus.REJECTED);
                    }
                    System.out.println("Returning to previous menu...");

                } while (selectedTitleChangeRequest != 0);
                System.out.println("Returning to main menu...");
                break;

            case 8:
                // Allocate Project
                int selectedRequest;
                int approvalResult;
                int noAllocateRequest;
                do {
                    // View Register Requests
                    noAllocateRequest = requestRegisterDB.viewAllRegisterRequestFYPCoord();
                    // Input Validation to return to previous Menu if there are no Requests
                    if (noAllocateRequest == 1) {
                        break;
                    }
                    System.out.println("> Please indicate which Request you would like to look at: ");
                    System.out.println("> Alternatively, Enter 0 to return to the previous menu.");
                    selectedRequest = scanner.nextInt();
                    
                    if (selectedRequest == 0) {
                        // After Request has been approved or rejected, to return to main menu.
                        continue;
                    } 

                    while(selectedRequest < 0 || selectedRequest > requestRegisterDB.getNumPenReq()){
                        System.out.println("You have selected an invalid input, indicate a valid request: ");
                        selectedRequest = scanner.nextInt();
                    }

                    RequestRegister targetRequest = requestRegisterDB
                            .viewRegisterRequestDetailedFYPCoord(selectedRequest);
                    approvalResult = scanner.nextInt();
                    while(approvalResult != 1 && approvalResult != 0){
                        System.out.println("You have selected an invalid input, indicate a valid one: ");
                        approvalResult = scanner.nextInt();
                    }
                    Student updateStudent = targetRequest.getStudent();
                    if (approvalResult == 1) { // Approve Request
                        requestRegisterDB.approveRegisterRequestFYPCoord(targetRequest);
                        targetRequest.setRequestStatus(RequestStatus.APPROVED);

                    } else { 
                        // Reject Request
                        updateStudent.setHasAppliedForProject(false);
                        targetRequest.setRequestStatus(RequestStatus.REJECTED);
                        // Update Project Status back to Available so that other students can view it
                        targetRequest.getProject().setProjectStatus(ProjectStatus.AVAILABLE);
                        
                        // Update numProjects supervised by Supervisor because 1 was added when request
                        // was made
                        targetRequest.getSupervisor().editNumProjects(-1);
                        if (targetRequest.getSupervisor().getNumProj() < 2) {
                            projectDB.setSupervisorProjectsToNewStatus(targetRequest.getSupervisor(),
                                    ProjectStatus.AVAILABLE);
                        }
                        //Reduce number of pending request
                        requestRegisterDB.setNumPenReq(-1);
                    }
                    System.out.println("Returning to previous menu...");

                } while (selectedRequest != 0);
                System.out.println("Returning to main menu...");
                break;

            case 9:
                // Changing Supervisor
                int selectedSupervisorRequest;
                int approvalTransferResult;
                int noTransferRequest;
                do {
                    // View Transfer Requests
                    noTransferRequest = requestTransferDB.viewAllTransferRequestFYPCoord();
                    // Input Validation to return to previous Menu if there are no Requests
                    if (noTransferRequest == 1) {
                        break;
                    }
                    System.out.println("> Please indicate which Request you would like to look at: ");
                    System.out.println("> Alternatively, Enter 0 to return to the previous menu.");
                    selectedSupervisorRequest = scanner.nextInt();

                    if (selectedSupervisorRequest == 0) {
                        // After Request has been approved or rejected, to return to main menu.
                        continue;
                    }

                    while(selectedSupervisorRequest < 0 && selectedSupervisorRequest > requestTransferDB.getNumPenReq()){
                        System.out.println("You have selected an invalid input, indicate a valid request: ");
                        selectedSupervisorRequest = scanner.nextInt();
                    }

                    RequestTransfer targetTransferRequest = requestTransferDB
                            .viewTransferRequestDetailedFYPCoord(selectedSupervisorRequest);

                    // Add hint message in scenario where the replacement supervisor has already
                    // reached his/her cap
                    if (targetTransferRequest.getRepSupervisor().getNumProj() >= 2) {
                        System.out.printf("Warning: Replacement Supervisor already has %d allocated projects.\n",
                                targetTransferRequest.getRepSupervisor().getNumProj());
                    }

                    System.out.print("Your choice is: ");
                    approvalTransferResult = scanner.nextInt();

                    while(approvalTransferResult != 1 && approvalTransferResult != 0){
                        System.out.println("You have selected an invalid input, indicate a valid one: ");
                        approvalResult = scanner.nextInt();
                    }

                    Project projectBeingTransferred = targetTransferRequest.getProject();
                    if (approvalTransferResult == 1) {
                        requestTransferDB.approveTransferRequestFYPCoord(targetTransferRequest);
                        targetTransferRequest.setRequestStatus(RequestStatus.APPROVED);
                    } else {
                        // Reject Request
                        projectBeingTransferred.setAwaitingTransferRequest(false);
                        targetTransferRequest.setRequestStatus(RequestStatus.REJECTED);
                        requestTransferDB.setNumPenReq(-1);
                    }

                    System.out.println("Returning to previous menu...");

                } while (selectedSupervisorRequest != 0);
                System.out.println("Returning to main menu...");
                break;

            case 10:
                // Deregister Student
                int selectedDeregisterRequest;
                int approvalDeregisterRequest;
                int noDeregisterRequest;
                do {
                    // View Deregister Requests
                    noDeregisterRequest = requestDeregisterDB.viewAllDeregisterRequestFYPCoord();
                    // Input Validation to return to previous Menu if there are no Requests
                    if (noDeregisterRequest == 1) {
                        break;
                    }
                    
                    do {
                        System.out.println("Please indicate which Request you would like to look at: ");
                        System.out.println("Alternatively, Enter 0 to return to the previous menu.");
                        while (!scanner.hasNextInt()) {
                            System.out.println("Invalid input.");
                            scanner.next();
                        }
                        selectedDeregisterRequest = scanner.nextInt();
                    } while (selectedDeregisterRequest > requestDeregisterDB.getNumPenReq() || selectedDeregisterRequest < 1);

                    if (selectedDeregisterRequest == 0) {
                        // After Request has been approved or rejected, to return to main menu.
                        continue;
                    }

                    while(selectedDeregisterRequest < 0 || selectedDeregisterRequest > requestDeregisterDB.getNumPenReq()){
                        System.out.println("You have selected an invalid input, indicate a valid request: ");
                        selectedDeregisterRequest = scanner.nextInt();
                    }

                    RequestDeregister targetDeregisterRequest = requestDeregisterDB
                            .viewDeregisterRequestDetailedFYPCoord(selectedDeregisterRequest);
                    approvalDeregisterRequest = scanner.nextInt();

                    while(approvalDeregisterRequest != 1 && approvalDeregisterRequest != 0){
                        System.out.println("You have selected an invalid input, indicate a valid one: ");
                        approvalDeregisterRequest = scanner.nextInt();
                    }

                    if (approvalDeregisterRequest == 1) {
                        requestDeregisterDB.approveDeregisterRequestFYPCoord(targetDeregisterRequest);
                        targetDeregisterRequest.setRequestStatus(RequestStatus.APPROVED);
                    } else {
                        // Reject Request; Update the Status of the Request
                        targetDeregisterRequest.setRequestStatus(RequestStatus.REJECTED);
                        requestDeregisterDB.setNumPenReq(-1);
                    }
                    System.out.println("Returning to previous menu...");

                } while (selectedDeregisterRequest != 0);
                System.out.println("Returning to main menu...");
                break;

            case 11:
                // View History & Status
                System.out.println("Viewing History...");
                requestManager.getAllRequestHistory(currentFypCoordinator);
                break;

            case 12:
                // Change Password
                this.currentFypCoordinator.changeUserPassword(currentFypCoordinator, scanner, terminaConsole);
                break;

            default:
                System.out.println("Error!! Invalid Input!!");

        }
    }

    /**
     * Implements the User Menu to print the FYP Coordinator Menu
     * 
     * @return integer that represents User's choice.
     */
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
        System.out.println("| 7.  View Pending Title Change Requests                |");
        System.out.println("| 8.  Request for Allocating a Project to a Student     |");
        System.out.println("| 9.  Request for Changing Supervisor of Project        |");
        System.out.println("| 10. Request for Deregister a Student from Project     |");
        System.out.println("| 11. View Request History & Status                     |");
        System.out.println("| 12. Set New Password                                  |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("|             Enter 0 to log out from FYPMS             |");
        System.out.println("+-------------------------------------------------------+");
        System.out.println(""); // print empty line

        // Check if there are any new pending requests for the Supervisor
        if (requestRegisterDB.anyPendingRegisterRequestsForUser()) {
            System.out.println("Message: New Register Requests from student(s)!");
            System.out.println(""); // print empty line
        }
        if (requestChangeTitleDB.anyPendingChangeTitleRequestsForUser(currentFypCoordinator)) {
            System.out.println("Message: New Change Title Requests from student(s)!");
            System.out.println(""); // print empty line
        }
        if (requestTransferDB.anyPendingTransferRequestsForUser()) {
            System.out.println("Message: New Transfer Requests from Supervisor(s)!");
            System.out.println(""); // print empty line
        }
        if (requestDeregisterDB.anyPendingDeregisterRequestsForUser()) {
            System.out.println("Message: New Deregister Requests from student(s)!");
            System.out.println(""); // print empty line
        }

        System.out.print("Please enter your choice: ");

        choice = scObject.nextInt();
        // Remove \n from Buffer
        scObject.nextLine();

        return choice;
    }
}
