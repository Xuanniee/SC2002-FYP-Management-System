package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

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

    private FacultyDB facultyDB;

    public ProjectDB(FacultyDB facultyDB) {
        this.facultyDB = facultyDB;
        this.file = new File(filePath);
        this.projectList = new ArrayList<Project>();
        this.readFile();
    }

    public ProjectDB(String filePath, FacultyDB facultyDB) {
        this.facultyDB = facultyDB;
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
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(file, false));
            PrintWriter pw = new PrintWriter(bf);

            pw.println("Supervisor" + "\t" + "Title");

            for (Project proj : projectList) {
                String creatorName = proj.getSupervisor().getUserName();
                String projTitle = proj.getProjectTitle();

                pw.println(creatorName + "\t" + projTitle);
            }
            pw.close();
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
        Project targetProject = projectList.get(projectID);
        return targetProject;
    }

    public Boolean changeProjectTitle(int projectID, String newProjectTitle) {
        // Input Validation
        if (projectID > projectCount) {
            return false;
        }

        // Retrieve the Project whose title is to be changed
        Project targetProject = projectList.get(projectID);
        targetProject.setProjectTitle(newProjectTitle);

        return true;
    };

    /**
     * Return list of projects owned by particular Supervisor
     */
    public ArrayList<Project> retrieveSupervisorProjects(String supervisorID) {
        // Create Project List to be returned
        ArrayList<Project> supervisorProjectList = new ArrayList<Project>();

        for (int i = 0; i < projectList.size(); i += 1) {
            if (projectList.get(i).getSupervisor().getUserID().equalsIgnoreCase(supervisorID)) {
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

    /**
     * Viewing Available projects for Student
     * 
     * @param student
     */
    public void viewAvailableProjects() {
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        System.out.println(
                "|                           List of All Available Projects                         |");
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        for (Project project : projectList) {
            if (project.getProjectStatus() == ProjectStatus.AVAILABLE && project.getSupervisor().getNumProj() < 2) {
                project.printProjectDetails();
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
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        System.out.println(
                "|                           List of All Available Projects                         |");
        System.out.println(
                "+----------------------------------------------------------------------------------+");
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
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        System.out.println(
                "|                           List of All Allocated Projects                         |");
        System.out.println(
                "+----------------------------------------------------------------------------------+");
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
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        System.out.println(
                "|                           List of All Reserved Projects                          |");
        System.out.println(
                "+----------------------------------------------------------------------------------+");
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
        System.out.println(
                "+----------------------------------------------------------------------------------+");
        System.out.println(
                "|                         List of All Unavailable Projects                         |");
        System.out.println(
                "+----------------------------------------------------------------------------------+");
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

    // Project Report Functionality
    public void printProjectReport(ProjectStatus projectStatus) {
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println("|                          Project Report Details                       |");
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("List of " + projectStatus + " Projects:                                   ");
        int counter = 1;

        for (int i = 0; i < projectList.size(); i += 1) {
            Project currentProject = projectList.get(i);
            if (currentProject.getProjectStatus() == projectStatus) {
                System.out.println("Project " + counter);
                currentProject.printProjectDetails();
                System.out.println();

                counter += 1;
            }
        }

        if (counter == 1) {
            System.out.println("There is currently no projects that are of status " + projectStatus.toString());
        }
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("|                    Enter 0 to go back to Previous Menu                |");
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println(""); // print empty line

    }

    // TODO Project Report Generation
    public void projectStatusFilteredMenu(Scanner scObject) {
        int statusMenuChoice;
        Boolean invalidOption = true;

        do {
            System.out.println(""); // print empty line
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                           Project Report Portal                       |");
            System.out.println("|-----------------------------------------------------------------------|");
            System.out.println("| Please provide the Project Status you would like to filter by?        |");
            System.out.println("| 1. Allocated                                                          |");
            System.out.println("| 2. Available                                                          |");
            System.out.println("| 3. Reserved                                                           |");
            System.out.println("| 4. Unavailable                                                        |");
            System.out.println("|-----------------------------------------------------------------------|");
            System.out.println("|                    Enter 0 to go back to Previous Menu                |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println(""); // print empty line

            System.out.print("Please enter your choice: ");

            // Parse the Int within the String
            statusMenuChoice = scObject.nextInt();
            // Gets rid of \n
            scObject.nextLine();

            switch (statusMenuChoice) {
                case 0:
                    // Return to the Previous Menu
                    invalidOption = false;
                    break;

                case 1:
                    // Allocated Projects
                    printProjectReport(ProjectStatus.ALLOCATED);
                    invalidOption = false;
                    break;

                case 2:
                    // Available Projects
                    printProjectReport(ProjectStatus.AVAILABLE);
                    invalidOption = false;
                    break;

                case 3:
                    // Reserved Projects
                    printProjectReport(ProjectStatus.RESERVED);
                    invalidOption = false;
                    break;

                case 4:
                    // Unavailable Projects
                    printProjectReport(ProjectStatus.UNAVAILABLE);
                    invalidOption = false;
                    break;

                default:
                    System.out.println("Invalid Input. Please choose only from the list of options provided.");
            }
        } while (invalidOption == true);

    }

    public void projectSupervisorFilteredMenu(Scanner scObject) {
        System.out.println(""); // print empty line
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println("|                           Project Report Portal                       |");
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("| Please provide the Supervisor ID you would like to filter by?         |");
        System.out.println("|                                                                       |");
        System.out.println("|                                                                       |");
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("|                    Enter 0 to go back to Previous Menu                |");
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println(""); // print empty line

        String targetSupervisorID = scObject.nextLine();

        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println("|                          Project Report Details                       |");
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println(" List of " + targetSupervisorID + "'s Projects:                            ");

        // Printing the Projects for this.supervisor
        int counter = 1;
        for (int i = 0; i < projectList.size(); i += 1) {
            Project currentProject = projectList.get(i);
            if (currentProject.getSupervisor().getUserID() == targetSupervisorID) {
                System.out.println("Project Number " + counter + ": ");
                currentProject.printProjectDetails();
                System.out.println();

                counter += 1;
            }
        }

        if (counter == 1) {
            System.out.println("This supervisor is not supervising any project(s).");
        }
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("|                    Enter 0 to go back to Previous Menu                |");
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println(""); // print empty line
    }

    public void projectStudentFilteredMenu(Scanner scObject) {

        System.out.println(""); // print empty line
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println("|                           Project Report Portal                       |");
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("| Please provide the Student ID you would like to filter by?            |");
        System.out.println("|                                                                       |");
        System.out.println("|                                                                       |");
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("|                    Enter 0 to go back to Previous Menu                |");
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println(""); // print empty line

        String targetStudentID = scObject.nextLine();

        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println("|                          Project Report Details                       |");
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println(" List of " + targetStudentID + "'s Project:                            ");

        // Printing the Projects for this.supervisor
        int counter = 1;
        for (int i = 0; i < projectList.size(); i += 1) {
            Project currentProject = projectList.get(i);
            if (currentProject.getStudent().getUserID() == targetStudentID) {
                System.out.println("Project Number " + counter + ": ");
                currentProject.printProjectDetails();
                System.out.println();

                counter += 1;
            }
        }

        if (counter == 1) {
            // Student does not have allocated project currently
            System.out.println("Student " + targetStudentID + " does not currently have an allocated project.");
        }
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("|                    Enter 0 to go back to Previous Menu                |");
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println(""); // print empty line
    }
}
