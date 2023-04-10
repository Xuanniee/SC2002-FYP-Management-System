package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Entities.*;

public class FacultyDB {

    private ArrayList<Supervisor> supervisors = new ArrayList<Supervisor>();

    // When this class object is created, automatically read from text file and
    // store into arraylist of supervisor objects
    public FacultyDB() {
        String fileName = "./Data/faculty_list.txt";
        String line;
        boolean isFirstLine = true;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // skip the first line
                }
                String[] values = line.split("\t"); // split the line by tabs
                // System.out.println(values[0]);
                // System.out.println(values[1]);
                int index = values[1].indexOf('@');
                // System.out.println(index);
                supervisors.add(new Supervisor(values[1].substring(0, index), values[0], values[1]));
            }
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

    public void createProject(ProjectDB project_list, Supervisor loggedSupervisor){
        String projectTitle;
        Scanner scObject = new Scanner(System.in);
        System.out.println("#############     Project Creation     #############");
        System.out.println("Input the Project Title: ");
        scObject.nextLine();
        projectTitle = scObject.nextLine();

        // Increment the Project ID
        project_list.updateProjectCount();

        // Create new Project
        Project newlyCreatedProject = new Project(project_list.getProjectCount(), loggedSupervisor, projectTitle);
        project_list.addProject(newlyCreatedProject);

        System.out.println("Project Initialised.");
    }

    public void viewOwnProject(ArrayList<Project> supervisorProjectList) {
        if (supervisorProjectList.size() == 0) {
            System.out.println("You have not yet created any projects.");
        }
        int counter = 1;
        System.out.println("############# List of Created Projects #############");
        for (int i = 0; i < supervisorProjectList.size(); i += 1) {
            int projectId = supervisorProjectList.get(i).projectID;
            String projectName = supervisorProjectList.get(i).projectTitle;
            System.out.println(counter + " | Project ID: " + projectId + " | Project Title: " + projectName);
            counter += 1;
        }
        System.out.println("----------------------------------------------------");
    };

    public void modifyTitle(ProjectDB projectDB) {
        // Retrieve the list of projects owned by this particular supervisor
        ArrayList<Project> supervisorProjectList = projectDB.retrieveProfessorProjects(this.name);
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
            System.out.println("Project \"" + supervisorProjectList.get(projectIndex).projectTitle
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
