package Controller;

import java.util.ArrayList;

import Entities.FYPCoordinator;
import Entities.Project;
import Entities.ProjectAllocation;
import Entities.Student;
import Entities.Supervisor;
import Entities.RequestRegister;
import Entities.User;
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

    private ProjectDB projectDB;
    private StudentDB studentDB;
    private FacultyDB facultyDB;
    private ProjectAllocationDB projectAllocationDB;

    public RequestRegisterDB(ProjectDB projectDB, StudentDB studentDB, FacultyDB facultyDB,
            ProjectAllocationDB projectAllocationDB) {
        this.file = new File(filePath);
        this.requestRegisterList = new ArrayList<RequestRegister>();
        this.readFile();
        this.projectDB = projectDB;
        this.studentDB = studentDB;
        this.facultyDB = facultyDB;
        this.projectAllocationDB = projectAllocationDB;
    }

    public RequestRegisterDB(String filePath, ProjectDB projectDB, StudentDB studentDB, FacultyDB facultyDB,
            ProjectAllocationDB projectAllocationDB) {
        this.file = new File(filePath);
        this.requestRegisterList = new ArrayList<RequestRegister>();
        this.readFile();
        this.projectDB = projectDB;
        this.studentDB = studentDB;
        this.facultyDB = facultyDB;
        this.projectAllocationDB = projectAllocationDB;
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
                int projID = rq.getProject().getProjectID();
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

    public int viewAllRegisterRequestFYPCoord() {
        System.out.println("Loading all pending requests to register for a project...");
        int counter = 1;

        for (int i = 0; i < requestRegisterList.size(); i += 1) {
            RequestRegister currentRequest = requestRegisterList.get(i);
            Project currentProject = currentRequest.getProject();
            if (currentRequest.getRequestStatus() == RequestStatus.PENDING) {
                System.out.println(counter + ". " + currentProject.getProjectTitle() + "  requested by "
                        + requestRegisterList.get(i).getStudent().getUserName());
                counter += 1;
            }
        }
        System.out.println();

        if (counter == 1) {
            System.out.println("Pending Register Requests: 0");
            return 1;
        }

        System.out.println();
        System.out.println(); // Prints Empty Line
        return 0;
    }

    /**
     * Method to find the index of the target request based on input of user
     * 
     * @param requestChoice
     * @return
     */
    public int findRegisterRequestIndex(int requestChoice) {
        int counter = 1;
        for (int i = 0; i < requestRegisterList.size(); i += 1) {
            RequestRegister currentRequest = requestRegisterList.get(i);
            if (currentRequest.getRequestStatus() == RequestStatus.PENDING) {
                if (counter == requestChoice) {
                    return i;
                }
                counter += 1;
            }
        }
        return -1;
    }

    public RequestRegister viewRegisterRequestDetailedFYPCoord(int requestChoice) {
        // int targetRequestIndex = requestChoice - 1;

        int targetRequestIndex = findRegisterRequestIndex(requestChoice);
        RequestRegister targetRequest = requestRegisterList.get(targetRequestIndex);
        // RequestRegister targetRequest = requestRegisterList.get(targetRequestIndex);
        System.out.println("Loading selected request...");
        System.out.println();
        System.out.println(
                "+------------------------------------------------------------------------------------------------+");
        System.out.println(
                "|                                    Register Request Approval                                   |");
        System.out.println(
                "+------------------------------------------------------------------------------------------------+");
        targetRequest.getProject().printProjectDetails();
        System.out.println("Project Status        : " + targetRequest.getProject().getProjectStatus());
        System.out.println("Student who made request: " + targetRequest.getStudent().getUserName());
        System.out.println(
                "+-------------------------------------------------------------------------------------------------+");
        System.out.println("");
        System.out.println(
                "Select 1 to approve the request, and 0 to reject the request and return to the previous menu.");
        System.out.println();

        return targetRequest;
    }

    public Boolean approveRegisterRequestFYPCoord(RequestRegister approvedRequest) {
        if (approvedRequest == null) {
            return false;
        }

        Project approvedProject = approvedRequest.getProject();
        Student approvedStudent = approvedRequest.getStudent();
        Supervisor supervisingSupervisor = approvedRequest.getSupervisor();
        // Set Project Status
        approvedProject.setProjectStatus(ProjectStatus.ALLOCATED);
        // Set Student to Project
        approvedProject.setStudent(approvedStudent);
        approvedStudent.setAssignedProject(approvedProject);
        // Update Supervisor Project List
        supervisingSupervisor.getSupervisingProjectList().add(approvedProject);
        // Update the Allocation DB
        projectAllocationDB.addAllocation(new ProjectAllocation(approvedStudent.getUserID(),
                Integer.toString(approvedProject.getProjectID()), supervisingSupervisor.getUserID()));

        // Update Request Status so this.user cannot see it again
        // requestRegisterList.remove(requestRegisterList.indexOf(approvedRequest));
        approvedRequest.setRequestStatus(RequestStatus.APPROVED);

        System.out.println("Project " + approvedProject.getProjectTitle() + " has been successfully allocated to "
                + approvedStudent.getUserName());
        System.out.println("");

        return true;
    }

    public Boolean findStudent(User student) {
        for (RequestRegister req : requestRegisterList) {
            if (req.getStudent() == student) {
                return true;
            }
        }
        return false;
    }

    public Boolean findSupervisor(User supervisor) {
        for (RequestRegister req : requestRegisterList) {
            if (req.getSupervisor() == supervisor) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function for FYP Coordinator to view all the Register Requests
     * 
     * @param fypCoordinator
     * @return the print statements of all the register requests in FYPMS
     */
    public Boolean printAllHistory(FYPCoordinator fypCoordinator) {
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        System.out.println(
                "|                     List of All Student Registration Requests                    |");
        System.out.println(
                "+----------------------------------------------------------------------------------+");

        int counter = 1;
        for (int i = 0; i < requestRegisterList.size(); i += 1) {
            RequestRegister currentRequest = requestRegisterList.get(i);
            System.out.println(counter + ". | Requester: " + currentRequest.getStudent().getUserName()
                    + " | Requestee: " + currentRequest.getSupervisor().getUserName() +
                    " | Status: " + currentRequest.getRequestStatus().toString());

            counter += 1;
        }

        if (counter == 1) {
            System.out
                    .println("There is currently no register requests submitted by any user in the FYPMS System.");
        }

        return true;
    }

    public void printStudentHistory(Student student) {
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        System.out.println(
                "|                   List of All Requests to Register for Projects                  |");
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        if (findStudent(student)) {
            for (RequestRegister req : requestRegisterList) {
                if (req.getStudent() == student) {
                    System.out.printf("Registering for Project(ID/Title): %s / %s", req.getProject().getProjectID(),
                            req.getProject().getProjectTitle());
                    System.out.println("");
                    System.out.println("Requestee: " + req.getSupervisor().getSupervisorName());
                    System.out.println("Request Status: " + req.getRequestStatus().name());
                    System.out.println("");
                }
            }
        } else {
            System.out.println("No requests to Register");
            System.out.println("");
        }
    }

}
