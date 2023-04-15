package Entities;

import java.util.ArrayList;

/**
 * Represents a Supervisor user in the FYP Management System.
 * 
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class Supervisor extends User {

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
     * @param userID    Login ID of user
     * @param userName  User's legal name
     * @param userEmail User's email address
     */
    public Supervisor(String userID, String userName, String userEmail, String userPassword) {
        super(userID, userName, userEmail);
        this.password = userPassword;
    }

    /**
     * Get list of Projects that Supervisor is supervising.
     * 
     * @return this Supervisor's projects
     */
    public ArrayList<Project> getSupervisingProjectList() {
        return supervisingProjectList;
    }

    /**
     * Add newly created project to Supervisor's list of projects
     * and increase number of Supervising projects by 1.
     */
    public void addSupervisingProject(Project project) {
        supervisingProjectList.add(project);
        numProjects += 1;
    }

    /**
     * Prints the details of Projects that Supervisor is supervising.
     * 
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean viewSupervisingProjectList() {
        int counter = 1;
        for (int i = 0; i < supervisingProjectList.size(); i += 1) {
            Project currentProject = supervisingProjectList.get(i);
            System.out.println(counter + ". " + "| Project ID: " + currentProject.getProjectID() + " | Project Title: "
                    + currentProject.getProjectTitle() + "| Project Status: " + currentProject.getProjectStatus());
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
     * 
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

        // Remove the Project and Update the list
        supervisingProjectList.remove(oldProject);
        editNumProjects(-1);

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

        return true;
    }

    /**
     * Get number of projects Supervisor is currently supervising.
     * 
     * @return integer that represents the number of projects Supervisor is
     *         supervising.
     */
    public int getNumProj() {
        return this.numProjects;
    }

    public void editNumProjects(int change) {
        this.numProjects += change;
    }

}
