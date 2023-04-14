package Controller;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Entities.RequestChangeTitle;
import Entities.Student;
import Entities.Supervisor;
import Entities.FYPCoordinator;
import Entities.Project;
import Entities.User;
import enums.RequestStatus;

/**
 * Represents a Database of all Title Change Requests in FYP Management System.
 * 
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class RequestChangeTitleDB extends Database {
    /**
     * Represents the file path of the Database of Title Change Requests in FYPMS.
     */
    private String filePath = String.join("", super.directory, "request_change_title_list.txt");

    /**
     * Represents the file containing the databse of all Title Change Requests
     */
    private File file;

    /**
     * ArrayList containing all the Title Change Requests in FYPMS
     */
    private ArrayList<RequestChangeTitle> requestChangeTitleList;

    /**
     * Represents the Database containing all Projects in FYPMS
     */
    private ProjectDB projectDB;

    /**
     * Represents the Database contianing all Student Users in FYPMS
     */
    private StudentDB studentDB;

    /**
     * Represents the Database contianing all Supervisor Users in FYPMS
     */
    private FacultyDB facultyDB;

    /**
     * Constructor
     * Creates a Database Object storing all the Title Change Requests.
     * 
     * @param projectDB Database containing all Projects in FYPMS
     * @param studentDB Database contianing all Student Users in FYPMS
     * @param facultyDB Database contianing all Supervisor Users in FYPMS
     */
    public RequestChangeTitleDB(ProjectDB projectDB, StudentDB studentDB, FacultyDB facultyDB) {
        this.file = new File(filePath);
        this.requestChangeTitleList = new ArrayList<RequestChangeTitle>();
        this.projectDB = projectDB;
        this.studentDB = studentDB;
        this.facultyDB = facultyDB;
        this.readFile();
    }

    /**
     * Reads the Title Change Requests from provided file.
     */
    public void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String requestLine = br.readLine();
            requestLine = br.readLine();
            String studentID, supervisorID, prevTitle, newTitle;
            int projectID;
            RequestStatus reqStatus;
            String[] requestData;

            while (requestLine != null) {
                requestData = requestLine.split(super.delimiter);
                studentID = requestData[0];
                supervisorID = requestData[1];
                projectID = Integer.parseInt(requestData[2]);
                prevTitle = requestData[3];
                newTitle = requestData[4];
                reqStatus = RequestStatus.valueOf(requestData[5]);

                // Add new record to list
                requestChangeTitleList.add(new RequestChangeTitle(studentDB.findStudent(studentID),
                        facultyDB.findSupervisor(supervisorID), projectDB.findProject(projectID),
                        prevTitle, newTitle, reqStatus));

                requestLine = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the updated Title Change Requests data back into the text file.
     */
    public void updateFile() {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(file, false));
            PrintWriter pw = new PrintWriter(bf);

            pw.println("Student (Requestor)" + "\t" + "Supervisor (Requestee)" + "\t" + "Project ID" + "\t"
                    + "Previous Title" + "\t" + "New Title" + "Request Status");

            for (RequestChangeTitle rq : requestChangeTitleList) {
                String studentID = rq.getStudent().getUserID();
                String supervisorID = rq.getSupervisor().getUserID();
                int projID = rq.getProjectID();
                String prevTitle = rq.getPrevTitle();
                String newTitle = rq.getNewTitle();
                String reqStatus = rq.getRequestStatus().name();

                pw.println(studentID + "\t" + supervisorID + "\t" + projID + "\t" + prevTitle + "\t" + newTitle
                        + "\t" + reqStatus);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends a Title Change Request to the Database containing all such requests.
     * 
     * @param requestChangeTitle Title Change Request to be appended
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean addRequest(RequestChangeTitle requestChangeTitle) {
        if (requestChangeTitle == null) {
            System.out.println("Change Title Request is NULL.");
            return false;
        }
        requestChangeTitleList.add(requestChangeTitle);
        return true;
    }

    /**
     * Checks whether a Student User has submitted a Title Change Request.
     * 
     * @param student Target Student
     * @return a Boolean to inform us if the Student submitted a request.
     */
    public Boolean findStudent(User student) {
        for (RequestChangeTitle req : requestChangeTitleList) {
            if (req.getStudent().equals(student)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a Supervisor User has one of their projects receive a Title
     * Change Request.
     * 
     * @param supervisor Target Supervisor
     * @return a boolean indicating to us if a request associated with Supervisor is
     *         found.
     */
    public Boolean findSupervisor(User user) {
        for (RequestChangeTitle req : requestChangeTitleList) {
            if (req.getSupervisor() == user) {
                return true;
            }
        }
        return false;
    }

    /**
     * Allows Supervisor to view all the Title Change Requests they had received.
     * 
     * @param currentSupervisor Target Supervisor
     * @return an integer representing whether there is no requests at all.
     */
    public int viewAllTitleChangeRequestSupervisor(Supervisor currentSupervisor) {
        int counter = 1;
        System.out.println("");
        System.out.println("Loading all pending change title requests...");
        for (int i = 0; i < requestChangeTitleList.size(); i += 1) {
            RequestChangeTitle currentRequest = requestChangeTitleList.get(i);
            if (currentRequest.getRequestStatus() == RequestStatus.PENDING
                    && requestChangeTitleList.get(i).getSupervisor().getUserID()
                            .equalsIgnoreCase(currentSupervisor.getUserID())) {
                System.out.println(counter + ". Project ID " + currentRequest.getProjectID() + "'s change of title"
                        + " requested by " + requestChangeTitleList.get(i).getStudent().getUserName());
                counter += 1;
            }
        }
        System.out.println();

        if (counter == 1) {
            System.out.println("Pending Title Change Requests: 0");
            System.out.println("Enter 0 to return to the previous menu.");
            return 1;
        }

        System.out.println();
        System.out.println(); // Prints Empty Line
        return 0;
    }

    /**
     * Method to find the index of the target title change request based on input of
     * user
     * 
     * @param requestChoice Selected Request by User
     * @return Target Request Index
     */
    public int findTitleChangeRequestIndex(int requestChoice) {
        int counter = 1;
        for (int i = 0; i < requestChangeTitleList.size(); i += 1) {
            RequestChangeTitle currentRequest = requestChangeTitleList.get(i);
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
     * View the Details of Title Change Request
     * 
     * @param requestChoice Target Request's Menu Number
     * @return Target Request
     */
    public RequestChangeTitle viewTitleChangeRequestDetailedSupervisor(int requestChoice) {
        int targetRequestIndex = findTitleChangeRequestIndex(requestChoice);
        RequestChangeTitle targetRequest = requestChangeTitleList.get(targetRequestIndex);
        System.out.println("Loading selected request...");
        System.out.println();
        System.out.println(
                "+------------------------------------------------------------------------------------------------------------+");
        System.out.println(
                "|                                       Title Change Request Approval                                        |");
        System.out.println(
                "|------------------------------------------------------------------------------------------------------------|");
        targetRequest.getProject().printProjectDetails();
        System.out.println("Requestee (Student)     : " + targetRequest.getStudent().getUserName());
        System.out.println("Old Title               : " + targetRequest.getPrevTitle());
        System.out.println("Suggested New Title     : " + targetRequest.getNewTitle());
        System.out.println(
                "|------------------------------------------------------------------------------------------------------------|");
        System.out.println(
                "Select 1 to approve the request, and 0 to reject the request and return to the previous menu.");
        System.out.println();

        return targetRequest;
    }

    /**
     * Function for Supervisor to approve change of Project's Title
     * 
     * @param approvedRequest Target Request
     * @return Boolean to note if function was success
     */
    public Boolean approveTitleChangeRequest(RequestChangeTitle approvedRequest) {
        if (approvedRequest == null) {
            return false;
        }

        Project approvedProject = approvedRequest.getProject();
        String newProjectTitle = approvedRequest.getNewTitle();

        // Set Project Title to New
        approvedProject.setProjectTitle(newProjectTitle);

        // Update Request Status so this.user cannot see it again
        approvedRequest.setRequestStatus(RequestStatus.APPROVED);
        System.out.println(
                "Project " + approvedRequest.getPrevTitle() + " has been successfully renamed to " + newProjectTitle);

        // Remove request from list after approval
        // requestChangeTitleList.remove(approvedRequest);

        return true;
    }

    /**
     * For Supervisor to view Pending Title Change Requests only
     * 
     * @param currentSupervisor
     */
    public void viewPendingTitleChangeRequests(Supervisor currentSupervisor) {
        ArrayList<RequestChangeTitle> currentSupervisorList = new ArrayList<>();
        for (int i = 0; i < requestChangeTitleList.size(); i += 1) {
            if (requestChangeTitleList.get(i).getProject().getSupervisor().equals(currentSupervisor)) {
                currentSupervisorList.add(requestChangeTitleList.get(i));
            }
        }
        if (currentSupervisorList.size() != 0) {
            System.out.println("Showing all Change Title Requests ... ");
            for (RequestChangeTitle req : currentSupervisorList) {
                System.out.println("Requester: " + req.getStudent().getUserName());
                System.out.println("Previous Project Title: " + req.getPrevTitle());
                System.out.println("New Project Title: " + req.getNewTitle());
                System.out.println("Request Status: " + req.getRequestStatus().name());
                System.out.println("");
            }
        } else {
            System.out.println("No requests to Change Title from Students");
        }
    }

    /**
     * Function for FYP Coordinator to view all the Title Change Requests
     * 
     * @param fypCoordinator
     * @return the print statements of all the change title requests in FYPMS
     */
    public Boolean printAllHistory(FYPCoordinator fypCoordinator) {
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        System.out.println(
                "|                        List of All Change Title Requests                         |");
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        int counter = 1;
        for (int i = 0; i < requestChangeTitleList.size(); i += 1) {
            RequestChangeTitle currentRequest = requestChangeTitleList.get(i);
            System.out.println(counter + ". | Requester: " + currentRequest.getStudent().getUserName()
                    + " | Requestee: " + currentRequest.getSupervisor().getUserName() +
                    " | Status: " + currentRequest.getRequestStatus().toString());

            counter += 1;
        }

        if (counter == 1) {
            System.out
                    .println("There is currently no title change requests submitted by any user in the FYPMS System.");
        }

        return true;

    }

    /**
     * Method for Student to view his/her change title request history
     * 
     * @param student Target Student
     */
    public void printStudentHistory(Student student) {
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        System.out.println(
                "|                  List of All Requests to Change Title of Project                 |");
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        if (findStudent(student)) {
            for (RequestChangeTitle req : requestChangeTitleList) {
                if (req.getStudent().equals(student)) {
                    System.out.println("Requestee: " + req.getSupervisor().getUserName());
                    System.out.println("Previous Project Title: " + req.getPrevTitle());
                    System.out.println("New Project Title: " + req.getNewTitle());
                    System.out.println("Request Status: " + req.getRequestStatus().name());
                    System.out.println("");
                }
            }
        } else {
            System.out.println("No requests to Change Title");
            System.out.println("");
        }
    }

}
