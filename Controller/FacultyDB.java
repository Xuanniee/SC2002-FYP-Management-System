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

public class FacultyDB extends Database {

    private ArrayList<Supervisor> supervisors = new ArrayList<Supervisor>();

    private String filePath = String.join("", super.directory, "faculty_list.txt");

    private File file;

    public FacultyDB() {
        this.file = new File(filePath);
        this.supervisors = new ArrayList<Supervisor>();
        this.readFile();
    }

    public FacultyDB(String filePath) {
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
            String supervisorName, supervisorEmail, supervisorID;
            String[] facultyData, temp;

            while (facultyLine != null) {
                facultyData = facultyLine.split(super.delimiter);
                supervisorName = facultyData[0];
                supervisorEmail = facultyData[1];

                temp = facultyData[1].split(super.emailDelimiter);
                supervisorID = temp[0];

                supervisors.add(new Supervisor(supervisorID, supervisorName, supervisorEmail));

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
            for (Supervisor currentSupervisor : supervisors) {
                String supervisorName = currentSupervisor.getUserName();
                String supervisorEmail = currentSupervisor.getUserEmail();

                pw.println(supervisorName + delimiter + supervisorEmail);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* For testing purposes */
    public void viewDB() {
        for (int i = 0; i < supervisors.size(); i++) {
            supervisors.get(i).viewDetails();
        }
    }

    /** 
     * Function for Supervisors to create new project
     */
    public void createProject(ProjectDB project_list, Supervisor loggedSupervisor) {
        String projectTitle;
        Scanner scObject = new Scanner(System.in);
        System.out.println(" ------------------ Project Creation ------------------ ");
        System.out.print("Input the Project Title: ");
        
        projectTitle = scObject.nextLine();

        // Increment the Project ID
        project_list.updateProjectCount();

        // Create new Project
        Project newlyCreatedProject = new Project(project_list.getProjectCount(), loggedSupervisor, projectTitle);
        project_list.addProject(newlyCreatedProject);

        System.out.println(" ----------------- Project Initialised ----------------- ");
        newlyCreatedProject.printProjectDetails();
        scObject.close();
    }

    /**
     * Function for Supervisors to view their own projects
     */
    public void viewOwnProject(ArrayList<Project> createdProjectList) {
        if (createdProjectList.size() == 0) {
            System.out.println("You have not yet created any projects.");
        }
        int counter = 1;
        System.out.println(" ----------------- List of Created Projects ----------------- ");
        for (int i = 0; i < createdProjectList.size(); i += 1) {
            int projectId = createdProjectList.get(i).getProjectID();
            String projectName = createdProjectList.get(i).getProjectTitle();
            System.out.println(counter + " | Project ID: " + projectId + " | Project Title: " + projectName);
            counter += 1;
        }
        System.out.println("----------------------------------------------------");
    };

    public void modifyTitle(ProjectDB projectDB, Supervisor managedSupervisor) {
        // Retrieve the list of projects created by this particular supervisor
        ArrayList<Project> supervisorProjectList = projectDB
                .retrieveSupervisorProjects(managedSupervisor.getUserName());
        // Print out list of projects created by this particular supervisor
        viewOwnProject(supervisorProjectList);

        Scanner scObject = new Scanner(System.in);
        int targetProjectID;
        int userInput;
        int projectIndex;

        do {
            viewOwnProject(supervisorProjectList);
            System.out.println("Provide the Project ID:");
            targetProjectID = scObject.nextInt();

            // Validate Project ID
            if (!validateProjectID(supervisorProjectList, targetProjectID)) {
                // Project ID does not exist
                System.out.println("Invalid!! Please provide a valid Project ID.");
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

        scObject.close();
    }

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
     * @param userID
     * @return
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
     * Get Supervisor List
     */
    public ArrayList<Supervisor> getSupervisorList() {
        return supervisors;
    }
}
