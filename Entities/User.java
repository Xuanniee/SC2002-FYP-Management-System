package Entities;

import java.util.ArrayList;
import java.util.Scanner;

import enums.UserType;

public class User {
    protected String userID;
    protected String password = "password";
    protected String userName;
    protected String userEmail;
    protected UserType userType = UserType.UNKNOWN;

    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
        this.userType = UserType.UNKNOWN;
    }

    public User(String userID, String userName, String userEmail) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = "password";
        this.userType = UserType.UNKNOWN;
    }

    /**
     * Setters and Getters
     */
    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userID = userName;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public UserType getUserType() {
        return this.userType;
    }

    public Boolean checkPassword(String inputPassword) {
        // Returns True if the Password matches
        return this.password.equals(inputPassword);
    }

    public UserType authenticateUser(String userID, String password, ArrayList<Supervisor> supervisorList,
            ArrayList<Student> studentList, ArrayList<FYPCoordinator> fypCoordinatorsList) {
        // Determine if the User is a Student, Professor, or FYP Coord
        // Supervisor
        for (int i = 0; i < supervisorList.size(); i += 1) {
            // If User is Found
            if (supervisorList.get(i).getUserID().equalsIgnoreCase(userID)
                    && supervisorList.get(i).getPassword().equalsIgnoreCase(password)) {
                this.userType = UserType.FACULTY;
            }
        }

        // Check if Student
        if (this.userType == UserType.UNKNOWN) {
            // If not Faculty, then check if Student
            for (int i = 0; i < studentList.size(); i += 1) {
                if (studentList.get(i).getUserID().equalsIgnoreCase(userID)
                        && studentList.get(i).getPassword().equalsIgnoreCase(password)) {
                    this.userType = UserType.STUDENT;
                }
            }
        }

        // Check if FYP Coordinator {Can be Supervisor}
        if (this.userType == UserType.UNKNOWN || this.userType == UserType.FACULTY) {
            // If Faculty, Check if FYP Coord
            for (int i = 0; i < fypCoordinatorsList.size(); i += 1) {
                if (fypCoordinatorsList.get(i).getUserID().equalsIgnoreCase(userID)
                        && fypCoordinatorsList.get(i).getPassword().equalsIgnoreCase(password)) {
                    this.userType = UserType.FYPCOORDINATOR;
                }
            }
        }
        return this.userType;
    }

    /**
     * Displays all the options of the system. Should not be Abstract as User must not be an Abstract Class for instantiation
     */
    public void printMenuOptions() {
        System.out.println("Should never be printed.");
    }

    /* For testing purposes */
    public void viewDetails() {
        System.out.println(userID + " " + userName + " " + userEmail + "\n");
    }

    public void printProfile() {
        System.out.println("Name   : " + getUserName());
        System.out.println("UserID : " + getUserID());
        System.out.println("Email  : " + getUserEmail());
    }

    // public Boolean changeUserPassword(User loggedUser, Scanner scObject) {
    //     int validationAttempts = 3;
    //     do {
    //         System.out.println(""); // print empty line
    //         System.out.println("+----------------------------------------------------------+");
    //         System.out.println("|                   Password Change Portal                 |");
    //         System.out.println("|----------------------------------------------------------|");
    //         System.out.println("|  Please re-enter your password to verify your identity.  |");
    //         System.out.println("|----------------------------------------------------------|");
    //         System.out.println("|              Enter 0 to go back to Main Menu             |");
    //         System.out.println("+----------------------------------------------------------+");
    //         System.out.println(""); // print empty line

    //         System.out.println("Enter your Password: ");
    //         String userPassword = scObject.nextLine();

    //         // Check if Password is correct (Case Sensitive)
    //         if (loggedUser.getPassword().equals(userPassword)) {
    //             System.out.println("Enter your New Password   : ");
    //             String newPassword1 = scObject.nextLine();

    //             System.out.println("Re-Enter your New Password: ");
    //             String newPassword2 = scObject.nextLine();

    //             if (newPassword1.equals(newPassword2)) {

    //             }


    //         }
    //     } while();




    // }
}
