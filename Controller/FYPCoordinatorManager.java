package Controller;

import java.io.Console;
import java.util.Scanner;

import Entities.FYPCoordinator;
import Entities.Student;
import Entities.Project;
import Entities.RequestDeregister;
import Entities.RequestRegister;
import Entities.RequestTransfer;
import enums.RequestStatus;

public class FYPCoordinatorManager {
    private Scanner scanner;
    private Console terminaConsole;

    // Attributes
    private FYPCoordinator currentFypCoordinator;
    private ProjectDB projectDB;
    private FacultyDB facultyDB;
    private RequestRegisterDB requestRegisterDB;
    private RequestTransferDB requestTransferDB;
    private RequestDeregisterDB requestDeregisterDB;
    private RequestManager requestManager;

    /**
     * Constructor for FYP Manager
     * 
     * @param currentFypCoordinator
     */
    public FYPCoordinatorManager(FYPCoordinator currentFypCoordinator, ProjectDB projectDB,
            FacultyDB facultyDB, RequestRegisterDB requestRegisterDB, 
            RequestTransferDB requestTransferDB, RequestDeregisterDB requestDeregisterDB, 
            RequestManager requestManager, Scanner scanner, Console terminaConsole) {
        this.currentFypCoordinator = currentFypCoordinator;
        this.projectDB = projectDB;
        this.facultyDB = facultyDB;
        this.requestRegisterDB = requestRegisterDB;
        this.requestTransferDB = requestTransferDB;
        this.requestDeregisterDB = requestDeregisterDB;
        this.requestManager = requestManager;
        this.scanner = scanner;
        this.terminaConsole = terminaConsole;
    }

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
                facultyDB.createProject(projectDB, currentFypCoordinator);
                break;

            case 3:
                // View own Project(s)
                facultyDB.viewOwnProject(projectDB.retrieveSupervisorProjects(currentFypCoordinator.getUserID()));
                break;
            
            case 4:
                // Modify the Title of your Project(s)
                facultyDB.modifyTitle(projectDB, currentFypCoordinator);
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
                    System.out.println("Please indicate which Request you would like to look at: ");
                    System.out.println("Alternatively, Enter 0 to return to the previous menu.");
                    selectedRequest = scanner.nextInt();

                    if (selectedRequest == 0) {
                        // After Request has been approved or rejected, to return to main menu.
                        continue;
                    }

                    RequestRegister targetRequest = requestRegisterDB
                            .viewRegisterRequestDetailedFYPCoord(selectedRequest);
                    approvalResult = scanner.nextInt();

                    Student updateStudent = targetRequest.getStudent();
                    if (approvalResult == 1) {
                        requestRegisterDB.approveRegisterRequestFYPCoord(targetRequest);
                        targetRequest.setRequestStatus(RequestStatus.APPROVED);
                    } else {
                        // Reject Request
                        updateStudent.setHasAppliedForProject(false);
                        targetRequest.setRequestStatus(RequestStatus.REJECTED);
                    }
                    System.out.println("Returning to previous menu...");

                } while (selectedRequest != 0);
                System.out.println("Returning to main menu...");
                break;

            case 8:
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
                    System.out.println("Please indicate which Request you would like to look at: ");
                    System.out.println("Alternatively, Enter 0 to return to the previous menu.");
                    selectedSupervisorRequest = scanner.nextInt();

                    if (selectedSupervisorRequest == 0) {
                        // After Request has been approved or rejected, to return to main menu.
                        continue;
                    }

                    RequestTransfer targetTransferRequest = requestTransferDB
                            .viewTransferRequestDetailedFYPCoord(selectedSupervisorRequest);
                    approvalTransferResult = scanner.nextInt();

                    Project projectBeingTransferred = targetTransferRequest.getProject();
                    if (approvalTransferResult == 1) {
                        requestTransferDB.approveTransferRequestFYPCoord(targetTransferRequest);
                        targetTransferRequest.setRequestStatus(RequestStatus.APPROVED);
                    } else {
                        // Reject Request
                        projectBeingTransferred.setAwaitingTransferRequest(false);
                        targetTransferRequest.setRequestStatus(RequestStatus.REJECTED);
                    }

                    System.out.println("Returning to previous menu...");

                } while (selectedSupervisorRequest != 0);
                System.out.println("Returning to main menu...");
                break;

            case 9:
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
                    System.out.println("Please indicate which Request you would like to look at: ");
                    System.out.println("Alternatively, Enter 0 to return to the previous menu.");
                    selectedDeregisterRequest = scanner.nextInt();

                    if (selectedDeregisterRequest == 0) {
                        // After Request has been approved or rejected, to return to main menu.
                        continue;
                    }
                    RequestDeregister targetDeregisterRequest = requestDeregisterDB
                            .viewDeregisterRequestDetailedFYPCoord(selectedDeregisterRequest);
                    approvalDeregisterRequest = scanner.nextInt();

                    if (approvalDeregisterRequest == 1) {
                        requestDeregisterDB.approveDeregisterRequestFYPCoord(targetDeregisterRequest);
                        targetDeregisterRequest.setRequestStatus(RequestStatus.APPROVED);
                    }
                    // else {
                    // // Reject Request
                    // projectBeingTransferred.setAwaitingTransferRequest(false);
                    // targetTransferRequest.setRequestStatus(RequestStatus.REJECTED);
                    // }
                    System.out.println("Returning to previous menu...");

                } while (selectedDeregisterRequest != 0);
                System.out.println("Returning to main menu...");
                break;

            case 10:
                // Request the Transfer of a Student
                this.currentFypCoordinator.viewSupervisingProjectList();
                requestManager.changeSupervisorRequest(currentFypCoordinator);
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
}
