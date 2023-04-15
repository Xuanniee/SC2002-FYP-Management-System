package Entities;

import enums.RequestType;
import enums.RequestStatus;

/**
 * Represents a FYP Project Registration Request in the FYP Management System.
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class RequestRegister extends Request {
    /**
     * Represents the Student who made the Registration Request for a FYP Project.
     */
    private Student student;

    /**
     * Represents the Requested FYP Project's Current Supervisor.
     */
    private Supervisor supervisor;

    /**
     * Represents the Requested FYP Project.
     */
    private Project project;

    /**
     * Constructor
     * Creates a new Register Request object when a Student submits a new Register Request while using the FYPMS.
     * 
     * @param student Represents the Student who made the Register Request.
     * @param project Represents the Project being registered.
     */
    public RequestRegister(Student student, Project project) {
        super(RequestType.REGISTER);
        this.student = student;
        this.supervisor = project.getSupervisor();
        this.project = project;
    }

    /**
     * Constructor
     * Creates a new Register Request object when reading files to be stored in the database.
     * 
     * @param student Student Requester of Register Request
     * @param supervisor Current Supervisor of Requested Project
     * @param project Requested Project
     * @param requestStatus Enumeration representing the Status of the Request.
     */
    public RequestRegister(Student student, Supervisor supervisor, Project project, RequestStatus requestStatus) {
        super(RequestType.REGISTER, requestStatus);
        this.student = student;
        this.supervisor = supervisor;
        this.project = project;
    }

    /**
     * Retrieves the Student who made the Register Request.
     * 
     * @return Student Requestee
     */
    public Student getStudent() {
        return this.student;
    }

    /**
     * Retrieves the Supervisor of the Requested Project.
     * 
     * @return Requested Project's Supervisor
     */
    public Supervisor getSupervisor() {
        return this.supervisor;
    }

    /**
     * Retrieves the requested project.
     * 
     * @return Requested Project.
     */
    public Project getProject() {
        return this.project;
    }
}
