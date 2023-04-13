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
import Entities.FYPCoordinator;
import Entities.User;
import enums.RequestStatus;

public class RequestDeregisterDB extends Database {

    private String filePath = String.join("", super.directory, "request_deregister_list.txt");

    private File file;

    private ArrayList<RequestDeregister> requestDeregisterList;

    private ProjectDB projectDB;
    private StudentDB studentDB;
    private FYPcoordDB fyPcoordDB;

    public RequestDeregisterDB(ProjectDB projectDB, StudentDB studentDB, FYPcoordDB fyPcoordDB) {
        this.file = new File(filePath);
        this.requestDeregisterList = new ArrayList<RequestDeregister>();
        this.readFile();
        this.projectDB = projectDB;
        this.studentDB = studentDB;
        this.fyPcoordDB = fyPcoordDB;
    }

    public RequestDeregisterDB(String filePath, ProjectDB projectDB, StudentDB studentDB, FYPcoordDB fyPcoordDB) {
        this.file = new File(filePath);
        this.requestDeregisterList = new ArrayList<RequestDeregister>();
        this.readFile();
        this.projectDB = projectDB;
        this.studentDB = studentDB;
        this.fyPcoordDB = fyPcoordDB;
    }

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
                        projectDB.findProject(projectID), reqStatus, fyPcoordDB.findFypCoordinator(coordinatorID)));

                requestLine = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateFile() {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(file, false));
            PrintWriter pw = new PrintWriter(bf);

            pw.println(
                    "Student (Requestor)" + "\t" + "Coordinator (Requestee)" + "\t" + "Project ID" + "Request Status");

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

    public void addRequest(RequestDeregister requestDeregister) {
        requestDeregisterList.add(requestDeregister);
    }

    public Boolean findStudent(User student) {
        for (RequestDeregister req : requestDeregisterList) {
            if (req.getStudent() == student) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function for FYP Coordinator to view all the Deregister Requests
     * 
     * @param fypCoordinator
     * @return the print statements of all the deregister requests in FYPMS
     */
    public Boolean printAllHistory(FYPCoordinator fypCoordinator) {
        if (fypCoordinator == null) {
            System.out.println("Only the FYP Coordinator can access this features.");
            return false;
        }

        int counter = 1;
        for (int i = 0; i < requestDeregisterList.size(); i += 1) {
            RequestDeregister currentRequest = requestDeregisterList.get(i);
            System.out.println(counter + ". Requester: " + currentRequest.getStudent().getUserName()
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

    public void printHistory(User student) {
        if (findStudent(student)) {
            System.out.println("Showing all Deregistration Requests ... ");
            for (RequestDeregister req : requestDeregisterList) {
                if (req.getStudent() == student) {
                    System.out.printf("Project to Deregister from (ID/Title): %s/%s", req.getProject().getProjectID(),
                            req.getProject().getProjectTitle());
                    System.out.println("Request Status: " + req.getRequestStatus().name());
                    System.out.println("");
                }
            }
        } else {
            System.out.println("No requests to Deregister");
            System.out.println("");
        }
    }

    /**
     * Allow FYP Coordinator to see Students' Deregister Requests
     */
    public int viewAllDeregisterRequestFYPCoord() {
        System.out.println("Loading all pending requests to deregister a project...");
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
        System.out.println();
        System.out.println(); // Prints Empty Line

        return 0;
    }

    /**
     * Allow FYP coordinator to see the specific details of the Deregister Request
     * 
     * @param requestChoice
     * @return
     */
    public RequestDeregister viewDeregisterRequestDetailedFYPCoord(int requestChoice) {
        int targetRequestIndex = requestChoice - 1;
        RequestDeregister targetRequest = requestDeregisterList.get(targetRequestIndex);
        System.out.println("Loading selected request...");
        System.out.println();
        System.out.println(
                "+------------------------------------------------------------------------------------------------------------+");
        System.out.println(
                "|                                       De-register Request Approval                                         |");
        System.out.println(
                "|------------------------------------------------------------------------------------------------------------|");
        targetRequest.getProject().printProjectDetails();
        System.out.println("Project Status              : " + targetRequest.getProject().getProjectStatus());
        System.out.println("Assigned Student (Requester): " + targetRequest.getStudent().getUserName());
        System.out.println(
                "|------------------------------------------------------------------------------------------------------------|");
        System.out.println(
                "Select 1 to approve the request, and 0 to reject the request and return to the previous menu.");
        System.out.println();

        return targetRequest;
    }

    /**
     * For FYP Coordinator to approve Deregister
     * 
     * @param approvedRequest
     * @return
     */
    public Boolean approveDeregisterRequestFYPCoord(RequestDeregister approvedRequest) {
        if (approvedRequest == null) {
            return false;
        }

        // Retrieve the Project and Replacement Supervisor
        Project deregisteredProject = approvedRequest.getProject();
        Student deregisteredStudent = approvedRequest.getStudent();

        // Update Student's Project
        deregisteredStudent.deregisterProject();
        deregisteredStudent.setIsDeregistered(true);

        // Update Project Status
        deregisteredProject.deregisterStudent();

        // Update Request Status so this.user cannot see it again
        // int indexCompletedRequest = requestDeregisterList.indexOf(approvedRequest);
        approvedRequest.setRequestStatus(RequestStatus.APPROVED);
        // requestDeregisterList.remove(indexCompletedRequest);
        System.out.println("Student " + deregisteredStudent.getStudentName()
                + " has been successfully deregistered from Project " + deregisteredProject.getProjectID());

        return true;
    }

}
