package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Entities.Project;
import Entities.Student;
import Entities.Supervisor;
import enums.ProjectStatus;

public class ProjectDB extends Database {

    /*
     * private ArrayList<Project> projectList = new ArrayList<Project>();
     * private int projectCount = 0;
     * 
     * public ProjectDB() {
     * String fileName = "./Data/rollover project.txt";
     * String line;
     * boolean isFirstLine = true;
     * try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
     * while ((line = br.readLine()) != null) {
     * 
     * if (isFirstLine) {
     * isFirstLine = false;
     * continue; // skip the first line
     * }
     * // Update the Number of Projects, which is also their Project ID
     * updateProjectCount();
     * // Split the line by tabs
     * String[] values = line.split("\t");
     * String professorName = values[0];
     * String projectTitle = values[1];
     * 
     * // TODO Get Supervisor Email
     * projectList.add(new Project(projectCount, professorName, "Unknown Email",
     * projectTitle));
     * 
     * for (String value : values) {
     * System.out.print(value + "\t");
     * }
     * System.out.println();
     * }
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     */

    private String filePath = String.join("", super.directory, "rollover project.txt");

    private File file;

    public ArrayList<Project> projectList;
    private int projectCount = 0;

    private FacultyDB facultyDB = new FacultyDB();

    public ProjectDB() {
        this.file = new File(filePath);
        this.projectList = new ArrayList<Project>();
        this.readFile();
    }

    public ProjectDB(String filePath) {
        this.file = new File(filePath);
        this.projectList = new ArrayList<Project>();
        this.readFile();
    }

    public void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String projectLine = br.readLine();
            projectLine = br.readLine();
            String supervisorName, projectTitle;
            String[] projectData;

            while (projectLine != null) {
                projectData = projectLine.split(super.delimiter);
                supervisorName = projectData[0];
                projectTitle = projectData[1];

                for (Supervisor currentSupervisor : facultyDB.getSupervisorList()) {
                    if (currentSupervisor.getUserName().equalsIgnoreCase(supervisorName)) {
                        Supervisor targetSupervisor = currentSupervisor;
                        projectList.add(new Project(getProjectCount(), targetSupervisor, projectTitle));
                        updateProjectCount();
                        break;
                    }
                }
                projectLine = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateFile() {

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
            if (projectList.get(i).getSupervisor().getUserName().equalsIgnoreCase(professorName)) {
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

    public void viewAvailableProjects(Student student) {
        System.out.println(" ----- List of Available Projects ----- ");

        // Do not print projects that student is deregistered from
        if (!student.getDeregisteredProjects().isEmpty()) {
            for (Project project : projectList) {
                if (project.getProjectStatus() == ProjectStatus.AVAILABLE) {
                    for (Project p : student.getDeregisteredProjects()) {
                        if (project.getProjectID() != p.getProjectID()) {
                            project.printProjectDetails();
                        } else
                            break;
                    }
                }
            }
            // Print all other AVAILABLE projects
        } else {
            for (Project project : projectList) {
                if (project.getProjectStatus() == ProjectStatus.AVAILABLE) {
                    project.printProjectDetails();
                }
            }
        }
    }

    public ArrayList<Project> getAllProjects() {
        return projectList;
    }

    public void viewAllProjectsFYPCoord() {
        Boolean available = false;
        Boolean allocated = false;
        Boolean reserved = false;
        Boolean unavailable = false;

        System.out.println();
        System.out.println(" ----- List of Available Projects ----- ");
        for (Project project : projectList) {
            if (project.getProjectStatus() == ProjectStatus.AVAILABLE) {
                project.printProjectDetails();
                available = true;
            }
        }
        if (!available) {
            System.out.println("There are currently no available project(s).");
            System.out.println();
        }

        System.out.println();
        System.out.println(" ----- List of Allocated Projects ----- ");
        for (Project project : projectList) {
            if (project.getProjectStatus() == ProjectStatus.ALLOCATED) {
                project.printProjectDetails();
                allocated = true;
            }
        }
        if (!allocated) {
            System.out.println("There are currently no allocated project(s).");
            System.out.println();
        }

        System.out.println();
        System.out.println(" ----- List of Reserved Projects ----- ");
        for (Project project : projectList) {
            if (project.getProjectStatus() == ProjectStatus.RESERVED) {
                project.printProjectDetails();
                reserved = true;
            }
        }
        if (!reserved) {
            System.out.println("There are currently no reserved project(s).");
            System.out.println();
        }

        System.out.println();
        System.out.println(" ----- List of Unavailable Projects ----- ");
        for (Project project : projectList) {
            if (project.getProjectStatus() == ProjectStatus.UNAVAILABLE) {
                project.printProjectDetails();
                unavailable = true;
            }
        }
        if (!unavailable) {
            System.out.println("There are currently no unavailable project(s).");
            System.out.println();
        }
    }

    // TODO Add SUpervisors Email somehow
}
