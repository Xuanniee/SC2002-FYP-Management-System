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

import Entities.*;
import enums.ProjectStatus;

/**
 * Represents a Database of faculties in FYP Management System.
 * 
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class FacultyDB extends Database {
    /**
     * Represents the file path of the Database of Faculty Users in FYPMS.
     */
    private String filePath = String.join("", super.directory, "faculty_list.txt");

    /**
     * Represents the database file of all the Faculty Users in FYPMS.
     */
    private File file;

    /**
     * Array List of Faculty Users in FYPMS.
     */
    private ArrayList<Supervisor> supervisors = new ArrayList<Supervisor>();

    /**
     * Constructor
     * Creates a Database storing all the Faculties in FYPMS when the Application is
     * first initialised.
     */
    public FacultyDB() {
        this.file = new File(filePath);
        this.supervisors = new ArrayList<Supervisor>();
        this.readFile();
    }

    /**
     * Reads supervisor data from faculty_list.txt
     */
    public void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String facultyLine = br.readLine();
            facultyLine = br.readLine();
            String supervisorName, supervisorEmail, supervisorID, supervisorPassword;
            String[] facultyData, temp;

            while (facultyLine != null) {
                facultyData = facultyLine.split(super.delimiter);
                supervisorName = facultyData[0];
                supervisorEmail = facultyData[1];
                supervisorPassword = facultyData[2];

                temp = facultyData[1].split(super.emailDelimiter);
                supervisorID = temp[0];

                supervisors.add(new Supervisor(supervisorID, supervisorName, supervisorEmail, supervisorPassword));

                facultyLine = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes updated supervisor data to supervisor_list.txt
     */
    public void updateFile() {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(file, false));
            PrintWriter pw = new PrintWriter(bf);

            // Write Headers
            pw.println("Name" + "\t" + "Email" + "\t" + "Password");
            for (Supervisor currentSupervisor : supervisors) {
                String supervisorName = currentSupervisor.getUserName();
                String supervisorEmail = currentSupervisor.getUserEmail();
                String supervisorPassword = currentSupervisor.getPassword();

                pw.println(supervisorName + delimiter + supervisorEmail + delimiter + supervisorPassword);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function for Supervisors to create new project.
     * 
     * @param project_list     Array list of all projects in FYPMS.
     * @param loggedSupervisor Supervisor currently using FYPMS.
     * @param scObject         Scanner used to process Supervisor's inputs.
     */
    public void createProject(ProjectDB project_list, Supervisor loggedSupervisor, Scanner scObject) {
        String projectTitle;

        System.out.println(
                "+----------------------------------------------------------------------------------------+");
        System.out.println(
                "|                                     Project Creation                                   |");
        System.out.println(
                "+----------------------------------------------------------------------------------------+");
        System.out.print("  Input the Project Title [Enter 'QUIT' to return to previous menu]: ");

        projectTitle = scObject.nextLine();

        // If user does not want to create project anymore
        if (projectTitle.equalsIgnoreCase("QUIT")) {
            System.out.println("");
            System.out.println("Returning to the previous menu...");
            return;
        }
        // Create new Project
        Project newlyCreatedProject = new Project(project_list.getProjectCount(), loggedSupervisor, projectTitle);
        
        // Increment the Project ID
        project_list.updateProjectCount();

        // Add newly created project to Supervisor's list of projects
        //loggedSupervisor.addSupervisingProject(newlyCreatedProject);
        if (loggedSupervisor.getNumProj() >= 2) {
            newlyCreatedProject.setProjectStatus(ProjectStatus.UNAVAILABLE);
        }

        // Add newly created project to database of projects
        project_list.addProject(newlyCreatedProject);
        
        System.out.println(
                "+----------------------------------- Project Initialised --------------------------------+");
        newlyCreatedProject.printProjectDetails();
        System.out.println(
                "+------------------------------------------ END ------------------------------------------+");
    }

    /**
     * Function for Supervisors to view their own projects.
     * 
     * @param createdProjectList List of projects created by Supervisor.
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean viewOwnProject(ArrayList<Project> createdProjectList) {
        if (createdProjectList.size() == 0) {
            // No Projects
            System.out.println("You have not yet created any projects.");
            return false;
        }

        int counter = 1;
        System.out.println(
                "+----------------------------------------------------------------------------------------+");
        System.out.println(
                "|                                List of Created Projects                                |");
        System.out.println(
                "+----------------------------------------------------------------------------------------+");
        for (int i = 0; i < createdProjectList.size(); i += 1) {
            int projectId = createdProjectList.get(i).getProjectID();
            String projectName = createdProjectList.get(i).getProjectTitle();
            String projectStatus = createdProjectList.get(i).getProjectStatus().toString();
            System.out.println(counter + " | Project ID: " + projectId + " | Project Title: " + projectName
                    + " | Project Status: " + projectStatus);
            counter += 1;
        }
        System.out.println(
                "+------------------------------------------ END ------------------------------------------+");
        return true;
    };

    /**
     * Allow Supervisor to modify title of their own projects.
     * 
     * @param projectDB         Database of projects in FYPMS.
     * @param managedSupervisor Supervisor currently using FYPMS.
     * @param scObject          Scanner used to process Supervisor's inputs.
     */
    public void modifyTitle(ProjectDB projectDB, Supervisor managedSupervisor, Scanner scObject) {
        // Retrieve the list of projects created by this particular supervisor
        ArrayList<Project> supervisorProjectList = projectDB.retrieveSupervisorProjects(managedSupervisor.getUserID());

        int targetProjectID;
        int userInput;
        int projectIndex;

        do {
            // Print out list of projects created by this particular supervisor
            Boolean createdProject = viewOwnProject(supervisorProjectList);

            if (createdProject == false) {
                // No Projects Created Yet
                System.out.println("Enter the word 'QUIT' to return to the previous menu.");
                String quitInput = scObject.nextLine();
                if (quitInput.equalsIgnoreCase("Quit")) {
                    System.out.println("Returning to the previous menu...");
                    return;
                }
                else {
                    // Error Catching if Quit is not provided
                    System.out.println("Invalid input provided. Please try again.");
                    continue;
                }
            }

            System.out.println("Provide the Project ID:");
            targetProjectID = scObject.nextInt();

            // Validate Project ID
            if (!validateProjectID(supervisorProjectList, targetProjectID)) {
                // Project ID does not exist
                System.out.println("Invalid! Please provide a valid Project ID.");
                userInput = 3;
                continue;
            }
            System.out.println("New Title: ");
            scObject.nextLine();
            String newTitle = scObject.nextLine();

            // Verify with User for Project Deletion
            projectIndex = projectDB.getProjectIndexInSupervisorProjectList(targetProjectID, supervisorProjectList);
            System.out.println("Project \"" + supervisorProjectList.get(projectIndex).getProjectTitle()
                    + "\" will be changed to \"" + newTitle + "\"");

            System.out.println("Press 1 to Confirm and 2 to Quit.");
            userInput = scObject.nextInt();

            // Change the Title
            if (userInput == 1) {
                projectDB.changeProjectTitle(targetProjectID, newTitle);
                break;
            } else if (userInput == 2) {
                break;
            } else {
                System.out.println("Invalid Input. Please key in 1 or 2.");
            }
        } while (true);
    }

    /**
     * Checks whether project exists using projectID.
     * 
     * @param supervisorProjectList List of projects supervised by Supervisor.
     * @param unknownProjectId      Project ID to be verified.
     * @return a Boolean to indicate whether the project exists.
     */
    public Boolean validateProjectID(ArrayList<Project> supervisorProjectList, int unknownProjectId) {
        for (int i = 0; i < supervisorProjectList.size(); i += 1) {
            if (supervisorProjectList.get(i).getProjectID() == unknownProjectId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the Target Supervisor so long as they are present
     * 
     * @param userID of Target Supervisor.
     * @return Target Supervisor.
     */
    public Supervisor findSupervisor(String userID) {
        int targetIndex = -1;
        for (int i = 0; i < supervisors.size(); i += 1) {
            if (supervisors.get(i).getUserID().equalsIgnoreCase(userID)) {
                targetIndex = i;
            }
        }
        if (targetIndex == -1) {
            return null;
        }
        return supervisors.get(targetIndex);
    }

    /**
     * Retrieves Supervisor List.
     * 
     * @return Array list of Supervisors.
     */
    public ArrayList<Supervisor> getSupervisorList() {
        return supervisors;
    }
}
