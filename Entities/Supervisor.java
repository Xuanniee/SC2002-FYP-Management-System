package Entities;

import java.util.ArrayList;
import java.util.Scanner;

import Boundary.Menu;

/**
 * Represents a Supervisor user in the FYP Management System.
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class Supervisor extends User implements Menu{

    /**
     * List of all projects Supervised by this Supervisor.
     */
    private ArrayList<Project> supervisingProjectList = new ArrayList<Project>();
    
    /**
     * Number of projects being supervised by this Supervisor.
     */
    private int numProjects = 0;

    /**
     * Constructor
     * Creates a new Supervisor with the given User ID, Name, and Email address.
     * 
     * @param userID Login ID of user
     * @param userName User's legal name
     * @param userEmail User's email address
     */
    public Supervisor(String userID, String userName, String userEmail) {
        super(userID, userName, userEmail);
    }

    /**
     * Get list of Projects that Supervisor is supervising.
     * @return this Supervisor's projects 
     */
    public ArrayList<Project> getSupervisingProjectList() {
        return supervisingProjectList;
    }

    /**
     * Add newly created project to Supervisor's list of projects
     * and increase number of Supervising projects by 1.
     */
    public void addSupervisingProject(Project project){
        supervisingProjectList.add(project);
        numProjects += 1;
    }

    /**
     * Prints the details of Projects that Supervisor is supervising.
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean viewSupervisingProjectList() {
        int counter = 1;
        for (int i = 0; i < supervisingProjectList.size(); i += 1) {
            Project currentProject = supervisingProjectList.get(i);
            System.out.println(counter + ". " + "| Project ID: " + currentProject.getProjectID() + " | Project Title: "
                    + currentProject.getProjectTitle());
            counter += 1;
        }

        if (counter == 1) {
            System.out.println("You are not currently supervising any project.");
            return false;
        }

        return true;
    }

    /**
     * Get a particular project from Supervisor's projects using projectID.
     * @param projectID projectID of target project.
     * @return target project.
     */
    public Project getParticularSupervisingProject(int projectID) {
        Project targetProject;
        for (int i = 0; i < supervisingProjectList.size(); i += 1) {
            if (supervisingProjectList.get(i).getProjectID() == projectID) {
                targetProject = supervisingProjectList.get(i);
                return targetProject;
            }
        }
        return null;

    }

    /**
     * Removes the Project from the Replaced Supervisor's Project List
     * 
     * @param oldProject Project to be removed from Supervisor's project list.
     * @return
     */
    public Boolean removeSupervisingProjectList(Project oldProject) {
        if (oldProject == null) {
            return false;
        }

        // Get index of Object to be removed
        int indexRemovedProject = supervisingProjectList.indexOf(oldProject);
        // Remove the Project and Update the list
        supervisingProjectList.remove(indexRemovedProject);
        numProjects -= 1;

        return true;
    }

    /**
     * Updates the Replacement Supervisor with the Project.
     * 
     * @param oldProject
     * @param replacementProject
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean setSupervisingProjectList(Project replacementProject) {
        if (replacementProject == null) {
            return false;
        } 

        supervisingProjectList.add(replacementProject);
        
        numProjects += 1;

        return true;
    }

    /** 
     * Get number of projects Supervisor is currently supervising.
     * @return integer that represents the number of projects Supervisor is supervising.
     */
    public int getNumProj(){
        return this.numProjects;
    }

    public void editNumProjects(int change) {
        this.numProjects += change;
    }

    /**
     * Implements the User Menu to print the Supervisor Menu
     * @return integer that represents User's choice.
     */
    public int printMenuOptions(Scanner scObject) {
        // Supervisor Menu
        int choice;

        System.out.println(""); // print empty line
        System.out.println("+-------------------------------------------------------+");
        System.out.println("|                   Faculty Portal                      |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("| 1. Create a Project                                   |");
        System.out.println("| 2. View own Project(s)                                |");
        System.out.println("| 3. Modify the title of your Project(s)                |");
        System.out.println("| 4. View Pending Title Change Requests                 |");
        System.out.println("| 5. View Request History & Status                      |");
        System.out.println("| 6. Request the Transfer of a Student                  |");
        System.out.println("| 7. Set New Password                                   |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("|              Enter 0 to Log out of FYPMS              |");
        System.out.println("+-------------------------------------------------------+");
        System.out.println(""); // print empty line

        System.out.print("Please enter your choice: ");

        choice = scObject.nextInt();
        // Remove \n from Buffer
        scObject.nextLine();

        return choice;

    }

    /*public Boolean checkIfFull() {
        return this.isFull;
    }*/

    /**
     * Displays all the options of the system. Abstract so can be overridden
     */
    /*
     * public void printMenuOptions() {
     * System.out.println("************ Welcome to FYPMS " + this.name +
     * " *************");
     * System.out.println("************ Supervisor Options: *************");
     * System.out.println(" 1. Create a Project.");
     * System.out.println(" 2. View own Project(s).");
     * System.out.println(" 3. Modify the title of your Project(s).");
     * System.out.println(" 4. View pending requests.)");
     * System.out.println(" 5. View request history & status.");
     * System.out.println(" 6. Request the transfer of a student.");
     * System.out.println(" 7. Log out.");
     * System.out.println();
     * }
     */

    /**
     * Method to allow User to create a FYP Project
     */
    /*
     * public void createProject(ProjectDB projectDB, Scanner scObject) {
     * String projectTitle;
     * 
     * System.out.println("#############     Project Creation     #############");
     * System.out.println("Input the Project Title: ");
     * scObject.nextLine();
     * projectTitle = scObject.nextLine();
     * 
     * // Increment the Project ID
     * projectDB.updateProjectCount();
     * 
     * // Create new Project
     * Project newlyCreatedProject = new Project(projectDB.getProjectCount(), this,
     * projectTitle);
     * projectDB.addProject(newlyCreatedProject);
     * 
     * System.out.println("Project Initialised.");
     * };
     */

    /*
     * public void viewOwnProject(ArrayList<Project> supervisorProjectList) {
     * if (supervisorProjectList.size() == 0) {
     * System.out.println("You have not yet created any projects.");
     * }
     * int counter = 1;
     * System.out.println("############# List of Created Projects #############");
     * for (int i = 0; i < supervisorProjectList.size(); i += 1) {
     * int projectId = supervisorProjectList.get(i).projectID;
     * String projectName = supervisorProjectList.get(i).projectTitle;
     * System.out.println(counter + " | Project ID: " + projectId +
     * " | Project Title: " + projectName);
     * counter += 1;
     * }
     * System.out.println("----------------------------------------------------");
     * };
     */

    /**
     * Function to change Project Title. Called after User elects to change Title
     */
    /*
     * public void modifyTitle(ProjectDB projectDB, Scanner scObject) {
     * // Retrieve the list of projects owned by this particular supervisor
     * ArrayList<Project> supervisorProjectList =
     * projectDB.retrieveProfessorProjects(this.userName);
     * // Scanner scObject = new Scanner(System.in);
     * int targetProjectID;
     * int userInput;
     * int projectIndex;
     * 
     * do {
     * viewOwnProject(supervisorProjectList);
     * System.out.println("Provide the Project ID:");
     * targetProjectID = scObject.nextInt();
     * 
     * // Validate Project ID
     * if (!validateProjectID(supervisorProjectList, targetProjectID)) {
     * // Project ID does not exist
     * System.out.println("Invalid!! Please provide a valid Project ID.");
     * userInput = 3;
     * continue;
     * }
     * System.out.println("New Title: ");
     * scObject.nextLine();
     * String newTitle = scObject.nextLine();
     * 
     * // Verify with User for Project Deletion
     * projectIndex =
     * projectDB.getProjectIndexInSupervisorProjectList(targetProjectID,
     * supervisorProjectList);
     * System.out.println("Project \"" +
     * supervisorProjectList.get(projectIndex).projectTitle
     * + "\" will be changed to \"" + newTitle + "\"");
     * 
     * System.out.println("Press 1 to Confirm and 2 to Quit.");
     * userInput = scObject.nextInt();
     * 
     * // Change the Title
     * if (userInput == 1) {
     * projectDB.changeProjectTitle(targetProjectID, newTitle);
     * break;
     * } else if (userInput == 2) {
     * break;
     * } else {
     * System.out.println("Invalid Input. Please key in 1 or 2.");
     * }
     * } while (true);
     * 
     * };
     */

    /*
     * public Boolean validateProjectID(ArrayList<Project> supervisorProjectList,
     * int unknownProjectId) {
     * for (int i = 0; i < supervisorProjectList.size(); i += 1) {
     * if (supervisorProjectList.get(i).projectID == unknownProjectId) {
     * return true;
     * }
     * }
     * return false;
     * }
     */

}
