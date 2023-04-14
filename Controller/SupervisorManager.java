package Controller;

import java.io.Console;
import java.util.Scanner;

import Entities.Supervisor;
import enums.RequestStatus;
import Entities.Project;
import Entities.RequestChangeTitle;

public class SupervisorManager {
    Supervisor managedSupervisor;
    private FacultyDB facultyDB;
    private ProjectDB projectDB;
    private RequestChangeTitleDB requestChangeTitleDB;
    private RequestManager requestManager;
    private Scanner scanner;
    private Console terminaConsole;


    // Constructor
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

    /*
     * public SupervisorManager(String supervisorUserID, FacultyDB facultyDB) {
     * // Determine the Managed Supervisor
     * this.managedSupervisor = facultyDB.findSupervisor(supervisorUserID);
     * };
     * 
     * /**
     * Function that allows Supervisor to interface with the Menu
     * 
     * @param scObject
     * 
     * @param project_list
     */
    public void processSupervisorChoice(int userInput) {

        // Call the Menu for the respective users
        switch (userInput) {
            case 0:
                System.out.println("Logging out...");
                break;

            case 1:
                facultyDB.createProject(projectDB, managedSupervisor);
                break;

            case 2:
                facultyDB.viewOwnProject(projectDB.retrieveSupervisorProjects(managedSupervisor.getUserID()));
                break;

            case 3:
                facultyDB.modifyTitle(projectDB, managedSupervisor);
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
                    selectedTitleChangeRequest = scanner.nextInt();

                    if (selectedTitleChangeRequest == 0) {
                        // After Request has been approved or rejected, to return to main menu.
                        continue;
                    }

                    RequestChangeTitle targetRequest = requestChangeTitleDB
                            .viewTitleChangeRequestDetailedSupervisor(selectedTitleChangeRequest);
                    approvalResult = scanner.nextInt();

                    Project updateProject = targetRequest.getProject();
                    if (approvalResult == 1) {
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

            case 5:
                // View Pending History & Status
                requestManager.getRequestHistoryAndStatus(managedSupervisor);
                break;

            case 6:
                // Transfer Supervisor
                this.managedSupervisor.viewSupervisingProjectList();
                requestManager.changeSupervisorRequest(managedSupervisor);
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

}
