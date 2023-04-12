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
import Entities.Supervisor;
import Entities.Project;
import Entities.User;
import enums.RequestStatus;
import enums.UserType;

public class RequestChangeTitleDB extends Database {

    private String filePath = String.join("", super.directory, "request_change_title_list.txt");

    private File file;

    private ArrayList<RequestChangeTitle> requestChangeTitleList;

    private ProjectDB projectDB;
    private StudentDB studentDB;
    private FacultyDB facultyDB;

    /**
     * Read in from Data
     */
    public RequestChangeTitleDB(ProjectDB projectDB, StudentDB studentDB, FacultyDB facultyDB) {
        this.file = new File(filePath);
        this.requestChangeTitleList = new ArrayList<RequestChangeTitle>();
        this.readFile();
    }

    public RequestChangeTitleDB(String filePath, ProjectDB projectDB, StudentDB studentDB, FacultyDB facultyDB) {
        this.file = new File(filePath);
        this.requestChangeTitleList = new ArrayList<RequestChangeTitle>();
        this.readFile();
    }

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

    public void addRequest(RequestChangeTitle requestChangeTitle) {
        requestChangeTitleList.add(requestChangeTitle);
    }

    public Boolean findStudent(User user) {
        for (RequestChangeTitle req : requestChangeTitleList) {
            if (req.getStudent() == user) {
                return true;
            }
        }
        return false;
    }

    public Boolean findSupervisor(User user) {
        for (RequestChangeTitle req : requestChangeTitleList) {
            if (req.getSupervisor() == user) {
                return true;
            }
        }
        return false;
    }

    public int viewAllTitleChangeRequestSupervisor(Supervisor currentSupervisor) {
        int counter = 1;
        System.out.println("Loading all pending change title requests...");
        for (int i = 0; i < requestChangeTitleList.size(); i += 1) {
            RequestChangeTitle currentRequest = requestChangeTitleList.get(i);
            if (currentRequest.getRequestStatus() == RequestStatus.PENDING && requestChangeTitleList.get(i).getProject().getSupervisor().equals(currentSupervisor)) {
                System.out.println(counter + ". Project ID " + currentRequest.getProjectID() + "'s change of title" + " requested by " + requestChangeTitleList.get(i).getStudent());    
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
        System.out.println();       // Prints Empty Line
        return 0;
    }

    /**
     * View the Details of Title Change Request
     * @param requestChoice
     * @return targetRequest
     */
    public RequestChangeTitle viewTitleChangeRequestDetailedSupervisor(int requestChoice) {
        int targetRequestIndex = requestChoice - 1;
        RequestChangeTitle targetRequest = requestChangeTitleList.get(targetRequestIndex);
        System.out.println("Loading selected request...");
        System.out.println();
        System.out.println("+------------------------------------------------------------------------------------------------------------+");
        System.out.println("|                                       Title Change Request Approval                                        |");
        System.out.println("|------------------------------------------------------------------------------------------------------------|");
        targetRequest.getProject().printProjectDetails();
        System.out.println("Requestee (Student)     : " + targetRequest.getStudent());
        System.out.println("Old Title               :" + targetRequest.getPrevTitle());
        System.out.println("Suggested New Title     : " + targetRequest.getNewTitle());
        System.out.println("|------------------------------------------------------------------------------------------------------------|");
        System.out.println("Select 1 to approve the request, and 0 to reject the request and return to the previous menu.");
        System.out.println();

        return targetRequest;
    }

    /**
     * Function for Supervisor to approve change of Project's Title
     * @param approvedRequest
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
        System.out.println("Project " + approvedRequest.getPrevTitle() + " has been successfully renamed to " + newProjectTitle);

        return true;
    }

    /**
     * For Supervisor to view Pending Title Change Requests only
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

    public void printHistory(User user) {
        if (user.getUserType() == UserType.STUDENT) {
            if (findStudent(user)) {
                System.out.println("Showing all Change Title Requests ... ");
                for (RequestChangeTitle req : requestChangeTitleList) {
                    if (req.getStudent().equals(user)) {
                        System.out.println("Requestee: " + req.getSupervisor().getUserName());
                        System.out.println("Previous Project Title: " + req.getPrevTitle());
                        System.out.println("New Project Title: " + req.getNewTitle());
                        System.out.println("Request Status: " + req.getRequestStatus().name());
                        System.out.println("");
                    }
                }
            } else {
                System.out.println("No requests to Change Title for Supervisors");
            }
        }

        else if (user.getUserType() == UserType.FACULTY) {
            if (findSupervisor(user)) {
                System.out.println("Showing all Change Title Requests ... ");
                for (RequestChangeTitle req : requestChangeTitleList) {
                    if (req.getSupervisor() == user) {
                        System.out.println("Requester: " + req.getStudent().getUserName());
                        System.out.println("Previous Project Title: " + req.getPrevTitle());
                        System.out.println("New Project Title: " + req.getNewTitle());
                        System.out.println("Request Status: " + req.getRequestStatus().name());
                        System.out.println("");
                    }
                }
            } else {
                System.out.println("No requests to Change Title from Students");
            }

        }

    }

}
