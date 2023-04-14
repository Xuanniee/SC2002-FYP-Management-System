package Controller;

import java.io.Console;
import java.util.Scanner;

import Entities.*;

public class StudentManager {

    private StudentDB studentDB;
    private ProjectDB projectDB;
    private FYPcoordDB fyPcoordDB;
    private RequestManager requestManager;
    private Scanner scanner;
    private Console terminaConsole;

    private Student currentStudent;

    public StudentManager(Student student, StudentDB studentDB, ProjectDB projectDB, RequestManager requestManager,
            FYPcoordDB fyPcoordDB, Scanner scanner, Console terminalConsole) {
        this.currentStudent = student;
        this.studentDB = studentDB;
        this.projectDB = projectDB;
        this.fyPcoordDB = fyPcoordDB;
        this.requestManager = requestManager;
        this.scanner = scanner;
        this.terminaConsole = terminalConsole;
    }

    public void processStudentChoice(int choice) {

        System.out.println("");

        switch (choice) {
            case 0:
                System.out.println("Logging out...");
                break;

            case 1:
                System.out.println("Viewing Student Profile...");
                studentDB.viewStudentProfile(currentStudent);
                break;

            case 2:
                System.out.println("Viewing All Projects...");
                if (currentStudent.getIsDeregistered()) {
                    System.out.println(
                            "You are not allowed to view any projects because you have already deregistered from FYP.");
                } else if(currentStudent.getIsAssigned()){
                    System.out.println("You are currently allocated to a FYP and do not have access to available project list.");
                } else {
                    projectDB.viewAvailableProjects();
                }
                break;

            case 3:
                System.out.println("Viewing Registered Project...");
                if (!currentStudent.getIsAssigned()) {
                    System.out.println("You have not registered for any projects.");
                } else {
                    studentDB.viewRegisteredProject(currentStudent);
                }
                break;

            case 4:
                System.out.println("Request to Register for a Project...");
                // Student is only allowed to make 1 Register Request at any point in time
                if (currentStudent.getHasAppliedForProject()) {
                    System.out.println("You have already applied for another project. " +
                            "Please wait for the results of the request before making another.");
                    return;
                } else {
                    projectDB.viewAvailableProjects();
                    requestManager.studentRegister(currentStudent);
                }
                break;

            case 5:
                System.out.println("Request to Change Title of Registered Project...");
                if (!currentStudent.getIsAssigned()) {
                    System.out.println("You do not have a registered project yet.");
                    break;
                } else if (currentStudent.getAssignedProject().getAwaitingTitleChangeRequest()) {
                    System.out.println("You have already requested for a Title Change. " +
                            "Please wait for the results of the request before making another.");
                } else if(currentStudent.getIsDeregistered()){
                    System.out.println("You are not allowed to view any projects because you have already deregistered from FYP.");
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
                System.out.println("Viewing all Request History...");
                requestManager.getRequestHistory(currentStudent);
                break;

            case 8:
                // Change password
                this.currentStudent.changeUserPassword(currentStudent, scanner, terminaConsole);
                break;

            default:
                System.out.println("Please enter a valid choice");
                break;
        }

    }
}
