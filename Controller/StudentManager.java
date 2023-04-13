package Controller;

import java.util.Scanner;

import Entities.*;

public class StudentManager {

    private StudentDB studentDB;
    private ProjectDB projectDB;
    private FYPcoordDB fyPcoordDB;
    private RequestManager requestManager;

    Scanner scanner = new Scanner(System.in);

    private Student currentStudent;

    public StudentManager(Student student, StudentDB studentDB, ProjectDB projectDB, RequestManager requestManager,
            FYPcoordDB fyPcoordDB) {
        this.currentStudent = student;
        this.studentDB = studentDB;
        this.projectDB = projectDB;
        this.fyPcoordDB = fyPcoordDB;
        this.requestManager = requestManager;
    }

    public void processStudentChoice(int choice) {

        switch (choice) {
            case 1:
                System.out.println("View Student Profile...");
                studentDB.viewStudentProfile(currentStudent);
                break;

            case 2:
                System.out.println("View All Projects...");
                if (currentStudent.getIsDeregistered()) {
                    System.out.println(
                            "You are not allowed to view any projects because you have already deregistered from FYP.");
                } else {
                    projectDB.viewAvailableProjects(currentStudent);
                }
                break;

            case 3:
                System.out.println("View Registered Project...");
                if (!currentStudent.getIsAssigned()) {
                    System.out.println("You have not registered for any projects.");
                } else {
                    studentDB.viewRegisteredProject(currentStudent);
                }
                break;

            case 4:
                System.out.println("Request to Register for a Project...");
                projectDB.viewAvailableProjects(currentStudent);
                requestManager.studentRegister(currentStudent);
                break;

            case 5:
                System.out.println("Request to Change Title of Registered Project...");
                if (!currentStudent.getIsAssigned()) {
                    System.out.println("You do not have a registered project yet.");
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
                    System.out.println("You do not have a registered project yet.");
                    break;
                } else {
                    System.out.println("Your registered project: ");
                    currentStudent.getAssignedProject().printProjectDetails();
                    requestManager.studentDeregister(currentStudent, fyPcoordDB.getFypCoordinatorsList().get(0));
                }
                break;

            case 7:
                System.out.println("View all Request History...");
                requestManager.getRequestHistory(currentStudent);
                break;

            case 0:
                System.out.println("Logging out...");
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
        System.out.println("|              Enter 0 to Log out of FYPMS              |");
        System.out.println("+-------------------------------------------------------+");
        System.out.println(""); // print empty line

        System.out.print("Please enter your choice: ");

        choice = scanner.nextInt();

        return choice;
    }

}
