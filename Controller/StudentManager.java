package Controller;

import java.util.Scanner;

import Entities.*;

public class StudentManager {

    private StudentDB studentDB;
    private ProjectDB projectDB;
    private RequestManager requestManager = new RequestManager();

    Scanner scanner = new Scanner(System.in);

    private Student currentStudent;

    public StudentManager(Student student, StudentDB studentDB, ProjectDB projectDB) {
        this.currentStudent = student;
        this.studentDB = studentDB;
        this.projectDB = projectDB;
    }

    public void processStudentChoice(int choice) {

        switch (choice) {
            case 1:
                System.out.println("View Student Profile...");
                studentDB.viewStudentProfile(currentStudent);
                break;

            case 2:
                System.out.println("View All Projects...");
                projectDB.viewAvailableProjects(currentStudent);
                break;

            case 3:
                System.out.println("View Registered Project...");
                studentDB.viewRegisteredProject(currentStudent);
                break;

            case 4:
                System.out.println("Request to Register for a Project...");
                projectDB.viewAvailableProjects(currentStudent);
                requestManager.studentRegister(currentStudent);
                break;

            case 5:
                System.out.println("Request to Change Title of Registered Project...");
                if (!currentStudent.getIsAssigned()) {
                    break;
                } else {
                    System.out.println("Your registered project: ");
                    currentStudent.getAssignedProject().printProjectDetails();
                    requestManager.changeTitle(currentStudent, currentStudent.getAssignedProject());
                }
                break;

            case 6:
                System.out.println("Request to Deregister from Registered Project...");
                if (!currentStudent.getIsAssigned()) {
                    break;
                } else {
                    System.out.println("Your registered project: ");
                    currentStudent.getAssignedProject().printProjectDetails();
                    requestManager.studentDeregister(currentStudent);
                }
                break;

            case 7:
                System.out.println("View all Request History...");
                requestManager.getRequestHistory(currentStudent);
                break;

            case 0:
                System.out.println("Returning to homepage...");
                break;

            default:
                System.out.println("Please enter a valid choice");
                break;
        }

    }

    public int displayStudentMenu() {
        int choice;

        System.out.println(""); // print empty line
        System.out.println("+-------------------------------------------------------+");
        System.out.println("|                   Student Portal                      |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("| 1. View Profile                                       |");
        System.out.println("| 2. View All Projects                                  |");
        System.out.println("| 3. View Registered Project                            |");
        System.out.println("| 4. Request to Register for a Project                  |");
        System.out.println("| 5. Request to Change Title of Project                 |");
        System.out.println("| 6. Request to Deregister from Project                 |");
        System.out.println("| 7. View Request History                               |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("|           Enter 0 to go back to Main Menu             |");
        System.out.println("+-------------------------------------------------------+");
        System.out.println(""); // print empty line

        System.out.print("Please enter your choice: ");

        choice = scanner.nextInt();

        return choice;
    }

}
