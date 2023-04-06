package Controller;

import java.util.Scanner;

import Entities.Student;

public class StudentLoginManager {

    Student currentStudent;

    StudentDB studentDB = new StudentDB();

    Scanner sc = new Scanner(System.in);

    public StudentLoginManager() {
    }

    /**
     * This method displays the login UI for staff to input Username and Password
     */
    public void displayLogin() {

        String userID, password;

        System.out.println("+-------------------------------------------------------+");
        System.out.println("|               Welcome to Student Portal               |");
        System.out.println("+-------------------------------------------------------+");
        System.out.println("");
        System.out.print("Please enter UserID: ");
        userID = sc.next();
        System.out.println("");
        System.out.print("Please enter Password: ");
        password = sc.next();
        System.out.println("");

        if (loginUser(userID, password) == true) {
            String studentName = studentDB.findStudent(userID, password);
            this.currentStudent = new Student(userID, studentName, password);
            System.out.println("Login successful!");
        } else {
            System.out.println("Login unsuccessful. Please try again.");
        }

    }

    public boolean loginUser(String userID, String password) {
        return studentDB.login(userID, password);
    }

    public Student getCurrentStudent() {
        return this.currentStudent;
    }

    public boolean getIsLoggedIn() {
        if (this.currentStudent == null) {
            return false;
        } else {
            return true;
        }

    }

    public void logout() {
        this.currentStudent = null;
    }

}
