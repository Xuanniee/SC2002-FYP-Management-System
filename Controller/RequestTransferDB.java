package Controller;

import java.util.ArrayList;

import Entities.RequestTransfer;
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
}
