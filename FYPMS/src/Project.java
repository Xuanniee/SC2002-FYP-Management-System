public class Project {
    protected int projectID;
    protected String supervisorName;
    protected String supervisorEmail;
    protected String projectTitle;
    protected ProjectStatus projectStatus;

    public enum ProjectStatus {
        AVAILABLE,
        RESERVED,
        UNAVAILABLE,
        ALLOCATED
    }

    // Constructors
    public Project(int projectID, String supervisorName, String supervisorEmail, String projectTitle) {
        this.projectID = projectID;
        this.supervisorName = supervisorName;
        this.supervisorEmail = supervisorEmail;
        this.projectTitle = projectTitle;
        this.projectStatus = ProjectStatus.AVAILABLE;
    }

    // Getters and Setters
    // projectID
    public int getProjectID() {
        return this.projectID;
    }

    // supervisorName
    public String getSupervisorName() {
        return this.supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    // supervisorEmail
    public String getSupervisorEmail() {
        return supervisorEmail;
    }

    public void setSupervisorEmail(String supervisorEmail) {
        this.supervisorEmail = supervisorEmail;
    }

    // projectTitle
    public String getProjectTitle() {
        return projectTitle;
    }

    public void changeProjectTitle(String newProjectTitle) {
        this.projectTitle = newProjectTitle;
    } 

    // projectStatus
    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    // Method to display all information the project
    public void displayProjectInfo() {
        System.out.println("------------- PROJECT INFO -------------");
        System.out.println("Project ID: " + this.projectID);
        System.out.println("Project Title: " + this.projectTitle);
        System.out.println("Project Status: " + this.projectStatus);
        System.out.println("Supervisor Name: " + this.supervisorName);
        System.out.println("Supervisor email: " + this.supervisorEmail);
        System.out.println("----------------------------------------");
    }

}
