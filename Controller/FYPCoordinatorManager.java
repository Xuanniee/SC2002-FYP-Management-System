package Controller;

import java.util.Scanner;

import Entities.FYPCoordinator;
import Entities.Student;
import Entities.Project;
import enums.RequestStatus;
import Entities.RequestDeregister;
import Entities.RequestRegister;
import Entities.RequestTransfer;

public class FYPCoordinatorManager {
    Scanner scanner = new Scanner(System.in);

    // Attributes
    private FYPCoordinator currentFypCoordinator;
    private ProjectDB projectDB;
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
            RequestRegisterDB requestRegisterDB, RequestTransferDB requestTransferDB,
            RequestDeregisterDB requestDeregisterDB, RequestManager requestManager) {
        this.currentFypCoordinator = currentFypCoordinator;
        this.projectDB = projectDB;
        this.requestRegisterDB = requestRegisterDB;
        this.requestTransferDB = requestTransferDB;
        this.requestDeregisterDB = requestDeregisterDB;
        this.requestManager = requestManager;
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
                System.out.println("Viewing your profile...");
                currentFypCoordinator.printProfile();
                break;

            case 2:
                System.out.println("Viewing every Project in FYPMS...");
                projectDB.viewAllProjectsFYPCoord();
                break;

            case 3:
                // TODO Generate Project Menu
                System.out.println("Opening Project Report Menu...");
                Boolean invalidProjectMenuOption = true;
                do {
                    // Main Project Menu
                    displayProjectReportMenu();

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

            case 4:
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

            case 5:
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

            case 6:
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

            case 7:
                // View History & Status
                System.out.println("Viewing History...");
                requestManager.getAllRequestHistory(currentFypCoordinator);
                break;

            default:
                System.out.println("Error!! Invalid Input!!");

        }
    }

    public int displayFypCoordinatorMenu() {
        int choice;

        System.out.println(""); // print empty line
        System.out.println("+-------------------------------------------------------+");
        System.out.println("|                 FYP Coordinator Portal                |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("| 1. View Profile                                       |");
        System.out.println("| 2. View All Projects                                  |");
        System.out.println("| 3. Generate Project Report                            |");
        System.out.println("| 4. Request for Allocating a Project to a Student      |");
        System.out.println("| 5. Request for Changing Supervisor of Project         |");
        System.out.println("| 6. Request for Deregister a Student from Project      |");
        System.out.println("| 7. View Request History & Status                      |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("|             Enter 0 to log out from FYPMS             |");
        System.out.println("+-------------------------------------------------------+");
        System.out.println(""); // print empty line

        System.out.print("Please enter your choice: ");

        choice = scanner.nextInt();

        return choice;
    }

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
