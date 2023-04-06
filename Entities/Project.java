package Entities;

public class Project {

    enum ProjectStatus {
        AVAILABLE, UNAVAILABLE, RESERVED, ALLOCATED
    }

    private static int projectCount = 0;
    private int projectID;
    private String projectTitle;
    private ProjectStatus projectStatus;
    private Student student;
    private Faculty faculty;

    public Project(String projectTitle) {
        this.projectTitle = projectTitle;
        this.projectStatus = ProjectStatus.AVAILABLE;
        this.projectID = projectCount++;
    }

    /**
     * Get ID number of Project
     * 
     * @return ID number of Project
     */
    public int getProjectID() {
        return this.projectID;
    }

    /**
     * Get Name of this Project
     * 
     * @return Name of this Project
     */
    public String getProjectTitle() {
        return this.projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public ProjectStatus getProjectStatus() {
        return this.projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public void setStudent(Student student) {
        this.student = student;
        this.projectStatus = ProjectStatus.UNAVAILABLE;
    }

    public String getStudentName() {
        return student.getUserName();
    }

    public String getStudentEmail() {
        return student.getUserEmail();
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public String getFacultyName() {
        return faculty.getUserName();
    }

    public String getFacultyEmail() {
        return faculty.getUserEmail();
    }

    public void viewProjectDetails() {
        System.out.println("Title of Project: " + this.projectTitle);
        System.out.println("Faculty In-charge's Name: " + this.getFacultyName());
        System.out.println("Faculty In-charge's Email: " + this.getFacultyEmail());
        System.out.println("Student's Name: " + this.getStudentName());
        System.out.println("Student's Email: " + this.getStudentEmail());
    }
}
