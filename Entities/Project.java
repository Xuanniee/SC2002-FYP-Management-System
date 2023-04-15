package Entities;

import enums.ProjectStatus;

/**
 * Represents a Project in the FYP Management System.
 * Each project can be classified with 1 of the 4 status: RESERVED, ALLOCATED,
 * AVAILABLE, UNAVILABLE
 * 
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class Project {
    /**
     * Unique Identifying Project ID
     */
    protected int projectID;

    /**
     * Boolean describing whether a Project's Current Supervisor is requesting to be
     * replaced
     */
    protected Boolean awaitingTransferRequest = false;

    /**
     * Boolean describing whether a Project's Title is currently being requested to
     * be changed.
     */
    protected Boolean awaitingTitleChangeRequest = false;

    /**
     * Project's Current Supervisor
     */
    protected Supervisor supervisor;

    /**
     * Project's Allocated Student
     */
    protected Student student;

    /**
     * Project's Title
     */
    protected String projectTitle;

    /**
     * Current State/Status of Project, e.g. whether available or not.
     */
    protected ProjectStatus projectStatus;

    /**
     * Constructor
     * Creates a Project object that contains information about the Supervisor et
     * cetera in FYPMS.
     * 
     * @param projectID    Unique Identifying Project Number
     * @param supervisor   Current Supervisor
     * @param projectTitle Title of Project
     */
    public Project(int projectID, Supervisor supervisor, String projectTitle) {
        this.projectID = projectID;
        this.supervisor = supervisor;
        this.student = null;
        this.projectTitle = projectTitle;
        this.projectStatus = ProjectStatus.AVAILABLE;
    }

    /**
     * Retrieves the Title Change Status of Project
     * 
     * @return Title Change Status Boolean
     */
    public Boolean getAwaitingTitleChangeRequest() {
        return this.awaitingTitleChangeRequest;
    }

    /**
     * Updates the Title Change Status of Project.
     * 
     * @param status New Title Change Status
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean setAwaitingTitleChangeRequest(Boolean status) {
        if (status == null) {
            return false;
        }

        this.awaitingTitleChangeRequest = status;
        return true;
    }

    /**
     * Retrieves the Transfer Request Status of Project
     * 
     * @return Transfer Request Status
     */
    public Boolean getAwaitingTransferRequest() {
        return this.awaitingTransferRequest;
    }

    /**
     * Updates the Transfer Request Status of Project
     * 
     * @param status New Transfer Request Status
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean setAwaitingTransferRequest(Boolean status) {
        if (status == null) {
            return false;
        }

        this.awaitingTransferRequest = status;
        return true;
    }

    /**
     * Retrieves the Project ID.
     * 
     * @return Project ID
     */
    public int getProjectID() {
        return projectID;
    }

    /**
     * Retrieves the Current Supervisor of this Project.
     * 
     * @return Current Supervisor
     */
    public Supervisor getSupervisor() {
        return this.supervisor;
    }

    /**
     * Updates the Supervising Faculty Member of the Project
     * 
     * @param supervisor Replacement Supervisor
     */
    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    /**
     * Retrieves the Project's allocated Student
     * 
     * @return Project's Student
     */
    public Student getStudent() {
        return this.student;
    }

    /**
     * Updates the Project's allocated Student
     * 
     * @param student New Student
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Updates the Project Title.
     * 
     * @param newProjectTitle New Project Title
     */
    public void setProjectTitle(String newProjectTitle) {
        this.projectTitle = newProjectTitle;
    }

    /**
     * Retrieves the Project Title
     * 
     * @return Project Title
     */
    public String getProjectTitle() {
        return projectTitle;
    }

    /**
     * Retrieve the Current Project Status.
     * 
     * @return Enum Project Status
     */
    public ProjectStatus getProjectStatus() {
        return this.projectStatus;
    }

    /**
     * Updates the Project Status.
     * 
     * @param projectStatus New Project Status
     */
    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    /**
     * Prints out all the relevant details of a particular project.
     */
    public void printProjectDetails() {
        System.out.println("ProjectID: " + this.projectID);
        System.out.println("Title of Project: " + this.projectTitle);
        System.out.println("    Faculty In-charge's Name: " + this.getSupervisor().getUserName());
        System.out.println("    Faculty In-charge's Email: " + this.getSupervisor().getUserEmail());
        if (this.projectStatus == ProjectStatus.ALLOCATED) {
            System.out.println("This project is allocated to the following student:");
            System.out.println("    Student's Name: " + this.getStudent().getUserName());
            System.out.println("    Student's Email: " + this.getStudent().getUserEmail());
        }
        System.out.println("");
    }

    /**
     * Function to deregister a Student from their allocated Project.
     * 
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean deregisterStudent() {
        System.out.println(
                "Student " + this.student.userName + " has been deregistered from the Project " + this.projectID);
        if (this.student == null) {
            System.out.println("Error. No Student has been allocated in the first place.");
            return false;
        }
        this.student = null;
        this.projectStatus = ProjectStatus.AVAILABLE;

        return true;
    }
}
