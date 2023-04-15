package Entities;

import enums.RequestStatus;
import enums.RequestType;

/**
 * Represents a FYP Project De-Register Request in the FYP Management System.
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class RequestDeregister extends Request {
    /**
     * Represents the Project that the Student is currently trying to deregister.
     */
    private Project project;

    /**
     * Represents the Student User who submitted a de-register request.
     */
    private Student student;

    /**
     * Represents the FYP Coordinator who will decide whether to approve the result.
     */
    private FYPCoordinator fypCoordinator;

    /**
     * Creates a New Deregister Request Object that is added to the Database when Student submits.
     * 
     * @param student Requester Student
     * @param project Deregistered Project
     * @param fypCoordinator Approving FYP Coordiantor
     */
    public RequestDeregister(Student student, Project project, FYPCoordinator fypCoordinator) {
        super(RequestType.DEREGISTER);
        this.student = student;
        this.project = project;
        this.fypCoordinator = fypCoordinator;
    }

    /**
     * Creates a Deregister Request to be added to the database when reading and loading the rollover data.
     * 
     * @param student Requester Student
     * @param project Deregistered Project
     * @param requestStatus Denotes the Request Type
     * @param fypCoordinator Approving FYP Coordinator
     */
    public RequestDeregister(Student student, Project project, RequestStatus requestStatus,
            FYPCoordinator fypCoordinator) {
        super(RequestType.DEREGISTER, requestStatus); 
        this.student = student;
        this.project = project;
        this.fypCoordinator = fypCoordinator;
    }

    /*@Override
    public void approveRequest(Boolean approve) {
        if (approve) {
            this.setRequestStatus(RequestStatus.APPROVED);
            this.project.setProjectStatus(ProjectStatus.AVAILABLE);
        } else {
            this.setRequestStatus(RequestStatus.REJECTED);
        }
    }*/

    /**
     * Retrieves the FYP Coordinator who can approve the request.
     * 
     * @return FYP Coordinator
     */
    public Supervisor getRequestCoordinator() {
        return this.fypCoordinator;
    }

    /**
     * Updates the Approving FYP Coordinator.
     * 
     * @param approvalFypCoordinator Current FYP Coordinator
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean setRequestCoordinator(FYPCoordinator approvalFypCoordinator) {
        if (approvalFypCoordinator == null) {
            System.out.println("Missing approval supervisor.");
            return false;
        }
        this.fypCoordinator = approvalFypCoordinator;

        return true;
    }

    /**
     * Retrieves the Student who made the Deregister Request
     * 
     * @return Requester Student
     */
    public Student getStudent() {
        return this.student;
    }

    /**
     * Retrieves the Project being de-registered.
     * 
     * @return Deregistered Project
     */
    public Project getProject() {
        return this.project;
    }

}
