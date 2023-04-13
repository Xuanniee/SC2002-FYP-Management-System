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
import Entities.RequestTransfer;
import Entities.Supervisor;
import Entities.FYPCoordinator;
import Entities.User;
import enums.RequestStatus;

public class RequestTransferDB extends Database {

    private String filePath = String.join("", super.directory, "request_transfer_list.txt");

    private File file;

    private ArrayList<RequestTransfer> requestTransferList;

    private ProjectDB projectDB;
    private FacultyDB facultyDB;

    public RequestTransferDB(ProjectDB projectDB, FacultyDB facultyDB) {
        this.file = new File(filePath);
        this.requestTransferList = new ArrayList<RequestTransfer>();
        this.readFile();
        this.projectDB = projectDB;
        this.facultyDB = facultyDB;
    }

    public RequestTransferDB(String filePath, ProjectDB projectDB, FacultyDB facultyDB) {
        this.file = new File(filePath);
        this.requestTransferList = new ArrayList<RequestTransfer>();
        this.readFile();
        this.projectDB = projectDB;
        this.facultyDB = facultyDB;
    }

    public void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String requestLine = br.readLine();
            requestLine = br.readLine();
            String supervisorID, repSupervisorID;
            int projectID;
            RequestStatus reqStatus;
            String[] requestData;

