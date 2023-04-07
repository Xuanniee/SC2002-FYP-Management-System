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
import enums.RequestStatus;

public class RequestChangeTitleDB extends Database {

    private String filePath = String.join("", super.directory, "request_change_title_list.txt");

    private File file;

    private ArrayList<RequestChangeTitle> requestChangeTitleList;

    private ProjectDB projectDB = new ProjectDB();
    private StudentDB studentDB = new StudentDB();
    private FacultyDB facultyDB = new FacultyDB();

    public RequestChangeTitleDB() {
        this.file = new File(filePath);
        this.requestChangeTitleList = new ArrayList<RequestChangeTitle>();
        this.readFile();
    }

    public RequestChangeTitleDB(String filePath) {
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

}
