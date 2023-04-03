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
        System.out.println("Welcome to the Login Page.");
        System.out.println("--------------------------");
        System.out.println("Please enter your username:");
        String username = scanner.nextLine();
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();
    }
}