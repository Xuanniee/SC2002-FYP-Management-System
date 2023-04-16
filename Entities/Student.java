package Entities;

/**
 * Represents a Student User in the FYP Management System.
 * 
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class Student extends User {

    /**
     * This student's registered project.
     */
    private Project project;
    /**
     * Indicator of whether this student is registered to any project.
     */
    private Boolean isAssigned = false;
    /**
     * Indicator of whether this student has applied for any project.
     */
    private Boolean hasAppliedForProject = false;
    /**
     * Indicator of whether this student has deregistered from FYP.
     */
    private Boolean isDeregistered = false;

    /**
     * Constructor
     * Creates a new Student User with the given User ID, Name, and Email Address
     * Password will be the default password, but Users can change it after logging
     * in for the first time.
     * 
     * @param userID    Login ID of user
     * @param userName  User's legal name
     * @param userEmail User's email address
     */
    public Student(String userID, String userName, String userEmail, String userPassword) {
        super(userID, userName, userEmail);
        this.project = null;
        this.password = userPassword;
    }

    /**
     * Gets the Boolean indicating whether a Student has applied for a FYP Project.
     * 
     * @return a status boolean.
     */
    public Boolean getHasAppliedForProject() {
        return this.hasAppliedForProject;
    }

    /**
     * Updates the status boolean that indicates whether a student has applied for a
     * FYP Project.
     * Applied project's status will be updated to Reserved until FYP Coordinator
     * approves or rejects the request.
     * 
     * @param status this Student User's new Status
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean setHasAppliedForProject(Boolean status) {
        // Input Validation
        if (status == null) {
            return false;
        }

        this.hasAppliedForProject = status;
        return true;
    }

    /**
     * Updates this Student's relevant project status after FYP Coordinator has
     * approved their registration request.
     * 
     * @param project Represents this Student's allocated FYP Project
     */
    public void setAssignedProject(Project project) {
        this.project = project;
        this.isAssigned = true;
    }

    /**
     * Gets the boolean representing whether this Student has a registered FYP
     * project.
     * 
     * @return whether a Student has an allocated project.
     */
    public Boolean getIsAssigned() {
        return this.isAssigned;
    }

    /**
     * Updates the status of this Student with regards to whether they have an
     * allocated project.
     * 
     * @param assign Represents this Student's updated status.
     */
    public void setIsAssigned(Boolean assign) {
        this.isAssigned = assign;
    }

    /**
     * Retrieves this Student's allocated FYP Project.
     * 
     * @return this Student's Project.
     */
    public Project getAssignedProject() {
        return this.project;
    }

    /**
     * Retrieves the Status representing whether this Student's has deregistered a
     * project before.
     * 
     * @return this Student's deregistered status as a boolean.
     */
    public Boolean getIsDeregistered() {
        return this.isDeregistered;
    }

    /**
     * Updates whether this Student has deregistered a project before.
     * 
     * @param status Represents this Student's new deregistered status.
     */
    public void setIsDeregistered(Boolean status) {
        this.isDeregistered = status;
    }

}
