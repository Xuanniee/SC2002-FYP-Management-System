package Controller;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Entities.Project;
import Entities.RequestDeregister;
import Entities.Student;
import Entities.Supervisor;
import Entities.FYPCoordinator;
import Entities.User;
import enums.ProjectStatus;
import enums.RequestStatus;

/**
 * Represents a Database of all Deregister Requests in FYP Management System.
 * 
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class RequestDeregisterDB extends Database {
    /**
     * Represents the file path of the Database of Deregister Requests in FYPMS.
     */
    private String filePath = String.join("", super.directory, "request_deregister_list.txt");

    /**
     * Represents the file containing database of all Deregister Requests.
     */
    private File file;

    /**
     * Represents a list of Deregister Requests in FYPMS.
     */
    private ArrayList<RequestDeregister> requestDeregisterList;

    /**
     * Represents a Database of all projects in FYPMS.
     */
    private ProjectDB projectDB;

    /**
     * Represents a Database of all students in FYPMS.
     */
    private StudentDB studentDB;

    /**
     * Represents a Database of all FYP Coordinators in FYPMS,
     */
    private FYPcoordDB fypCoordDB;

     /**
     * Represents the number of pending requests currently in FYPMS
     */
    private int numPendReq = 0;

    /**
     * Creates a Database of Deregister Requests in FYPMS when the application is
     * first initialised.
     * 
     * @param projectDB  Database of projects in FYPMS.
     * @param studentDB  Database of students in FYPMS.
     * @param fypCoordDB Database of FYP coordinators in FYPMS.
     */
    public RequestDeregisterDB(ProjectDB projectDB, StudentDB studentDB, FYPcoordDB fypCoordDB) {
        this.file = new File(filePath);
        this.requestDeregisterList = new ArrayList<RequestDeregister>();
        this.readFile();
        this.projectDB = projectDB;
        this.studentDB = studentDB;
        this.fypCoordDB = fypCoordDB;
    }

    /**
     * Reads Deregister Requests from request_deregister_list.txt
     */
    public void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String requestLine = br.readLine();
            requestLine = br.readLine();
            String studentID, coordinatorID;
            int projectID;
            RequestStatus reqStatus;
            String[] requestData;

            while (requestLine != null) {
                requestData = requestLine.split(super.delimiter);
                studentID = requestData[0];
                coordinatorID = requestData[1];
                projectID = Integer.parseInt(requestData[2]);
                reqStatus = RequestStatus.valueOf(requestData[3]);

                // Add new record to list
                requestDeregisterList.add(new RequestDeregister(studentDB.findStudent(studentID),
                        projectDB.findProject(projectID), reqStatus, fypCoordDB.findFypCoordinator(coordinatorID)));

                requestLine = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes updated Deregister Requests data to the text file.
     */
    public void updateFile() {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(file, false));
            PrintWriter pw = new PrintWriter(bf);

            pw.println("Student (Requestor)" + "\t" + "Coordinator (Requestee)" + "\t" + "Project ID" + "\t" + "Request Status");

            for (RequestDeregister rq : requestDeregisterList) {
                String studentID = rq.getStudent().getUserID();
                String coordinatorID = rq.getRequestCoordinator().getUserID();
                int projID = rq.getProject().getProjectID();
                String reqStatus = rq.getRequestStatus().name();

                pw.println(studentID + "\t" + coordinatorID + "\t" + projID + "\t" + reqStatus);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a new request to the list of all Deregister Requests.
     * 
     * @param requestDeregister New deregister request to be added to list.
     */
    public void addRequest(RequestDeregister requestDeregister) {
        requestDeregisterList.add(requestDeregister);
        numPendReq += 1;
    }

    /**
     * Checks whether target Student can be found in list of Deregister Requests.
     * 
     * @param supervisor Target student.
     * @return Boolean variable to indicate whether student can be found in list.
     */
    public Boolean findStudent(User student) {
        for (RequestDeregister req : requestDeregisterList) {
            if (req.getStudent() == student) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function for FYP Coordinator to view all the Deregister Requests.
     * 
     * @param fypCoordinator Target FYP coordinator.
     * @return the print statements of all the deregister requests in FYPMS.
     */
    public Boolean printAllHistory(FYPCoordinator fypCoordinator) {
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        System.out.println(
                "|                   List of All Student Deregistration Requests                    |");
        System.out.println(
                "+----------------------------------------------------------------------------------+");

        int counter = 1;
        for (int i = 0; i < requestDeregisterList.size(); i += 1) {
            RequestDeregister currentRequest = requestDeregisterList.get(i);
            System.out.println(counter + " Requester: " + currentRequest.getStudent().getUserName()
                    + " | Requestee: " + currentRequest.getRequestCoordinator().getUserName() +
                    " | Status: " + currentRequest.getRequestStatus().toString());

            counter += 1;
        }

        if (counter == 1) {
            System.out
                    .println("There is currently no deregister requests submitted by any user in the FYPMS System.");
        }

        return true;
    }

    /**
     * Prints all Deregister Requests made by specified Student
     * 
     * @param student Specified student.
     */
    public void printStudentHistory(Student Student) {
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        System.out.println(
                "|                    List of All Requests to Deregister from FYP                   |");
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        if (findStudent(Student)) {
            for (RequestDeregister req : requestDeregisterList) {
                if (req.getStudent() == Student) {
                    System.out.printf("Project to Deregister from (ID/Title): %s / %s", req.getProject().getProjectID(),
                            req.getProject().getProjectTitle());
                    System.out.println();
                    System.out.println("Request Status: " + req.getRequestStatus().name());
                    System.out.println("");
                }
            }
        } else {
            System.out.println("You have not submitted any requests to deregister.");
            System.out.println("");
        }
    }

    /**
     * Check if FYP coordinator has any pending requests to deregister
     * 
     * @return a Boolean to indicate whether there are any pending requests for user
     */
    public Boolean anyPendingDeregisterRequestsForUser() {
        for (int i = 0; i < requestDeregisterList.size(); i += 1) {
            if (requestDeregisterList.get(i).getRequestStatus() == RequestStatus.PENDING) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function for FYP Coordinator to view all the Deregister Requests
     * 
     * @return the print statements of all the transfer requests in FYPMS
     */
    public int viewAllDeregisterRequestFYPCoord() {
        System.out.println("");
        System.out.println(
                "+------------------------------------------------------------------------------------------------------------+");
        System.out.println(
                "|                     List of all Pending Requests from Students to deregister from Project                  |");
        System.out.println(
                "+------------------------------------------------------------------------------------------------------------+");
        int counter = 1;
        for (int i = 0; i < requestDeregisterList.size(); i += 1) {
            RequestDeregister currentRequest = requestDeregisterList.get(i);
            Project currentProject = currentRequest.getProject();
            if (currentRequest.getRequestStatus() == RequestStatus.PENDING) {
                System.out.println(counter + ". " + currentProject.getProjectTitle() + "  requested by "
                        + currentProject.getStudent().getUserName());
                counter += 1;
            }
        }
        System.out.println();

        if (counter == 1) {
            System.out.println("Pending Deregister Requests: 0");
            System.out.println("Enter 0 to return to the previous menu.");
            return 1;
        }
        
        System.out.println(); // Prints Empty Line

        return 0;
    }

    /**
     * Method to find the index of the target request based on input of user.
     * 
     * @param requestChoice User's input based on which Request they want to manage.
     * @return Index of target request in the list of all Deregister Requests.
     */
    public int findDeregisterRequestIndex(int requestChoice) {
        int counter = 1;
        for (int i = 0; i < requestDeregisterList.size(); i += 1) {
            RequestDeregister currentRequest = requestDeregisterList.get(i);
            if (currentRequest.getRequestStatus() == RequestStatus.PENDING) {
                if (counter == requestChoice) {
                    return i;
                }
                counter += 1;
            }
        }
        return -1;
    }

    /**
     * Allow FYP coordinator to see the specific details of the Deregister Request
     * 
     * @param requestChoice FYP coordinator's input based on which Request they want
     *                      to manage.
     * @return Target Request chosen by UFYP coordinatorser.
     */
    public RequestDeregister viewDeregisterRequestDetailedFYPCoord(int requestChoice) {
        int targetRequestIndex = findDeregisterRequestIndex(requestChoice);
        RequestDeregister targetRequest = requestDeregisterList.get(targetRequestIndex);
        System.out.println("Loading selected request...");
        System.out.println();
        System.out.println(
                "+------------------------------------------------------------------------------------------------------------+");
        System.out.println(
                "|                                       De-register Request Approval                                         |");
        System.out.println(
                "+------------------------------------------------------------------------------------------------------------+");
        targetRequest.getProject().printProjectDetails();
        System.out.println("Project Status              : " + targetRequest.getProject().getProjectStatus());
        System.out.println("Assigned Student (Requester): " + targetRequest.getStudent().getUserName());
        System.out.println(
                "+------------------------------------------------------------------------------------------------------------+");
        System.out.println(
                "|      Select 1 to approve the request, and 0 to reject the request and return to the previous menu.         |");
        System.out.println(
                "+------------------------------------------------------------------------------------------------------------+");
        System.out.println();
        System.out.println();

        return targetRequest;
    }

    /**
     * Process the approved Deregister Request by updating relevant entities
     * 
     * @param approvedRequest Request that is approved by FYP coordinator.
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean approveDeregisterRequestFYPCoord(RequestDeregister approvedRequest) {
        if (approvedRequest == null) {
            return false;
        }

        // Retrieve the Project, student & supervisor
        Project deregisteredProject = approvedRequest.getProject();
        Student deregisteredStudent = approvedRequest.getStudent();
        Supervisor deregisteredSupervisor = approvedRequest.getProject().getSupervisor();

        // Update Student Details
        deregisteredStudent.setAssignedProject(null);
        deregisteredStudent.setIsAssigned(false);
        deregisteredStudent.setIsDeregistered(true);
        deregisteredStudent.setHasAppliedForProject(false);

        // Update Project Status
        deregisteredProject.deregisterStudent();

        // Update Supervisor supervising list and number of projects
        // When student deregisters from a project, the supervisor may fall below
        // capacity, hence his remaining projects have to be set to AVAILABLE.
        deregisteredSupervisor.removeSupervisingProjectList(deregisteredProject);

        if (deregisteredSupervisor.getNumProj() < 2) {
            System.out.println("lessthan2");
            projectDB.setSupervisorProjectsToNewStatus(deregisteredSupervisor, ProjectStatus.AVAILABLE);
        }

        numPendReq -= 1;

        // Update Request Status so this.user cannot see it again
        // int indexCompletedRequest = requestDeregisterList.indexOf(approvedRequest);
        approvedRequest.setRequestStatus(RequestStatus.APPROVED);

        System.out.println("Student " + deregisteredStudent.getUserName()
                + " has been successfully deregistered from Project " + deregisteredProject.getProjectID());

        return true;
    }

    /**
     * Set the number of pending requests in the FYPMS
     * 
     * @param num
     */
    public void setNumPenReq(int num){
        numPendReq += num;
    }

    /**
     * Get the number of pending requests in the FYPMS
     */
    public int getNumPenReq(){
        return numPendReq;
    }
}
