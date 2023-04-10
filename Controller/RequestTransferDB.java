package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Entities.Project;
import Entities.Student;
import Entities.RequestTransfer;
import enums.ProjectStatus;
import enums.RequestStatus;

public class RequestTransferDB extends Database{
    private String filePath = String.join("", super.directory, "request_transfer_list.txt");

    private File file;

    private ArrayList<RequestTransfer> requestTransferList;

    private ProjectDB projectDB = new ProjectDB();
    private StudentDB studentDB = new StudentDB();
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

    // TODO Read and Update
    public void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String requestLine = br.readLine();
            requestLine = br.readLine();
            String studentID, supervisorID;
            int projectID;
            RequestStatus reqStatus;
            String[] requestData;

            while (requestLine != null) {
                requestData = requestLine.split(super.delimiter);
                studentID = requestData[0];
                supervisorID = requestData[1];
                projectID = Integer.parseInt(requestData[2]);
                reqStatus = RequestStatus.valueOf(requestData[3]);

                // Add new record to list
                requestRegisterList.add(new RequestRegister(studentDB.findStudent(studentID),
                        facultyDB.findSupervisor(supervisorID), projectDB.findProject(projectID), reqStatus));

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

            for (RequestRegister rq : requestRegisterList) {
                String studentID = rq.getStudent().getUserID();
                String supervisorID = rq.getSupervisor().getUserID();
                int projID = rq.getProjectID();
                String reqStatus = rq.getRequestStatus().name();

                pw.println(studentID + "\t" + supervisorID + "\t" + projID + "\t" + reqStatus);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRequest(RequestTransfer requestTransfer) {
        requestTransferList.add(requestTransfer);
    }

}
