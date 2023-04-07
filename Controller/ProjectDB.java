package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Entities.Project;
import enums.ProjectStatus;

public class ProjectDB {

    private ArrayList<Project> projectList = new ArrayList<Project>();
    private int projectCount = 0;

    public ProjectDB() {
        String fileName = "./Data/rollover project.txt";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                // Update the Number of Projects, which is also their Project ID
                updateProjectCount();
                // Split the line by tabs
                String[] values = line.split("\t");
                String professorName = values[0];
                String projectTitle = values[1];

                // TODO Get Supervisor Email
                projectList.add(new Project(projectCount, professorName, "Unknown Email", projectTitle));

                for (String value : values) {
                    System.out.print(value + "\t");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getProjectCount() {
        return projectCount;
    }

    public void updateProjectCount() {
        this.projectCount += 1;
    }

    public Boolean addProject(Project newlyCreatedProject) {
        // Don't need increment projectCount since should incremented before calling
        // this function
        if (newlyCreatedProject == null) {
            return false;
        }
        projectList.add(newlyCreatedProject);

        return true;
    }

    public Project findProject(int projectID) {
        Project targetProject = projectList.get(projectID - 1);
        return targetProject;
    }

    public Boolean changeProjectTitle(int projectID, String newProjectTitle) {
        // Input Validation
        if (projectID > projectCount) {
            return false;
        }

        // Retrieve the Project whose title is to be changed
        Project targetProject = projectList.get(projectID - 1);
        targetProject.setProjectTitle(newProjectTitle);

        return true;
    };

    public ArrayList<Project> retrieveProfessorProjects(String professorName) {
        // Create Project List to be returned
        ArrayList<Project> supervisorProjectList = new ArrayList<Project>();

        for (int i = 0; i < projectList.size(); i += 1) {
            if (projectList.get(i).getStudent().getUserName().equalsIgnoreCase(professorName)) {
                supervisorProjectList.add(projectList.get(i));
            }
        }

        return supervisorProjectList;
    }

    public int getProjectIndexInSupervisorProjectList(int projectID, ArrayList<Project> supervisorProjectList) {
        int index = -1;
        for (int i = 0; i < supervisorProjectList.size(); i += 1) {
            if (supervisorProjectList.get(i).getProjectID() == projectID) {
                index = i;
            }
        }
        return index;
    }

    public void viewAvailableProjects() {
        System.out.println(" ----- List of Available Projects ----- ");
        for (Project project : projectList) {
            if (project.getProjectStatus() == ProjectStatus.AVAILABLE) {
                project.viewProjectDetails();
            }
        }
    }

    // TODO Add SUpervisors Email somehow
}
