package Controller;

import java.util.ArrayList;

import Entities.FYPCoordinator;
import Entities.Project;
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

/**
 * Represents a Database of all Register Requests in FYP Management System.
 * 
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class RequestRegisterDB extends Database {
    /**
     * Represents the file path of the Database of Register Requests in FYPMS.
     */
    private String filePath = String.join("", super.directory, "request_register_list.txt");

    /**
     * Represents the file containing the database of all Registration Requests
     */
    private File file;

    /**
     * ArrayList containing all the Register Requests in FYPMS
     */
    private ArrayList<RequestRegister> requestRegisterList;

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
     * Creates the Register Request Database when the system is first initialised.
     * 
     * @param projectDB Database containing all projects in FYPMS
     * @param studentDB Database containing all students in FYPMS
     * @param facultyDB Database containing all faculty supervisors in FYPMS
     */
    public RequestRegisterDB(ProjectDB projectDB, StudentDB studentDB, FacultyDB facultyDB) {
        this.file = new File(filePath);
        this.requestRegisterList = new ArrayList<RequestRegister>();
        this.readFile();
        this.projectDB = projectDB;
        this.studentDB = studentDB;
        this.facultyDB = facultyDB;
    }

    /**
     * Reads the Register Requests from provided file.
     */
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

    /**
     * Writes the updated Registered Requests data back into the text file.
     */
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

    /**
     * Appends a Register Request to the Database containing all such requests.
     * 
     * @param requestRegister
     */
    public void addRequest(RequestRegister requestRegister) {
        requestRegisterList.add(requestRegister);
        // Update Supervisor number of supervising project
        // System.out.println("Before Adding: ");
        requestRegister.getSupervisor().editNumProjects(1);

        // When student request to register for a project, the supervisor may hit
        // his/her
        // capacity, hence his remaining projects have to be set to UNAVAILABLE.
        if (requestRegister.getSupervisor().getNumProj() >= 2) {
            projectDB.setSupervisorProjectsToNewStatus(requestRegister.getSupervisor(), ProjectStatus.UNAVAILABLE);
        }
    }

    /**
     * Check if user (supervisor or FYP coordinator) has any pending requests to
     * register
     * 
     * @param fypCoord
     * @return a Boolean to indicate whether there are any pending requests for user
     */
    public Boolean anyPendingRegisterRequestsForUser(User fypCoord) {
        for (int i = 0; i < requestRegisterList.size(); i += 1) {
            if (requestRegisterList.get(i).getRequestStatus() == RequestStatus.PENDING) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prints all the pending Register Requests that were submitted to the FYP
     * Coordinator for their approval.
     * 
     * @return an integer representing whether there is no requests at all.
     */
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
     * @param requestChoice Selected Request by User
     * @return index of target request
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

    /**
     * Prints the specific details of the Register Requests for FYP Coordinator to
     * evaluate whether it should be approved.
     * 
     * @param requestChoice Selected Request by FYP Coordinator
     * @return Selected Register Request Object
     */
    public RequestRegister viewRegisterRequestDetailedFYPCoord(int requestChoice) {
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

    /**
     * Approves and Allocate the Project to the Student.
     * 
     * @param approvedRequest Target Request that was approved
     * @return a Boolean to inform us if the function is working as intended.
     */
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
        supervisingSupervisor.setSupervisingProjectList(approvedProject);

        // Update details of Supervisor
        // When student registers for a project, the supervisor may hit his/her
        // capacity, hence his remaining projects have to be set to UNAVAILABLE.
        if (supervisingSupervisor.getNumProj() >= 2) {
            projectDB.setSupervisorProjectsToNewStatus(supervisingSupervisor, ProjectStatus.UNAVAILABLE);
        }

        // Update Request Status so this.user cannot see it again
        approvedRequest.setRequestStatus(RequestStatus.APPROVED);

        System.out.println("Project " + approvedProject.getProjectTitle() + " has been successfully allocated to "
                + approvedStudent.getUserName());
        System.out.println("");

        return true;
    }

    /**
     * Checks whether a Student User has submitted a Register Request.
     * 
     * @param student Target Student
     * @return a Boolean to inform us if the Student submitted a request.
     */
    public Boolean findStudent(User student) {
        for (RequestRegister req : requestRegisterList) {
            if (req.getStudent() == student) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a Supervisor User has one of their projects receive a Register
     * Request.
     * 
     * @param supervisor Target Supervisor
     * @return a boolean indicating to us if a request associated with Supervisor is
     *         found.
     */
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

    /**
     * Prints the Register Request History for Students
     * 
     * @param student
     */
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
                    System.out.println("Requestee: " + req.getSupervisor().getUserName());
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
