package Entities;

public class ProjectAllocation {
    private String studentID;
    private String projectID;
    private String supervisorID;

    public ProjectAllocation(String student, String project, String supervisor) {
        this.studentID = student;
        this.projectID = project;
        this.supervisorID = supervisor;
    }

}