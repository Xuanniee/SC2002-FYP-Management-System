package Entities;

import java.util.ArrayList;
import java.util.Scanner;

import enums.*;

public class Student extends User {

    private static final UserType userType = UserType.STUDENT;
    private Project project;
    private Boolean isAssigned;
    private ArrayList<Request> studentHistory;
    private ArrayList<Project> deregisteredProjects;

    /**
     * Constructor for Student
     * 
     * @param userID    Login ID of user
     * @param userName
     * @param userEmail
     */
    public Student(String userID, String userName, String userEmail) {
        super(userID, "password");
        this.project = null;
        this.isAssigned = false;
        this.studentHistory = new ArrayList<Request>();
        this.deregisteredProjects = new ArrayList<Project>();
    }

    public UserType getUserType() {
        return Student.userType;
    }

    public void setAssignedProject(Project project) {
        this.project = project;
        this.isAssigned = true;
    }

    public Boolean getIsAssigned() {
        return this.isAssigned;
    }

    public void setIsAssigned(Boolean assign) {
        this.isAssigned = assign;
    }

    public Project getAssignedProject() {
        return this.project;
    }

    public void addHistory(Request request) {
        this.studentHistory.add(request);
    }

    public void addDeregisteredProjects(Project project) {
        this.deregisteredProjects.add(project);
    }

    public ArrayList<Request> getRequestHistory() {
        return this.studentHistory;
    }

    public void displayStudentMenu() {

        int choice;
        Scanner sc = new Scanner(System.in);

        do {
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
            System.out.print("Choice chosen is: ");

            choice = sc.nextInt();

        } while (choice != 0);

        sc.close();
    }

}
