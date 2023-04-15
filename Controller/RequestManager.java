package Controller;

import java.util.Scanner;

import Entities.*;
import enums.ProjectStatus;

/**
 * Represents a Request Manager Object that manages the logic of requests.
 * 
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class RequestManager {
    /**
     * Represents the Database containing all Projects in FYPMS
     */
    private ProjectDB projectDB;

    /**
     * Represents the Database containing all Faculty Users in FYPMS
     */
    private FacultyDB facultyDB;

    /**
     * Represents the Database containing all Change Title Requests
     */
    private RequestChangeTitleDB requestChangeTitleDB;

    /**
     * Represents the Database containing all Register Requests
     */
    private RequestRegisterDB requestRegisterDB;

    /**
     * Represents the Database containing all De-Register Requests
     */
    private RequestDeregisterDB requestDeregisterDB;

    /**
     * Represents the Database containing all Transfer Requests
     */
    private RequestTransferDB requestTransferDB;

    /**
     * Constructor
     * Creates a Request Manager Object with references to multiple databases.
     * 
     * @param projectDB            Database containing all projects
     * @param facultyDB            Database containing all faculty members
     * @param requestChangeTitleDB Database containing all title change requests
     * @param requestRegisterDB    Database containing all register requests
     * @param requestDeregisterDB  Database containing all de-register requests
     * @param requestTransferDB    Database containing all transfer requests
     */
    public RequestManager(ProjectDB projectDB, FacultyDB facultyDB, RequestChangeTitleDB requestChangeTitleDB,
            RequestRegisterDB requestRegisterDB,
            RequestDeregisterDB requestDeregisterDB, RequestTransferDB requestTransferDB) {
        this.projectDB = projectDB;
        this.facultyDB = facultyDB;
        this.requestRegisterDB = requestRegisterDB;
        this.requestDeregisterDB = requestDeregisterDB;
        this.requestChangeTitleDB = requestChangeTitleDB;
        this.requestTransferDB = requestTransferDB;
    }

    Scanner sc = new Scanner(System.in);

    /**
     * Method for Student to create request to register for a Project
     */
    public void studentRegister(Student student) {
        System.out.println("Please enter Project ID of the project you want to register for: ");
        int projID = sc.nextInt();
        Project targetProject = projectDB.findProject(projID);
        targetProject.setProjectStatus(ProjectStatus.RESERVED);

        RequestRegister requestRegister = new RequestRegister(student, targetProject);
        requestRegisterDB.addRequest(requestRegister);

        // Set Status
        student.setHasAppliedForProject(true);
        System.out.println("Request submitted successfully.");
    }

    /**
     * Method for Student to create request to change title of their registered
     * project
     */
    public void changeTitle(Student student, Project project) {
        System.out.print("Please enter new title: ");
        // Remove \n
        sc.nextLine();
        String newTitle = sc.nextLine();
        RequestChangeTitle requestChangeTitle = new RequestChangeTitle(student, newTitle);
        requestChangeTitleDB.addRequest(requestChangeTitle);
        // Update status
        student.getAssignedProject().setAwaitingTitleChangeRequest(true);
        System.out.println("Request submitted successfully.");
    }

    /**
     * Method for Student to create request to deregister from their project
     */
    public void studentDeregister(Student student, FYPCoordinator fypCoordinator) {
        System.out.print("Please confirm the Project ID of the project you want to deregister from: ");
        int projID = sc.nextInt();
        Project currentProject = student.getAssignedProject();
        if (currentProject.getProjectID() == projID) {
            RequestDeregister requestDeregister = new RequestDeregister(student, currentProject, fypCoordinator);
            requestDeregisterDB.addRequest(requestDeregister);
            System.out.println("Request submitted successfully.");
        } else {
            System.out.print("Error - You are not assigned to this project!");
        }
    }

    /**
     * Method for Supervisor to create request to Transfer a project to another
     * Supervisor
     */
    public Boolean changeSupervisorRequest(Supervisor requesterSupervisor) {
        if (requesterSupervisor == null) {
            System.out.println("No Supervisor.");
            return false;
        }
        System.out.println("Provide the Project ID of the Project you no longer wish to supervise   : ");
        int projectBeingChanged = sc.nextInt();
        // Remove \n since nextInt does not clear it from the buffer
        sc.nextLine();
        // Retrieve the Project
        Project targetProject = requesterSupervisor.getParticularSupervisingProject(projectBeingChanged);

        System.out.println("Provide the UserID of the Replacement Supervisor                       : ");
        String replacementSupervisorID = sc.nextLine();
        // Retrieve the Replacement Supervisor
        Supervisor replacementSupervisor = facultyDB.findSupervisor(replacementSupervisorID);
        requestTransferDB.addRequest(new RequestTransfer(targetProject, requesterSupervisor, replacementSupervisor));
        targetProject.setAwaitingTransferRequest(true);

        System.out.println("Request submitted successfully.");
        return true;
    }

    /**
     * View History for Students
     * 
     * @param student Target Student
     */
    public void getRequestHistory(Student student) {
        requestChangeTitleDB.printStudentHistory(student);
        requestDeregisterDB.printStudentHistory(student);
        requestRegisterDB.printStudentHistory(student);
    }

    /**
     * For FYP Coordinator to view all Requests from ALL Users
     * 
     * @param fypCoordinator Current FYP Coordiator
     */
    public Boolean getAllRequestHistory(FYPCoordinator fypCoordinator) {
        if (fypCoordinator == null) {
            System.out.println("Only FYP Coordinator can access.");
            return false;
        }

        requestChangeTitleDB.printAllHistory(fypCoordinator);
        System.out.println();
        System.out.println();

        requestTransferDB.printAllHistory(fypCoordinator);
        System.out.println();
        System.out.println();

        requestDeregisterDB.printAllHistory(fypCoordinator);
        System.out.println();
        System.out.println();

        requestRegisterDB.printAllHistory(fypCoordinator);
        System.out.println();
        System.out.println();

        return true;
    }

    /**
     * Viewing Pending Title Change Requests for Supervisor
     * 
     * @param currentSupervisor Current Supervisor of Project
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean getPendingRequestsTitleChange(Supervisor currentSupervisor) {
        if (currentSupervisor == null) {
            System.out.println("No Supervisor provided.");
            return false;
        }

        requestChangeTitleDB.viewPendingTitleChangeRequests(currentSupervisor);
        return true;
    }

    /**
     * Viewing all incoming and outgoing requests for Supervisor regardless of
     * RequestStatus
     * 
     * @param currentSupervisor Target Supervisor
     */
    public void getRequestHistoryAndStatus(Supervisor currentSupervisor) {
        if (currentSupervisor == null) {
            System.out.println("No Supervisor provided.");
            return;
        }

        // History & Status of Incoming Requests
        System.out.println("###################  Incoming Title Change Requests  ###################");
        requestChangeTitleDB.viewPendingTitleChangeRequests(currentSupervisor);

        System.out.println("###################    Outgoing Transfer Requests    ###################");
        requestTransferDB.viewTransferRequestsForSupervisor(currentSupervisor);

        System.out.println("###########################    END    ###########################");
    }

}
