import java.util.Scanner;

public class mainApp{
    public static void main(String[] args){
        /*Initialise Database*/
        StudentDB student_list = new StudentDB();
        FacultyDB faculty_list = new FacultyDB();
        ProjectDB project_list = new ProjectDB();
        FYPcoordDB FYPcoord_list = new FYPcoordDB();

        /*For Testing purposes*/
        //student_list.viewDB();
        //faculty_list.viewDB();
        
        Scanner scanner = new Scanner(System.in);
        int userInput;

        /* Login Page */
        printWelcome();
        System.out.println("Please enter your username:");
        String username = scanner.nextLine();
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();

        // Check through all the Users
        do {
            printSupervisorOptions(username);
            userInput = scanner.nextInt();

            Supervisor tester = faculty_list.getSupervisor("ASMADHUKUMAR");
            switch(userInput) {
                case 1:
                    tester.createProject(project_list, scanner);
                    break;
                case 2:
                    tester.viewOwnProject(project_list.retrieveProfessorProjects(tester.getName()));
                    break;
                case 3:
                    tester.modifyTitle(project_list, scanner);
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Error!!");
            }

        } while (userInput != 7);
        
        scanner.close();
    }

    /**
     * Displays the welcome message.
     */
    public static void printWelcome() {
        System.out.println();
        System.out.println("#######   #     #   #######   ##       ##   #######");
        System.out.println("#          #   #    #     #   # #     # #   #      ");
        System.out.println("#           # #     #     #   # #     # #   #      ");
        System.out.println("#######      #      #######   #  #  #   #   #######");
        System.out.println("#            #      #         #  #  #   #         #");
        System.out.println("#            #      #         #   ##    #         #");
        System.out.println("#            #      #         #   ##    #   #######");
        System.out.println();
        System.out.println("****************** Welcome to FYPMS! ******************");
        System.out.println();
    }

    /**
     * Displays all the options of the system. Abstract so can be overridden
     */
    public static void printSupervisorOptions(String professorName) {
        System.out.println("************ Welcome to FYPMS " + professorName + " *************");
        System.out.println("************ Supervisor Options: *************");
        System.out.println(" 1. Create a Project.");
        System.out.println(" 2. View own Project(s).");
        System.out.println(" 3. Modify the title of your Project(s).");
        System.out.println(" 4. View pending requests.)");
        System.out.println(" 5. View request history & status.");
        System.out.println(" 6. Request the transfer of a student.");
        System.out.println(" 7. Log out.");
        System.out.println();
    }


}