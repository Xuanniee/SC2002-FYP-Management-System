package Controller;

import java.util.Scanner;

import Entities.Faculty;

public class FacultyLoginManager {

    Faculty currentFaculty;

    FacultyDB facultyDB = new FacultyDB();

    Scanner sc = new Scanner(System.in);

    public FacultyLoginManager() {
    }

    /**
     * This method displays the login UI for staff to input Username and Password
     */
    public void displayLogin() {

        String userID, password;

        System.out.println("+-------------------------------------------------------+");
        System.out.println("|               Welcome to Faculty Portal               |");
        System.out.println("+-------------------------------------------------------+");
        System.out.println("");
        System.out.print("Please enter UserID: ");
        userID = sc.next();
        System.out.println("");
        System.out.print("Please enter Password: ");
        password = sc.next();
        System.out.println("");

        

    }

    

}



