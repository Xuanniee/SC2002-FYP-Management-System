package Controller;

import java.util.Scanner;

import Entities.Supervisor;

public class SupervisorManager {
    Supervisor managedSupervisor;
    
    // Constructor
    public SupervisorManager(String supervisorUserID, FacultyDB facultyDB) {
        // Determine the Managed Supervisor
        this.managedSupervisor = facultyDB.getSupervisor(supervisorUserID);
    };

    /**
     * Function that allows Supervisor to interface with the Menu
     * @param scObject
     * @param project_list
     */
    public void processSupervisorChoice(Scanner scObject, ProjectDB project_list) {
        int userInput;

        do {
            // Call the Menu for the respective users
            managedSupervisor.printMenuOptions();
            userInput = scObject.nextInt();

            switch(userInput) {
                case 1:
                    managedSupervisor.createProject(project_list, scObject);
                    break;
                case 2:
                    managedSupervisor.viewOwnProject(project_list.retrieveProfessorProjects(managedSupervisor.getSupervisorName()));
                    break;
                case 3:
                    managedSupervisor.modifyTitle(project_list, scObject);
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Error!!");
            }
        } while(userInput != 7);

        System.out.println("Thank you for using FYPMS. You have been logged out.");
    }
    
}
