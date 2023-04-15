package Entities;

import java.io.Console;
import java.util.ArrayList;
import java.util.Scanner;

import enums.UserType;

/**
 * Represents a User in the FYP Management System.
 * A User can only be classified with 1 User Type, that is either Student, Faculty, or FYP Coordinator
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class User {
    /**
     * Unique Identifier of every User
     */
    protected String userID;

    /**
     * Password of User to log into the System. Default is "password"
     */
    protected String password = "password";

    /**
     * First & Last Name of the User
     */
    protected String userName;

    /**
     * Email Address of User
     */
    protected String userEmail;

    /**
     * Classification of User. 
     * User can only be 1 of 3 types: Student, Faculty, FYP Coordinator
     */
    protected UserType userType = UserType.UNKNOWN;

    /**
     * Constructor
     * Creates a new User Object with the given UserID and Password.
     * This object does not represent an actual user of FYPMS yet. Becomes an actual user only after identifying the User Type.
     * @param userID
     * @param password
     */
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
     * Gets the Unique ID of this User
     * 
     * @return this User's ID
     */
    public String getUserID() {
        return this.userID;
    }

    /**
     * Changes the User ID of this User.
     * 
     * @param userID This User's new UserID. Must be a unique identifier if used.
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean setUserID(String userID) {
        if (userID.isEmpty()) {
            return false;
        }
        this.userID = userID;
        return true;
    }

    /**
     * Gets the Password of this User
     * 
     * @return this User's password. Be careful when calling this function to avoid security issues.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Changes the Password of this User.
     * 
     * @param password This User's new password.
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean setPassword(String password) {
        if (password.isEmpty()) {
            return false;
        }
        this.password = password;
        return true;
    }

    /**
     * Gets the First and Last Name of this User.
     * 
     * @return this User's legal name.
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Changes the full legal name of this User.
     * 
     * @param userName this User's new legal name.
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean setUserName(String userName) {
        if (userName.isEmpty()) {
            return false;
        }
        this.userID = userName;
        return true;
    }

    /**
     * Gets this User's electronic mail address.
     * 
     * @return this user's email.
     */
    public String getUserEmail() {
        return this.userEmail;
    }

    /**
     * Updates this User's email address.
     * 
     * @param userEmail this User's new email address.
     * @return a Boolean to inform us if the function is working as intended.
     */
    public Boolean setUserEmail(String userEmail) {
        if (userEmail.isEmpty()) {
            return false;
        }
        this.userEmail = userEmail;
        return true;
    }

    /**
     * Retrieves the classification / type of User.
     * 
     * @return an Enumeration representing the UserType.
     */
    public UserType getUserType() {
        return this.userType;
    }

    /**
     * Verifies if the provided password matches the User's password in the database.
     * 
     * @param inputPassword this User's input.
     * @return a Boolean to inform us if the provided password is correct.
     */
    public Boolean checkPassword(String inputPassword) {
        // Returns True if the Password matches
        return this.password.equals(inputPassword);
    }

    /**
     * Authenticates the User trying to log into FYPMS.
     * Verifies and identifies what type of User is trying to log in so as to classify them.
     * 
     * @param userID Provided UserID Input
     * @param password Provided Password Input
     * @param supervisorList ArrayList of Registered Supervisors in FYPMS
     * @param studentList ArrayList of Registered Students in FYPMS
     * @param fypCoordinatorsList ArrayList of FYP Coordinators in FYPMS {Usually 1 for every course}
     * @return the UserType Enumeration to identify if it is a valid user.
     */
    public UserType authenticateUser(String userID, String password, ArrayList<Supervisor> supervisorList,
            ArrayList<Student> studentList, ArrayList<FYPCoordinator> fypCoordinatorsList) {
        // Determine if the User is a Student, Professor, or FYP Coord
        // Supervisor
        for (int i = 0; i < supervisorList.size(); i += 1) {
            // If User is Found
            if (supervisorList.get(i).getUserID().equalsIgnoreCase(userID)
                    && supervisorList.get(i).getPassword().equalsIgnoreCase(password)) {
<<<<<<< Updated upstream
                System.out.println(supervisorList.get(i).getUserID());
                System.out.println(supervisorList.get(i).getPassword());
=======
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
                && fypCoordinatorsList.get(i).getPassword().equalsIgnoreCase(password)) {
=======
                        && fypCoordinatorsList.get(i).getPassword().equalsIgnoreCase(password)) {
>>>>>>> Stashed changes
                    this.userType = UserType.FYPCOORDINATOR;
                }
            }
        }
        return this.userType;
    }

    // /* For testing purposes */
    // public void viewDetails() {
    //     System.out.println(userID + " " + userName + " " + userEmail + "\n");
    // }

    /**
     * Prints the Profile Details of the Logged User
     */
    public void printProfile() {
        System.out.println("Name   : " + getUserName());
        System.out.println("UserID : " + getUserID());
        System.out.println("Email  : " + getUserEmail());
    }

    /**
     * Provides an interface for User to change their Password.
     * Users must verify their identity by providing their password and any password input is masked for security reasons.
     * 
     * @param loggedUser Logged User
     * @param scObject Scanner to read input from User
     * @param terminalConsole Console to mask the Password of User
     * @return a Boolean to inform us if the provided password is correct.
     */
    public Boolean changeUserPassword(User loggedUser, Scanner scObject, Console terminalConsole) {
        int validationAttempts = 3;
        scObject.nextLine();
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

            System.out.print("Enter your Password: ");
            String userPassword = scObject.nextLine();
            //char[] maskedPassword = terminalConsole.readPassword("Enter your Password: ");
            //String userPassword = new String(maskedPassword);

            // Check if User wants to cancel Password Change
            if (userPassword.equals("0")) {
                System.out.println("Cancelling the Password Change Request.");
                System.out.println("Returning to previous menu...");
                return false;
            }

            // Check if Password is correct (Case Sensitive)
            if (loggedUser.getPassword().equals(userPassword)) {
                System.out.print("Enter your New Password: ");
                //maskedPassword = terminalConsole.readPassword("Enter your New Password   : ");
                String newPassword1 = scObject.nextLine();

                System.out.print("Re-Enter your New Password: ");
                //maskedPassword = terminalConsole.readPassword("Re-Enter your New Password: ");
                String newPassword2 = scObject.nextLine();

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
