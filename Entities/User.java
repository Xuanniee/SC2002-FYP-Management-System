package Entities;

import java.io.Console;
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

    public Boolean setUserID(String userID) {
        if (userID.isEmpty()) {
            return false;
        }
        this.userID = userID;
        return true;
    }

    public String getPassword() {
        return this.password;
    }

    public Boolean setPassword(String password) {
        if (password.isEmpty()) {
            return false;
        }
        this.password = password;
        return true;
    }

    public String getUserName() {
        return this.userName;
    }

    public Boolean setUserName(String userName) {
        if (userName.isEmpty()) {
            return false;
        }
        this.userID = userName;
        return true;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public Boolean setUserEmail(String userEmail) {
        if (userEmail.isEmpty()) {
            return false;
        }
        this.userEmail = userEmail;
        return true;
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

    // /**
    //  * Displays all the options of the system. Should not be Abstract as User must not be an Abstract Class for instantiation
    //  */
    // public int printMenuOptions(Scanner scObject) {
    //     System.out.println("Should never be printed.");
    //     return 0;
    // }

    /* For testing purposes */
    public void viewDetails() {
        System.out.println(userID + " " + userName + " " + userEmail + "\n");
    }

    public void printProfile() {
        System.out.println("Name   : " + getUserName());
        System.out.println("UserID : " + getUserID());
        System.out.println("Email  : " + getUserEmail());
    }

    public Boolean changeUserPassword(User loggedUser, Scanner scObject, Console terminalConsole) {
        int validationAttempts = 3;
        do {
            System.out.println(""); // print empty line
            System.out.println("+----------------------------------------------------------+");
            System.out.println("|                   Password Change Portal                 |");
            System.out.println("|----------------------------------------------------------|");
            System.out.println("|  Please re-enter your password to verify your identity.  |");
            System.out.println("|----------------------------------------------------------|");
            System.out.println("|              Enter 0 to go back to Main Menu             |");
            System.out.println("+----------------------------------------------------------+");
            System.out.println(""); // print empty line

            char[] maskedPassword = terminalConsole.readPassword("Enter your Password: ");
            String userPassword = new String(maskedPassword);

            // Check if User wants to cancel Password Change
            if (userPassword.equals("0")) {
                System.out.println("Cancelling the Password Change Request.");
                System.out.println("Returning to previous menu...");
                return false;
            }

            // Check if Password is correct (Case Sensitive)
            if (loggedUser.getPassword().equals(userPassword)) {
                maskedPassword = terminalConsole.readPassword("Enter your New Password   : ");
                String newPassword1 = new String(maskedPassword);

                maskedPassword = terminalConsole.readPassword("Re-Enter your New Password: ");
                String newPassword2 = new String(maskedPassword);

                if (newPassword1.equals(newPassword2)) {
                    // Change the Password
                    loggedUser.setPassword(newPassword1);
                    System.out.println("Your password has been changed successfully.");
                    break;
                }
                else {
                    System.out.println("Your passwords do not match. Please try again.");
                    continue;
                }
            }
            else {
                // User did not validate their password
                validationAttempts -= 1;
                System.out.println("You currently have " + validationAttempts + " more tries to validate your identity.");
            }
        } while(validationAttempts != 0);

        if (validationAttempts == 0) {
            // User could not validate himself
            System.out.println("You are not allowed to change your password as you have not validated your identity.");
            return false;
        }

        return true;
    }
}
