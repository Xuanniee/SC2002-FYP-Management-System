package Controller;

import java.util.Scanner;

import Entities.FYPCoordinator;
import Entities.RequestRegister;

public class FYPCoordinatorManager {
    private StudentDB studentDB = new StudentDB();
    private ProjectDB projectDB = new ProjectDB();
    private RequestChangeTitleDB requestChangeTitleDB = new RequestChangeTitleDB();
    private RequestRegisterDB requestRegisterDB = new RequestRegisterDB();
    private RequestDeregisterDB requestDeregisterDB = new RequestDeregisterDB();
    private RequestManager requestManager = new RequestManager();

    Scanner scanner = new Scanner(System.in);

    // Attributes
    private FYPCoordinator currentFypCoordinator;

    /**
     * Constructor for FYP Manager
     * @param currentFypCoordinator
     */
    public FYPCoordinatorManager(FYPCoordinator currentFypCoordinator) {
        this.currentFypCoordinator = currentFypCoordinator;
    }

    public void processFypCoordinatorChoice (int choice) {
        switch(choice) {
            case 1:
                break;

            case 2:
                System.out.println("Viewing every Project in FYPMS...");
                projectDB.getAllProjects();
                break;

            case 3:
                // TODO
                System.out.println("Opening Project Report Menu...");
                displayProjectReportMenu();
                break;

            case 4:
                // Allocate Project
                int selectedRequest;
                int approvalResult;
                do {
                    // View Register Requests
                    requestRegisterDB.viewAllRegisterRequestFYPCoord();
                    selectedRequest = scanner.nextInt();
                    RequestRegister targetRequest = requestRegisterDB.viewRegisterRequestDetailedFYPCoord(selectedRequest);
                    approvalResult = scanner.nextInt();

                    if (approvalResult == 1) {
                        requestRegisterDB.approveRegisterRequestFYPCoord(targetRequest);
                    }
                    System.out.println("Returning to previous menu...");

                } while (selectedRequest != 0);
                System.out.println("Returning to previous menu...");
                break;
            
            case 5:
                // Changing Supervisor
                
                break;
            
            case 6:
                // Deregister Student
                break;
            
            case 7:
                // View History & Status
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
        System.out.println("|           Enter 0 to go back to Main Menu             |");
        System.out.println("+-------------------------------------------------------+");
        System.out.println(""); // print empty line

        System.out.print("Please enter your choice: ");

        choice = scanner.nextInt();

        return choice;
    }

    public int displayProjectReportMenu() {
        int choice;

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

        choice = scanner.nextInt();

        return choice;
    }
    
}
