package Controller;

import java.util.Scanner;

import Entities.*;

public class RequestManager {
    private ProjectDB projectDB;
    private FacultyDB facultyDB;
    private RequestChangeTitleDB requestChangeTitleDB;
    private RequestRegisterDB requestRegisterDB;
    private RequestDeregisterDB requestDeregisterDB;
    private RequestTransferDB requestTransferDB;

    // Constructor
    public RequestManager(ProjectDB projectDB, FacultyDB facultyDB, RequestChangeTitleDB requestChangeTitleDB, RequestRegisterDB requestRegisterDB,
    RequestDeregisterDB requestDeregisterDB, RequestTransferDB requestTransferDB) {
        this.projectDB = projectDB;
        this.facultyDB = facultyDB;
        this.requestRegisterDB = requestRegisterDB;
        this.requestDeregisterDB = requestDeregisterDB;
        this.requestChangeTitleDB = requestChangeTitleDB;
        this.requestTransferDB = requestTransferDB;
    }

    Scanner sc = new Scanner(System.in);

    public void studentRegister(Student student) {
        // Student is only allowed to make 1 Register Request at any point in time
        if (student.getHasAppliedForProject()) {
            // Not allowed to make request
            System.out.println("You have already applied for another project. " +
            "Please wait for the results of the request before making another.");
            return;
        }
        System.out.print("Please enter Project ID of the project you want to register for: ");
        int projID = sc.nextInt();
        Project targetProject = projectDB.findProject(projID);
        RequestRegister requestRegister = new RequestRegister(student, targetProject);
        requestRegisterDB.addRequest(requestRegister);

        // Set Status
        student.setHasAppliedForProject(true);
        System.out.println("Request submitted successfully.");
    }

    public void changeTitle(Student student, Project project) {
        System.out.println("Please enter new title: ");
        sc.nextLine();
        String newTitle = sc.nextLine();
        RequestChangeTitle requestChangeTitle = new RequestChangeTitle(student, newTitle);
        requestChangeTitleDB.addRequest(requestChangeTitle);
        student.getAssignedProject().setAwaitingTitleChangeRequest(true);
        System.out.println("Request submitted successfully.");
    }

    public void studentDeregister(Student student) {
        System.out.print("Please confirm the Project ID of the project you want to deregister from: ");
        int projID = sc.nextInt();
        Project currentProject = student.getAssignedProject();
        if (currentProject.getProjectID() == projID) {
            RequestDeregister requestDeregister = new RequestDeregister(student, currentProject);
            requestDeregisterDB.addRequest(requestDeregister);
            System.out.println("Request submitted successfully.");
        } else {
            System.out.print("Error - You are not assigned to this project!");
        }
    }

    public Boolean changeSupervisorRequest(Supervisor requesterSupervisor) {
        if (requesterSupervisor == null) {
            System.out.println("No Supervisor.");
            return false;
        }
        System.out.println("Provide the Project ID of the Project you no longer wish to supervise   : ");
        int projectBeingChanged = sc.nextInt();
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
     * @param user
     */
    public void getRequestHistory(User user) {
        requestChangeTitleDB.printHistory(user);
        requestDeregisterDB.printHistory(user);
        requestRegisterDB.printHistory(user);
    }

    /**
     * Viewing Pending Title Change Requests for Supervisor
     * @param currentSupervisor
     * @return
     */
    public Boolean getPendingRequestsTitleChange(Supervisor currentSupervisor) {
        if (currentSupervisor == null) {
            System.out.println("No Supervisor provided.");
            return false;
        }

        requestChangeTitleDB.viewPendingTitleChangeRequests(currentSupervisor);
        return true;
    }


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
