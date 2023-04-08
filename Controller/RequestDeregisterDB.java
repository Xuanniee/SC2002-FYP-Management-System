package Controller;

import java.util.ArrayList;

import Entities.RequestDeregister;
import enums.RequestStatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestDeregisterDB extends Database {

    private String filePath = String.join("", super.directory, "request_deregister_list.txt");

    private File file;

    private ArrayList<RequestDeregister> requestDeregisterList;

    private ProjectDB projectDB = new ProjectDB();
    private StudentDB studentDB = new StudentDB();

    public RequestDeregisterDB() {
        this.file = new File(filePath);
        this.requestDeregisterList = new ArrayList<RequestDeregister>();
        this.readFile();
    }

    public RequestDeregisterDB(String filePath) {
        this.file = new File(filePath);
        this.requestDeregisterList = new ArrayList<RequestDeregister>();
        this.readFile();
    }

    public void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String requestLine = br.readLine();
            requestLine = br.readLine();
            String studentID;
            int projectID;
            RequestStatus reqStatus;
            String[] requestData;

            while (requestLine != null) {
                requestData = requestLine.split(super.delimiter);
                studentID = requestData[0];
                //supervisorID = requestData[1];
                projectID = Integer.parseInt(requestData[1]);
                reqStatus = RequestStatus.valueOf(requestData[2]);

                // Add new record to list
                requestDeregisterList.add(new RequestDeregister(studentDB.findStudent(studentID),
                         projectDB.findProject(projectID), reqStatus));

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
                    "Student (Requestor)" + "\t" + "Project ID" + "Request Status");

            for (RequestDeregister rq : requestDeregisterList) {
                String studentID = rq.getStudent().getUserID();
                int projID = rq.getProjectID();
                String reqStatus = rq.getRequestStatus().name();

                pw.println(studentID +"\t" + projID + "\t" + reqStatus);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRequest(RequestDeregister requestDeregister) {
        requestDeregisterList.add(requestDeregister);
    }
    
}
