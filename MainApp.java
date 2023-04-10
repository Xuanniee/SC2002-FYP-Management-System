import java.util.Scanner;
import java.io.Console;

import enums.*;
import Entities.*;
import Controller.*;

public class MainApp{
    public static void main(String[] args){
        /* Initialise Database */
        StudentDB student_list = new StudentDB();
        FacultyDB faculty_list = new FacultyDB();
        ProjectDB project_list = new ProjectDB();
        FYPcoordDB FYPcoord_list = new FYPcoordDB();
        RequestTransferDB requestTransferDB = new RequestTransferDB();
        RequestRegisterDB requestRegisterDB = new RequestRegisterDB();
        RequestDeregisterDB deregisterDB = new RequestDeregisterDB();
        RequestManager requestManager = new RequestManager();


        /*For Testing purposes*/
        //student_list.viewDB();
        //faculty_list.viewDB();
        
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
            mainMenuInput = scanner.nextInt();
            // Remove Enter
            scanner.nextLine();

            if (mainMenuInput == 2) {
                break;
            }
            else if (mainMenuInput == 1) {
                do {
                    System.out.println("Enter your username:");
                    username = scanner.nextLine();
                    
                    char passwordArray[] = terminalConsole.readPassword("Enter your Password: ");
                    String maskedPassword = new String(passwordArray);
        
                    // Create attempted User
                    User attemptUser = new User(username, maskedPassword);
                    attemptUserType = attemptUser.authenticateUser(username, maskedPassword, faculty_list.getSupervisorList(),
                            student_list.getStudentList(), FYPcoord_list.getFypCoordinatorsList());
        
                    // Check if exceed login limits
                    if (numLoginAttempts == 0) {
                        System.exit(1);
                    } else if (attemptUserType == UserType.UNKNOWN) {
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        System.out.println(
                                "Your Login Credentials are errorneous. You have " + numLoginAttempts + " attempts left.");
                        numLoginAttempts -= 1;
                    }
        
                } while (attemptUserType == UserType.UNKNOWN);
        
                // Find the Corresponding User
                switch (attemptUserType) {
                    case STUDENT:
                        Student loggedStudent = student_list.findStudent(username);
                        StudentManager studentMgr = new StudentManager(loggedStudent);
                        int choice;
                        do {
                            choice = studentMgr.displayStudentMenu();
                            studentMgr.processStudentChoice(choice);
                        } while (choice != 0);
                        break;
        
                    case FACULTY:
                        SupervisorManager supervisorManager = new SupervisorManager(username, faculty_list);
                        supervisorManager.processSupervisorChoice(scanner, project_list);
                        break;
        
                    case FYPCOORDINATOR:
                        FYPCoordinator fypCoordinator = FYPcoord_list.findFypCoordinator(username);
                        FYPCoordinatorManager fypManager = new FYPCoordinatorManager(fypCoordinator);
                        int fypChoice;
                        do {
                            fypChoice = fypManager.displayFypCoordinatorMenu();
                            fypManager.processFypCoordinatorChoice(fypChoice, project_list, requestRegisterDB, requestTransferDB, deregisterDB, requestManager);
                        } while (fypChoice != 0);

                        break;
        
                    case UNKNOWN:
                        System.out.println("Error!! Should never be unknown.");
                }
            }
            else {
                System.out.println("Invalid Option. Please select from 1 of the 2 options.");
            }
        } while(true);

        scanner.close();
    }

    /**
     * Displays the welcome message.
     */
    public static void printWelcome() {
        System.out.println();
        System.out.println("#######   #     #   #######   ##       ##   #######");
        System.out.println("#          #   #    #     #   # #     # #   #      ");
        System.out.println("#           # #     #     #   # #    #  #   #      ");
        System.out.println("#######      #      #######   #  #  #   #   #######");
        System.out.println("#            #      #         #  #  #   #         #");
        System.out.println("#            #      #         #   ##    #         #");
        System.out.println("#            #      #         #   ##    #   #######");
        System.out.println();
        System.out.println("****************** Welcome to FYPMS! ******************");
        System.out.println("##### Enter 1 to Log in or Enter 2 to exit FYPMS. #####");
    }

    


}