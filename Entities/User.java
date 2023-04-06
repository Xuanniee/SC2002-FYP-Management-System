package Entities;

public class User {

    /**
     * Types of User
     */
    enum UserType {
        STUDENT, FACULTY
    }

    private String userID;
    private String password;
    private String userName;
    private String userEmail;

    public User(String userID, String userName, String userEmail) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = "password";
    }

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
        this.userName = userName;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void printProfile() {
        System.out.println("Name: " + getUserName());
        System.out.println("UserID: " + getUserID());
        System.out.println("Email: " + getUserEmail());
    }

}
