package Controller;

import java.util.Scanner;

import Entities.Supervisor;

public class SupervisorManager {
    Supervisor managedSupervisor;
    private FacultyDB facultyDB;
    private ProjectDB projectDB;
    Scanner scanner = new Scanner(System.in);

    // Constructor
    public SupervisorManager(Supervisor supervisor, FacultyDB facultyDB, ProjectDB projectDB){
        this.managedSupervisor = supervisor;
        this.facultyDB = facultyDB;
        this.projectDB = projectDB;
    }

    /*public SupervisorManager(String supervisorUserID, FacultyDB facultyDB) {
        // Determine the Managed Supervisor
        this.managedSupervisor = facultyDB.findSupervisor(supervisorUserID);
    };

    /**
     * Function that allows Supervisor to interface with the Menu
     * @param scObject
     * @param project_list
     */
    public void processSupervisorChoice(int userInput) {

            // Call the Menu for the respective users
        switch(userInput) {
            case 1:
                facultyDB.createProject(projectDB, managedSupervisor);
                break;
            case 2:
                facultyDB.viewOwnProject(projectDB.retrieveProfessorProjects(managedSupervisor.getSupervisorName()));
                break;
            case 3:
                facultyDB.modifyTitle(projectDB,managedSupervisor);
                break;
            case 7:
                break;
            case 0:
                System.out.println("Returning to homepage...");
                break;
            default:
                System.out.println("Please enter a valid choice");
                break;
        }

        //System.out.println("Thank you for using FYPMS. You have been logged out.");
    }

    public int displayFacultyMenu() {
        int choice;

        System.out.println(""); // print empty line
        System.out.println("+-------------------------------------------------------+");
        System.out.println("|                   Faculty Portal                      |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("| 1. Create a Project                                   |");
        System.out.println("| 2. View own Project(s)                                |");
        System.out.println("| 3. Modify the title of your Project(s)                |");
        System.out.println("| 4. View pending requests                              |");
        System.out.println("| 5. View request history & status                      |");
        System.out.println("| 6. Request the transfer of a student                  |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("|           Enter 0 to go back to Main Menu             |");
        System.out.println("+-------------------------------------------------------+");
        System.out.println(""); // print empty line

        System.out.print("Please enter your choice: ");

        choice = scanner.nextInt();

        return choice;
    }
    
}
