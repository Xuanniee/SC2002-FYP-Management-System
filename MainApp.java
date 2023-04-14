import java.util.Scanner;
import java.io.Console;

import enums.*;
import Entities.*;
import Controller.*;

public class MainApp {
    public static void main(String[] args) {
        /* Initialise Database */
        StudentDB student_list = new StudentDB();
        FacultyDB faculty_list = new FacultyDB();
        FYPcoordDB FYPcoord_list = new FYPcoordDB();
<<<<<<< Updated upstream
        ProjectDB project_list = new ProjectDB(faculty_list, student_list);
=======
        ProjectDB project_list = new ProjectDB(faculty_list);
>>>>>>> Stashed changes
        ProjectAllocationDB allocation_list = new ProjectAllocationDB();
        RequestTransferDB requestTransferDB = new RequestTransferDB(project_list, faculty_list);
        RequestRegisterDB requestRegisterDB = new RequestRegisterDB(project_list, student_list, faculty_list,
                allocation_list);
        RequestDeregisterDB requestDeregisterDB = new RequestDeregisterDB(project_list, student_list, FYPcoord_list);
        RequestChangeTitleDB requestChangeTitleDB = new RequestChangeTitleDB(project_list, student_list, faculty_list);
        RequestManager requestManager = new RequestManager(project_list, faculty_list, requestChangeTitleDB,
                requestRegisterDB,
                requestDeregisterDB, requestTransferDB);

        Scanner scanner = new Scanner(System.in);
        Console terminalConsole = System.console();

        // Check to ensure Console is available
        if (terminalConsole == null) {
            System.err.println("No console.");
            System.exit(1);
        }

        String username;
        UserType attemptUserType;
        int numLoginAttempts = 3;
        int mainMenuInput;
        do {
            printWelcome();
            System.out.print("Your choice is: ");
            mainMenuInput = scanner.nextInt();
            // Remove Enter
            scanner.nextLine();
            System.out.println("");

            if (mainMenuInput == 2) {
                System.out.println("Exiting FYPMS...");
                break;
            } else if (mainMenuInput == 1) {
                do {
                    System.out.println("+-----------------------------------------------------------------------+");
                    System.out.println("|                            Login Interface                            |");
                    System.out.println("+-----------------------------------------------------------------------+");
                    System.out.print("Enter your username: ");
                    username = scanner.nextLine();

                    char passwordArray[] = terminalConsole.readPassword("Enter your Password: ");
                    String maskedPassword = new String(passwordArray);

                    // Create attempted User
                    User attemptUser = new User(username, maskedPassword);
                    attemptUserType = attemptUser.authenticateUser(username, maskedPassword,
                            faculty_list.getSupervisorList(),
                            student_list.getStudentList(), FYPcoord_list.getFypCoordinatorsList());

                    // Check if exceed login limits
                    if (numLoginAttempts == 0) {
                        System.exit(1);
                    } else if (attemptUserType == UserType.UNKNOWN) {
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        System.out.println(
                                "Your Login Credentials are errorneous. You have " + numLoginAttempts
                                        + " attempts left.");
                        numLoginAttempts -= 1;
                    }

                } while (attemptUserType == UserType.UNKNOWN);

                // Find the Corresponding User
                switch (attemptUserType) {
                    case STUDENT:
                        Student loggedStudent = student_list.findStudent(username);
                        StudentManager studentMgr = new StudentManager(loggedStudent, student_list, project_list,
                                requestManager, FYPcoord_list);
                        int choice;
                        do {
                            choice = studentMgr.displayStudentMenu();
                            studentMgr.processStudentChoice(choice);
                        } while (choice != 0);
                        break;

                    case FACULTY:
                        Supervisor loggedSupervisor = faculty_list.findSupervisor(username);
                        SupervisorManager supervisorManager = new SupervisorManager(loggedSupervisor, faculty_list,
                                project_list, requestManager, requestChangeTitleDB);
                        int supChoice;
                        do {
                            supChoice = supervisorManager.displayFacultyMenu();
                            supervisorManager.processSupervisorChoice(supChoice);
                        } while (supChoice != 0);
                        break;

                    case FYPCOORDINATOR:
                        FYPCoordinator fypCoordinator = FYPcoord_list.findFypCoordinator(username);
                        FYPCoordinatorManager fypManager = new FYPCoordinatorManager(fypCoordinator, project_list,
                                requestRegisterDB, requestTransferDB, requestDeregisterDB,
                                requestManager);
                        int fypChoice;
                        do {
                            fypChoice = fypManager.displayFypCoordinatorMenu();
                            fypManager.processFypCoordinatorChoice(fypChoice);
                        } while (fypChoice != 0);

                        break;

                    case UNKNOWN:
                        System.out.println("Error!! Should never be unknown.");
                }
            } else {
                System.out.println("Invalid Option. Please select from 1 of the 2 options.");
            }
        } while (true);

        scanner.close();
    }

    /**
     * Displays the welcome message.
     */
    public static void printWelcome() {
        System.out.println();
        System.out.println(" 88888888888  oooo    oooo   oooooooooo.  oooo         ooo   ooooooo.     ");
        System.out.println(" `88          od8y    y8bo  `888'   `Y8b  8888.       .88'  888   888.    ");
        System.out.println("  88           888    888    888     888  88 88b     d'88    '888         ");
        System.out.println("  8Y88888888     88888       888oooo888'  88  Y88  8P  88       '888      ");
        System.out.println("  88              888        888          88   `888'   88          88b    ");
        System.out.println("  88              888        888          88     Y     88   888    88b    ");
        System.out.println("  8o              ooo        o88         o88o         o88o   8oo8888b'    ");
        System.out.println();
        System.out.println("+-------------------------- Welcome to FYPMS! --------------------------+");
        System.out.println("|              Enter 1 to Log in or Enter 2 to exit FYPMS.              |");
        System.out.println("+-----------------------------------------------------------------------+");
    }

}