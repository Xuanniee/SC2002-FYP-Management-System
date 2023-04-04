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

    public void changeProjectTitle(String newProjectTitle) {
        this.projectTitle = newProjectTitle;
    } 
}
