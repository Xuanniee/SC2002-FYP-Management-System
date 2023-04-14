package Entities;

import enums.RequestType;
import enums.RequestStatus;

/**
 * Represents a Request to Change Title in the FYP Management System.
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class RequestChangeTitle extends Request {

    /**
     * Represents the Student (Requestor) who made the Change Title Request.
     */
    private Student student;
    /**
     * Represents the Supervisor (Requestee) who will decide whether to approve the Change Title Request.
     */
    private Supervisor supervisor;
    /** 
     * Represents the Project under consideration for a title change.
     */
    private Project project;
    /**
     * Represents the Previous Title of project.
     */
    private String prevTitle;
    /**
     * Represents the Suggested (New) Title of project.
     */
    private String newTitle;

    /**
     * Constructor
     * Creates a Request to change title with the requestor (student) and the suggested new title.
     * 
     * @param student Student (Requestor)
     * @param newTitle Suggested title given by Student (requestor)
     */
    public RequestChangeTitle(Student student, String newTitle) {
        super(RequestType.CHANGETITLE);
        this.project = student.getAssignedProject();
        this.student = student;
        this.supervisor = this.project.getSupervisor();
        this.prevTitle = student.getAssignedProject().getProjectTitle();
        this.newTitle = newTitle;
    }

    /**
     * Constructor 
     * Creates a new Change Title Request when reading from database.
     * 
     * @param student Student (Requestor)
     * @param supervisor Supervisor (Requestee)
     * @param project Project under consideration for a title change
     * @param prevTitle Previous title of project
     * @param newTitle Suggested title given by Student (requestor)
     * @param requestStatus Status of this request
     */
    public RequestChangeTitle(Student student,
            Supervisor supervisor,
            Project project,
            String prevTitle,
            String newTitle,
            RequestStatus requestStatus) {
        super(RequestType.CHANGETITLE, requestStatus);
        this.student = student;
        this.supervisor = supervisor;
        this.project = project;
        this.prevTitle = prevTitle;
        this.newTitle = newTitle;
    }

    /**
     * Retrieves the student (Requestor) of this request.
     * 
     * @return Student (Requestor) of this request.
     */
    public Student getStudent() {
        return this.student;
    }

    /**
     * Retrieves the supervisor (Requestee) of this request.
     * 
     * @return Supervisor (Requestee) of this request.
     */
    public Supervisor getSupervisor() {
        return this.supervisor;
    }

    /**
     * Retrieves the project ID of the project under consideration for a title change.
     * 
     * @return Project ID
     */
    public int getProjectID() {
        return this.project.getProjectID();
    }

    /**
     * Retrieves the project under consideration for a title change.
     * 
     * @return Project
     */
    public Project getProject() {
        return this.project;
    }

    /**
     * Retrieves the previous title of project.
     * 
     * @return Previous title of project.
     */
    public String getPrevTitle() {
        return this.prevTitle;
    }

    /**
     * Retrieves the suggested title of project.
     * 
     * @return Suggested title of project.
     */
    public String getNewTitle() {
        return this.newTitle;
    }

}
