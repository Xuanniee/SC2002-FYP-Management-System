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

        /*Login Page*/
        Scanner scanner = new Scanner(System.in);
        printWelcome();
        System.out.println("Please enter your username:");
        String username = scanner.nextLine();
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();
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
     * Displays all the options of the system.
     */
    public static void printSupervisorOptions() {
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