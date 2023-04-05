import java.util.Scanner;
import java.io.Console;
import enums.*;

public class mainApp{
    public static void main(String[] args){
        /* Initialise Database */
        StudentDB student_list = new StudentDB();
        FacultyDB faculty_list = new FacultyDB();
        ProjectDB project_list = new ProjectDB();
        FYPcoordDB FYPcoord_list = new FYPcoordDB();

        /*For Testing purposes*/
        //student_list.viewDB();
        //faculty_list.viewDB();
        
        Scanner scanner = new Scanner(System.in);
        Console terminalConsole = System.console();
        int userInput;

        // Check to ensure Console is available
        if (terminalConsole == null) {
            System.err.println("No console.");
            System.exit(1);
        }


        /* Login Page */
        String username;
        UserType attemptUserType;
        int numLoginAttempts = 3;
        printWelcome();
        do {
            System.out.println("Enter your username:");
            username = scanner.nextLine();
            
            char passwordArray[] = terminalConsole.readPassword("Enter your Password: ");
            String maskedPassword = new String(passwordArray);

            // Create attempted User
            User attemptUser = new User(username, maskedPassword);
            attemptUserType = attemptUser.authenticateUser(username, maskedPassword, faculty_list.getSupervisorList(), student_list.getStudentList());

            // Check if exceed login limits
            if (numLoginAttempts == 0) {
                System.exit(1);
            }
            else if (attemptUserType == UserType.UNKNOWN) {
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                System.out.println("Your Login Credentials are errorneous. You have " + numLoginAttempts + " attempts left.");
                numLoginAttempts -= 1;
            }

        } while(attemptUserType == UserType.UNKNOWN);

        // Find the Corresponding User
        switch(attemptUserType) {
            case STUDENT:
                Student loggedStudent = student_list.getStudent(username);
                break;

            case FACULTY:
                Supervisor loggedSupervisor = faculty_list.getSupervisor(username);
                do {
                    // Call the Menu for the respective users
                    loggedSupervisor.printMenuOptions();
                    userInput = scanner.nextInt();

                    switch(userInput) {
                        case 1:
                            loggedSupervisor.createProject(project_list, scanner);
                            break;
                        case 2:
                            loggedSupervisor.viewOwnProject(project_list.retrieveProfessorProjects(loggedSupervisor.getName()));
                            break;
                        case 3:
                            loggedSupervisor.modifyTitle(project_list, scanner);
                            break;
                        case 7:
                            break;
                        default:
                            System.out.println("Error!!");
                    }
                } while(userInput != 7);
                

            case FYPCOORDINATOR:
                break;

            case UNKNOWN:
                System.out.println("Error!! Should never be Unknown.");

        }

        
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
        System.out.println();
    }

    


}