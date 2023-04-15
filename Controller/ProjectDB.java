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

/**
 * Represents a Database of Projects in the FYP Management System.
 * 
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class ProjectDB extends Database {

    /**
     * Represents the file path of the Database of Projects in FYPMS.
     */
    private String filePath = String.join("", super.directory, "rollover project.txt");

    /**
     * Represents the file containing the database of all Projects.
     */
    private File file;

    /**
     * ArrayList containing all the Projects in FYPMS
     */
    public ArrayList<Project> projectList;

    /**
     * Integer attribute storing the number of projects.
     */
    private int projectCount = 0;

    /**
     * Represents the Database contianing all Supervisor Users in FYPMS
     */
    private FacultyDB facultyDB;

    /**
     * Represents the Database contianing all Student Users in FYPMS
     */
    private StudentDB studentDB;

    /**
     * Constructor
     * Creates a ProjectDB object that stores all the Projects and their relevant
     * details in FYPMS.
     * 
     * @param facultyDB Reference to Faculty Database
     * @param studentDB Reference to Student Database
     */
    public ProjectDB(FacultyDB facultyDB, StudentDB studentDB) {
        this.facultyDB = facultyDB;
<<<<<<< Updated upstream
=======
        this.studentDB = studentDB;
>>>>>>> Stashed changes
        this.file = new File(filePath);
        this.projectList = new ArrayList<Project>();
        this.readFile();
    }

    // public ProjectDB(String filePath, FacultyDB facultyDB, StudentDB studentDB) {
    // this.facultyDB = facultyDB;
    // this.studentDB = studentDB;
    // this.file = new File(filePath);
    // this.projectList = new ArrayList<Project>();
    // this.readFile();
    // }

    /**
     * Reads the Projects from provided file.
     */
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

    /**
     * Writes the updated Projects data back into the text file.
     */
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

    /**
     * Retrieves the Number of Projects.
     * 
     * @return Project Count
     */
    public int getProjectCount() {
        return projectCount;
    }

    /**
     * Updates the Number of Projects by incrementing by 1.
     */
    public void updateProjectCount() {
        this.projectCount += 1;
    }

    /**
     * Appending a newly created project to the database arraylist.
     * 
     * @param newlyCreatedProject New Project that is created
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean addProject(Project newlyCreatedProject) {
        // Don't need increment projectCount since should incremented before calling
        // this function
        if (newlyCreatedProject == null) {
            return false;
        }
        projectList.add(newlyCreatedProject);

        return true;
    }

    /**
     * Finds a Project using their Project ID.
     * 
     * @param projectID
     * @return
     */
    public Project findProject(int projectID) {
        Project targetProject = null;
        for (int i = 0; i < projectList.size(); i += 1) {
            Project currentProject = projectList.get(i);
            if (currentProject.getProjectID() == projectID) {
                targetProject = currentProject;
            }
        }
        return targetProject;
    }

    /**
     * Updates the Project Title after being approved.
     * 
     * @param projectID       Unique Project ID
     * @param newProjectTitle New Project Title
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean changeProjectTitle(int projectID, String newProjectTitle) {
        // Input Validation
        if (projectID > projectCount) {
            return false;
        }

        // Retrieve the Project whose title is to be changed
        Project targetProject = findProject(projectID);
        targetProject.setProjectTitle(newProjectTitle);

        return true;
    };

    /**
     * Return list of projects owned by particular Supervisor
     * 
     * @param supervisorID Target Supervisor's ID
     * @return ArrayList of Supervisor's Projects
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

    /**
     * Retrieves the Index of the Target Project within the ArrayList
     * 
     * @param projectID             Unique Project ID
     * @param supervisorProjectList List of Projects supervised by Specific Faculty
     *                              Member
     * @return the index of target project
     */
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
     * Retrieves all the Projects in the FYPMS
     * 
     * @return ArrayList of All Projects
     */
    public ArrayList<Project> getAllProjects() {
        return projectList;
    }

    /**
     * Viewing Available projects for Student
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

    /**
     * Views all the Projects in FYPMS regardless of their type.
     */
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
<<<<<<< Updated upstream
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("List of " + projectStatus + " Projects:                                   ");
=======
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println("  List of " + projectStatus + " Projects:                                 ");
>>>>>>> Stashed changes
        int counter = 1;

        for (int i = 0; i < projectList.size(); i += 1) {
            Project currentProject = projectList.get(i);
            if (currentProject.getProjectStatus() == projectStatus) {
<<<<<<< Updated upstream
                System.out.println("Project " + counter);
                currentProject.printProjectDetails();
                System.out.println();

=======
                // System.out.println("Project " + counter);
                currentProject.printProjectDetails();
>>>>>>> Stashed changes
                counter += 1;
            }
        }

        if (counter == 1) {
<<<<<<< Updated upstream
            System.out.println("There is currently no projects that are of status " + projectStatus.toString());
        }
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("|                    Enter 0 to go back to Previous Menu                |");
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println(""); // print empty line
=======
            System.out.println("  There is currently no projects that are of status " + projectStatus.toString());
        }
