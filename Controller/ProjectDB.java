package Controller;

import java.util.ArrayList;

import Entities.Project;

public class ProjectDB {

    ArrayList<Project> projects;

    private FacultyDB facultyDB = new FacultyDB();

    public ProjectDB() {
        this.projects = facultyDB.getAllProjects();
    }

    // Add in condition to not show the deregistered projects of a particular
    // student
    public void viewAvailableProjects() {
        System.out.println("--- List of Available Projects ---");
        for (Project project : this.projects) {
            System.out.println("ProjectID: " + project.getProjectID());
            System.out.println("Project Title: " + project.getProjectTitle());
            System.out.println("Faculty IC's Name: " + project.getFacultyName());
            System.out.println("Faculty IC's Email: " + project.getFacultyEmail());
        }
    }

}
