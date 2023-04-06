package Entities;

import java.util.ArrayList;
import java.util.Scanner;

import Controller.*;

public class Supervisor extends User{

    public Supervisor(String userID, String name, String userEmail){
        super(userID, "password");
        this.userID = userID;
        this.name = name;
        this.userEmail = userEmail;
    }

    public String getSupervisorID() {
        return this.userID;
    }

    public String getSupervisorPassword() {
        return this.password;
    }

    public String getSupervisorName() {
        return this.name;
    }

    /**
     * Displays all the options of the system. Abstract so can be overridden
     */
    @Override
    public void printMenuOptions() {
        System.out.println("************ Welcome to FYPMS " + this.name + " *************");
        System.out.println("************ Supervisor Options: *************");
        System.out.println(" 1. Create a Project.");
        System.out.println(" 2. View own Project(s).");
        System.out.println(" 3. Modify the title of your Project(s).");
        System.out.println(" 4. View pending requests.)");
        System.out.println(" 5. View request history & status.");
        System.out.println(" 6. Request the transfer of a student.");
        System.out.println(" 7. Log out.");
        System.out.println();
    }

    /**
     * Method to allow User to create a FYP Project
     */
    public void createProject(ProjectDB projectDB, Scanner scObject){
        String projectTitle;

        System.out.println("#############     Project Creation     #############");
        System.out.println("Input the Project Title: ");
        scObject.nextLine();
        projectTitle = scObject.nextLine();

        // Increment the Project ID
        projectDB.updateProjectCount();

        // Create new Project
        Project newlyCreatedProject = new Project(projectDB.getProjectCount(), this.name, this.userEmail, projectTitle);
        projectDB.addProject(newlyCreatedProject);

        System.out.println("Project Initialised.");
    };
    
    public void viewOwnProject(ArrayList<Project> supervisorProjectList){
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

    /**
     * Function to change Project Title. Called after User elects to change Title
     */
    public void modifyTitle(ProjectDB projectDB, Scanner scObject){
        // Retrieve the list of projects owned by this particular supervisor
        ArrayList<Project> supervisorProjectList = projectDB.retrieveProfessorProjects(this.name); 
        // Scanner scObject = new Scanner(System.in);
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
            System.out.println("Project \"" + supervisorProjectList.get(projectIndex).projectTitle + "\" will be changed to \"" + newTitle + "\"");
        
            System.out.println("Press 1 to Confirm and 2 to Quit.");
            userInput = scObject.nextInt();

            // Change the Title
            if (userInput == 1) {
                projectDB.changeProjectTitle(targetProjectID, newTitle);
                break;
            }
            else if (userInput == 2) {
                break;
            }
            else {
                System.out.println("Invalid Input. Please key in 1 or 2.");
            }
        } while (true);

    };

    public Boolean validateProjectID(ArrayList<Project> supervisorProjectList, int unknownProjectId) {
        for (int i = 0; i < supervisorProjectList.size(); i += 1){
            if (supervisorProjectList.get(i).projectID == unknownProjectId) {
                return true;
            }
        }
        return false;
    }

    public void viewPendingRequests(){};
    public void viewCreatedProjects(){};
    public void requestTransfer(){};
}