            while (requestLine != null) {
                requestData = requestLine.split(super.delimiter);
                supervisorID = requestData[0];
                projectID = Integer.parseInt(requestData[1]);
                repSupervisorID = requestData[2];
                reqStatus = RequestStatus.valueOf(requestData[3]);

                // Add new record to list
                requestTransferList.add(new RequestTransfer(projectDB.findProject(projectID),
                        facultyDB.findSupervisor(supervisorID), facultyDB.findSupervisor(repSupervisorID), reqStatus));

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

            pw.println("Supervisor (Requestor)" + "\t" + "Project ID" + "\t" + "Replacement Supervisor" + "\t"
                    + "Request Status");

            for (RequestTransfer rq : requestTransferList) {
                String curSupervisorID = rq.getCurSupervisor().getUserID();
                int projID = rq.getProject().getProjectID();
                String repSupervisorID = rq.getRepSupervisor().getUserID();
                String reqStatus = rq.getRequestStatus().name();
                pw.println(curSupervisorID + "\t" + projID + "\t" + repSupervisorID + "\t" + reqStatus);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRequest(RequestTransfer requestTransfer) {
        requestTransferList.add(requestTransfer);
    }

    public Boolean findSupervisor(User supervisor) {
        for (RequestTransfer req : requestTransferList) {
            if (req.getCurSupervisor() == supervisor) {
                return true;
            }
        }
        return false;
    }

    public void viewTransferRequestsForSupervisor(Supervisor currentSupervisor) {
        ArrayList<RequestTransfer> currentSupervisorList = new ArrayList<>();
        for (int i = 0; i < requestTransferList.size(); i += 1) {
            if (requestTransferList.get(i).getCurSupervisor().equals(currentSupervisor)) {
                currentSupervisorList.add(requestTransferList.get(i));
            }
        }
        if (currentSupervisorList.size() != 0) {
            System.out.println("Showing all your Transfer Requests ... ");
            for (RequestTransfer req : requestTransferList) {
                System.out.println("Project Title: " + req.getProject().getProjectTitle());
                System.out.println("Requester: " + req.getCurSupervisor().getUserName());
                System.out.println("Replacement Supervisor ID: " + req.getRepSupervisor().getUserID());
                System.out.println("Replacement Supervisor Name: " + req.getRepSupervisor().getUserName());
                System.out.println("Request Status: " + req.getRequestStatus().name());
                System.out.println("");
            }
        } else {
            System.out.println("You did not apply for any Transfers before.");
        }

    }

    /**
     * Function for FYP Coordinator to view all the Transfer Requests
     * 
     * @param fypCoordinator
     * @return the print statements of all the transfer requests in FYPMS
     */
    public Boolean printAllHistory(FYPCoordinator fypCoordinator) {
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        System.out.println(
                "|                           List of All Transfer Requests                          |");
        System.out.println(
                "+----------------------------------------------------------------------------------+");

        int counter = 1;
        for (int i = 0; i < requestTransferList.size(); i += 1) {
            RequestTransfer currentRequest = requestTransferList.get(i);
            System.out.println(counter + ". | Requester: " + currentRequest.getCurSupervisor().getUserName()
                    + " | Requestee: " + fypCoordinator.getUserName() +
                    " | Status: " + currentRequest.getRequestStatus().toString());

            counter += 1;
        }

        if (counter == 1) {
            System.out
                    .println("There is currently no transfer requests submitted by any user in the FYPMS System.");
        }

        return true;
    }

    /**
     * Allow FYP Coordinator to see Supervisors' Transfer Requests to change their
     * Supervisors
     */
    public int viewAllTransferRequestFYPCoord() {
        System.out.println("Loading all pending requests to change supervisors for a project...");
        int counter = 1;
        for (int i = 0; i < requestTransferList.size(); i += 1) {
            RequestTransfer currentRequest = requestTransferList.get(i);
            Project currentProject = currentRequest.getProject();
            if (currentRequest.getRequestStatus() == RequestStatus.PENDING) {
                System.out.println(counter + ". " + currentProject.getProjectTitle() + "  requested by "
                        + currentProject.getSupervisor().getUserName());
                counter += 1;
            }
        }
        System.out.println();

        if (counter == 1) {
            System.out.println("Pending Transfer Requests: 0");
            System.out.println("Enter 0 to return to the previous menu.");
            return 1;
        }
        System.out.println();
        System.out.println(); // Prints Empty Line

        return 0;
    }

    /**
     * Allow FYP coordinator to see the specific details of the Transfer Request
     * 
     * @param requestChoice
     * @return
     */
    public RequestTransfer viewTransferRequestDetailedFYPCoord(int requestChoice) {
        int targetRequestIndex = requestChoice - 1;
        RequestTransfer targetRequest = requestTransferList.get(targetRequestIndex);
        System.out.println("Loading selected request...");
        System.out.println();
        System.out.println(
                "+------------------------------------------------------------------------------------------------------------+");
        System.out.println(
                "|                                         Transfer Request Approval                                          |");
        System.out.println(
                "|------------------------------------------------------------------------------------------------------------|");
        targetRequest.getProject().printProjectDetails();
        System.out.println("Project Status               : " + targetRequest.getProject().getProjectStatus());
        System.out.println("Previous Supervisor (Requester): " + targetRequest.getCurSupervisor().getUserName());
        System.out.println("Replacement Supervisor         : " + targetRequest.getRepSupervisor().getUserName());
        System.out.println(
                "|------------------------------------------------------------------------------------------------------------|");
        System.out.println(
                "Select 1 to approve the request, and 0 to reject the request and return to the previous menu.");
        System.out.println();

        return targetRequest;
    }

    /**
     * For FYP Coordinator to approve Transfer
     * 
     * @param approvedRequest
     * @return
     */
    public Boolean approveTransferRequestFYPCoord(RequestTransfer approvedRequest) {
        if (approvedRequest == null) {
            return false;
        }

        // Retrieve the Project and Replacement Supervisor
        Project approvedProject = approvedRequest.getProject();
        Supervisor replacementSupervisor = approvedRequest.getRepSupervisor();
        Supervisor currentSupervisor = approvedRequest.getCurSupervisor();

        // Replace Project Supervisor
        approvedProject.setSupervisor(replacementSupervisor);
        // Update Supervisors {Both Old and New}
        replacementSupervisor.setSupervisingProjectList(approvedProject);
        currentSupervisor.removeSupervisingProjectList(approvedProject);

        // Update Request Status so this.user cannot see it again
        // int indexCompletedRequest = requestTransferList.indexOf(approvedRequest);
        // requestTransferList.remove(indexCompletedRequest);
        approvedRequest.setRequestStatus(RequestStatus.APPROVED);
        System.out.println("Project " + approvedProject.getProjectID()
                + "'s supervisor has been successfully changed from " + currentSupervisor.getSupervisorName() +
                " to " + replacementSupervisor.getSupervisorName());

        return true;
    }
}
