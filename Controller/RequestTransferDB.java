package Controller;

import java.util.ArrayList;

import Entities.Project;
import Entities.RequestTransfer;
import Entities.Supervisor;
import Entities.User;
import enums.RequestStatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestTransferDB extends Database {

    private String filePath = String.join("", super.directory, "request_transfer_list.txt");

    private File file;

    private ArrayList<RequestTransfer> requestTransferList;

    private ProjectDB projectDB = new ProjectDB();
    private FacultyDB facultyDB = new FacultyDB();

    public RequestTransferDB() {
        this.file = new File(filePath);
        this.requestTransferList = new ArrayList<RequestTransfer>();
        this.readFile();
    }

    public RequestTransferDB(String filePath) {
        this.file = new File(filePath);
        this.requestTransferList = new ArrayList<RequestTransfer>();
        this.readFile();
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

            pw.println("Supervisor (Requestor)" + "\t" + "Project ID" + "\t" + "Replacement Professor" + "\t"
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

    public void printHistory(User supervisor) {
        if (findSupervisor(supervisor)) {
            System.out.println("Showing all Project Transfer Requests ... ");
            for (RequestTransfer req : requestTransferList) {
                if (req.getCurSupervisor() == supervisor) {
                    System.out.printf("Supervisor (Requestor): " + req.getCurSupervisor().getUserName());
                    System.out.println("Project: " + req.getProject().getProjectTitle());
                    System.out.println("Replacement Supervisor: " + req.getRepSupervisor().getSupervisorName());
                    System.out.println("Request Status: " + req.getRequestStatus().name());
                    System.out.println("");
                }
            }
        } else {
            System.out.println("No requests to Transfer Project");
        }
    }

    /**
     * Allow FYP Coordinator to see Supervisors' Transfer Requests to change their Supervisors
     */
    public int viewAllTransferRequestFYPCoord() {
        System.out.println("Loading all pending requests to change supervisors for a project...");
        for (int i = 0; i < requestTransferList.size(); i += 1) {
            Project currentProject = requestTransferList.get(i).getProject();
            System.out.println((i+1) + ". " + currentProject.getProjectTitle() + "  requested by " + currentProject.getSupervisor());
        }
        System.out.println();

        if (requestTransferList.size() == 0) {
            System.out.println("Pending Transfer Requests: 0");
            System.out.println("Enter 0 to return to the previous menu.");
            return 1;
        }
        System.out.println();
        System.out.println();       // Prints Empty Line
        
        return 0;
    }

    /**
     * Allow FYP coordinator to see the specific details of the Transfer Request
     * @param requestChoice
     * @return
     */
    public RequestTransfer viewTransferRequestDetailedFYPCoord(int requestChoice) {
        int targetRequestIndex = requestChoice - 1;
        RequestTransfer targetRequest = requestTransferList.get(targetRequestIndex);
        System.out.println("Loading selected request...");
        System.out.println();
        System.out.println("+------------------------------------------------------------------------------------------------------------+");
        System.out.println("|                                         Transfer Request Approval                                          |");
        System.out.println("|------------------------------------------------------------------------------------------------------------|");
        targetRequest.getProject().printProjectDetails();
        System.out.println("Project Status               : " + targetRequest.getProject().getProjectStatus());
        System.out.println("Previous Supervisor (Requester): " + targetRequest.getCurSupervisor());
        System.out.println("Replacement Supervisor         : " + targetRequest.getRepSupervisor());
        System.out.println("|------------------------------------------------------------------------------------------------------------|");
        System.out.println("Select 1 to approve the request, and 0 to reject the request and return to the previous menu.");
        System.out.println();

        return targetRequest;
    }

    /**
     * For FYP Coordinator to approve Transfer
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

        // Remove Request from Database
        int indexCompletedRequest = requestTransferList.indexOf(approvedRequest);
        requestTransferList.remove(indexCompletedRequest);
        System.out.println("Project " + approvedProject.getProjectID() + "'s supervisor has been successfully changed from " + currentSupervisor.getSupervisorName() + 
        " to " + replacementSupervisor.getSupervisorName());

        return true;
    }
}