>>>>>>> Stashed changes

    }

    /**
     * Prints the Project Report Menu for User to filter.
     * 
     * @param scObject Scanner to read User Input
     */
    public void projectStatusFilteredMenu(Scanner scObject) {
        int statusMenuChoice;
<<<<<<< Updated upstream
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
=======
        int choice;
        Boolean invalidOption = true;
        do {
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

            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|              Enter 0 to return to Main Menu.                          |");
            System.out.println("|              Enter 1 to choose another Project Status.                |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.print("Your choice is: ");
            choice = scObject.nextInt();

        } while (choice == 1);
>>>>>>> Stashed changes

    }

    /**
     * Prints the Menu to filter by Supervisor ID
     * 
     * @param scObject Scanner
     */
    public void projectSupervisorFilteredMenu(Scanner scObject) {
<<<<<<< Updated upstream
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
=======
        int choice;

        do {
            System.out.println(""); // print empty line
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                           Project Report Portal                       |");
            System.out.println("|-----------------------------------------------------------------------|");
            System.out.println("| Please provide the Supervisor ID you would like to filter by?         |");
            System.out.println("|                                                                       |");
            System.out.println("|-----------------------------------------------------------------------|");
            System.out.println("|                    Enter 0 to go back to Previous Menu                |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println(""); // print empty line

            scObject.nextLine();
            String targetSupervisorID;
            do {
                System.out.print("Your choice of Supervisor is: ");
                targetSupervisorID = scObject.nextLine();

                // Check if the desired supervisor exists
                Supervisor targetSupervisor = facultyDB.findSupervisor(targetSupervisorID);
                if (targetSupervisor == null) {
                    System.out.println(
                            "   There is no such supervisor. Please check whether you have entered the UserID correctly.");
                    System.out.println("");
                } else {
                    break;
                }
            } while (true);
>>>>>>> Stashed changes

            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                          Project Report Details                       |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("    List of " + targetSupervisorID + "'s Projects:                         ");

            // Printing the Projects for this.supervisor
            int counter = 1;
            for (int i = 0; i < projectList.size(); i += 1) {
                Project currentProject = projectList.get(i);
                if (currentProject.getSupervisor().getUserID().equalsIgnoreCase(targetSupervisorID)) {
                    System.out.println("Project Number " + counter + ": ");
                    currentProject.printProjectDetails();
                    System.out.println();

                    counter += 1;
                }
            }
<<<<<<< Updated upstream
        }

        if (counter == 1) {
            System.out.println("This supervisor is not supervising any project(s).");
        }
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("|                    Enter 0 to go back to Previous Menu                |");
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println(""); // print empty line
=======

            if (counter == 1) {
                System.out.println("    This supervisor is not supervising any project(s).");
            }
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|              Enter 0 to return to Main Menu.                          |");
            System.out.println("|              Enter 1 to view another Supervisor's Projects.           |");
            System.out.println("+-----------------------------------------------------------------------+");

            System.out.print("Your choice is: ");
            choice = scObject.nextInt();
        } while (choice == 1);
>>>>>>> Stashed changes
    }

    /**
     * Prints the Menu to filter by Student ID
     * 
     * @param scObject Scanner
     */
    public void projectStudentFilteredMenu(Scanner scObject) {
<<<<<<< Updated upstream

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
=======
        int choice;

        do {
            System.out.println(""); // print empty line
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                           Project Report Portal                       |");
            System.out.println("|-----------------------------------------------------------------------|");
            System.out.println("| Please provide the Student ID you would like to filter by?            |");
            System.out.println("|                                                                       |");
            System.out.println("|-----------------------------------------------------------------------|");
            System.out.println("|                    Enter 0 to go back to Previous Menu                |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println(""); // print empty line

            scObject.nextLine();

            String targetStudentID;
            do {
                System.out.print("Your choice of Student is: ");
                targetStudentID = scObject.nextLine();

                // Check if the desired supervisor exists
                Student targetStudent = studentDB.findStudent(targetStudentID);
                if (targetStudent == null) {
                    System.out.println(
                            "   There is no such student. Please check whether you have entered the UserID correctly.");
                    System.out.println("");
                } else {
                    break;
                }
            } while (true);
>>>>>>> Stashed changes

            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                          Project Report Details                       |");
            System.out.println("|-----------------------------------------------------------------------|");
            System.out.println("  List of " + targetStudentID + "'s Project:                            ");

            // Printing the Projects for this.supervisor
            int counter = 1;
            for (int i = 0; i < projectList.size(); i += 1) {
                Project currentProject = projectList.get(i);
                if (currentProject.getStudent() != null) {
                    if (currentProject.getStudent().getUserID().equalsIgnoreCase(targetStudentID)) {
                        currentProject.printProjectDetails();
                        System.out.println();
                        counter += 1;
                        break;
                    }
                }
            }
<<<<<<< Updated upstream
        }

        if (counter == 1) {
            // Student does not have allocated project currently
            System.out.println("Student " + targetStudentID + " does not currently have an allocated project.");
        }
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("|                    Enter 0 to go back to Previous Menu                |");
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println(""); // print empty line
=======
            if (counter == 1) {
                // Student does not have allocated project currently
                System.out.println("  Student " + targetStudentID + " does not have an allocated project.");
            }
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|              Enter 0 to return to Main Menu.                          |");
            System.out.println("|              Enter 1 to view another Student's Projects.              |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println(""); // print empty line

            System.out.print("Your choice is: ");
            choice = scObject.nextInt();
        } while (choice == 1);
>>>>>>> Stashed changes
    }

    /**
     * Sets status of remaining projects of Supervisor to either AVAILABLE or
     * UNAVILABLE.
     * Used when student deregisters from a project or when project is transferred
     * to another supervisor.
     * 
     * @param currentSupervisor
     * @param newStatus
     */
    public void setSupervisorProjectsToNewStatus(Supervisor currentSupervisor, ProjectStatus newStatus) {
        Project currentProject;
        for (int i = 0; i < projectList.size(); i++) {
            currentProject = projectList.get(i);

            // Find supervisor's UNAVAILABLE and AVAILABLE projects in database of projects
            if (currentProject.getSupervisor().getUserID().equalsIgnoreCase(currentSupervisor.getUserID()) &&
                    currentProject.getProjectStatus() != ProjectStatus.ALLOCATED &&
                    currentProject.getProjectStatus() != ProjectStatus.RESERVED) {

                // Set status of the projects to new status
                currentProject.setProjectStatus(newStatus);
            }
        }
    }
}
