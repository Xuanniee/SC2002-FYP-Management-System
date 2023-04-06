package Entities;

import java.util.ArrayList;

import enums.UserType;

public class User {
    protected String userID;
    protected String password = "password";
    protected String name;
    protected String userEmail;
    protected UserType userType = UserType.UNKNOWN;

    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
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
        return this.userID;
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

    public Boolean checkPassword(String inputPassword) {
        // Returns True if the Password matches
        return this.password.equals(inputPassword);
    }

    public UserType authenticateUser(String userID, String password, ArrayList<Supervisor> supervisorList, ArrayList<Student> studentList) {
        // Determine if the User is a Student, Professor, or FYP Coord
        for (int i = 0; i < supervisorList.size(); i += 1){
            // If User is Found
            if (supervisorList.get(i).getUserID().equalsIgnoreCase(userID) && supervisorList.get(i).getPassword().equalsIgnoreCase(password)) {
                System.out.println(supervisorList.get(i).getUserID());
                System.out.println(supervisorList.get(i).getPassword());
                this.userType = UserType.FACULTY;
            }
        }

        if (this.userType == UserType.UNKNOWN) {
            // If not Faculty, then check if Student
            for (int i = 0; i < studentList.size(); i += 1) {
                if (studentList.get(i).getUserID().equalsIgnoreCase(userID) && studentList.get(i).getPassword().equalsIgnoreCase(password)) {
                    this.userType = UserType.STUDENT;
                }
            }
        }

        return this.userType;
    }

    /**
     * Displays all the options of the system. Abstract so can be overridden
     */
    public void printMenuOptions() {
        System.out.println("Should never be printed.");
    }

    /*For testing purposes */
    public void viewDetails(){
        System.out.println(userID + " " + name + " " + userEmail + "\n");
    }

    public void printProfile() {
        System.out.println("Name: " + getUserName());
        System.out.println("UserID: " + getUserID());
        System.out.println("Email: " + getUserEmail());
    }

}
