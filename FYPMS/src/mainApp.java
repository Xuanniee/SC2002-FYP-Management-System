import java.util.Scanner;

public class mainApp{
    public static void main(String[] args){
        StudentDB student_list = new StudentDB();
        FacultyDB faculty_list = new FacultyDB();
        ProjectDB project_list = new ProjectDB();
        FYPcoordDB FYPcoord_list = new FYPcoordDB();

        int userInput;

        Scanner scObject = new Scanner(System.in);

        
        
        
    }

    /**
     * Displays the welcome message.
     */
    public static void printWelcome() {
        System.out.println();
        System.out.println("#######   #      #  #######   ##       ##   #######");
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
        System.out.println("************ I can help you with these functions: *************");
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