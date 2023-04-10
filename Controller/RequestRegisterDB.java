package Controller;

import java.util.ArrayList;

import Entities.Project;
import Entities.Student;
import Entities.RequestRegister;
import enums.ProjectStatus;
import enums.RequestStatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestRegisterDB extends Database {

    private String filePath = String.join("", super.directory, "request_register_list.txt");

    private File file;

    private ArrayList<RequestRegister> requestRegisterList;

    private ProjectDB projectDB = new ProjectDB();
    private StudentDB studentDB = new StudentDB();
    private FacultyDB facultyDB = new FacultyDB();

    public RequestRegisterDB() {
        this.file = new File(filePath);
        this.requestRegisterList = new ArrayList<RequestRegister>();
        this.readFile();
    }

    public RequestRegisterDB(String filePath) {
        this.file = new File(filePath);
        this.requestRegisterList = new ArrayList<RequestRegister>();
        this.readFile();
    }

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

    public void addRequest(RequestRegister requestRegister) {
        requestRegisterList.add(requestRegister);
    }

    // ArrayList<RequestRegister>
    public void viewAllRegisterRequestFYPCoord() {
        System.out.println("Loading all pending requests to register for a project...");
        for (int i = 0; i < requestRegisterList.size(); i += 1) {
            Project currentProject = requestRegisterList.get(i).getProject();
            System.out.println((i+1) + ". " + currentProject.getProjectTitle() + "  requested by " + requestRegisterList.get(i).getStudent());
        }
        System.out.println("Enter 0 to return to the previous menu.");
        System.out.println();       // Prints Empty Line
    }

    public RequestRegister viewRegisterRequestDetailedFYPCoord(int requestChoice) {
        int targetRequestIndex = requestChoice - 1;
        RequestRegister targetRequest = requestRegisterList.get(targetRequestIndex);
        System.out.println("Loading selected request...");
        System.out.println();
        System.out.println("+------------------------------------------------------------------------------------------------------------+");
        System.out.println("|                                         Register Request Approval                                          |");
        System.out.println("|------------------------------------------------------------------------------------------------------------|");
        targetRequest.getProject().printProjectDetails();
        System.out.println("Project Status: ");
        System.out.println("Student who made request: " + targetRequest.getStudent());
        System.out.println("|------------------------------------------------------------------------------------------------------------|");
        System.out.println("Select 1 to approve the request, and 0 to reject the request and return to the previous menu.");
        System.out.println();

        return targetRequest;
    }

    public Boolean approveRegisterRequestFYPCoord(RequestRegister approvedRequest) {
        if (approvedRequest == null) {
            return false;
        }

        Project approvedProject = approvedRequest.getProject();
        Student approvedStudent = approvedProject.getStudent();
        // Set Project Status
        approvedProject.setProjectStatus(ProjectStatus.ALLOCATED);
        // Set Student to Project
        approvedProject.setStudent(approvedStudent);
        approvedStudent.setAssignedProject(approvedProject);

        // Remove Request from Database
        requestRegisterList.remove(requestRegisterList.indexOf(approvedRequest));
        System.out.println("Project " + approvedProject.getProjectTitle() + " has been successfully allocated to " + approvedStudent.getStudentName());

        return true;

    }

}
