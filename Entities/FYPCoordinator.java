package Entities;



public class FYPCoordinator extends User{
    /**
     * Constructor for FYP Coordinator
     * @param userID
     * @param name
     * @param userEmail
     */
    public FYPCoordinator(String userID, String name, String userEmail) {
        super(userID, "password");
        this.name = name;
        this.userEmail = userEmail;
    }

    // Setters and Getters
    public String getFYPCoordID() {
        return this.userID;
    }

    public Boolean setFYPCoordID(String newUserId) {
        if (newUserId.isEmpty()) {
            return false;
        }
        this.userID = newUserId;
        return true;
    }

    public String getFYPCoordName() {
        return this.name;
    }

    public Boolean setFYPCoordName(String newName) {
        if (newName.isEmpty()) {
            return false;
        }
        this.name = newName;
        return true;
    }

    public String getFYPCoordEmail() {
        return this.userEmail;
    }

    public Boolean setFYPCoordEmail(String newEmail) {
        if (newEmail.isEmpty()) {
            return false;
        }
        this.userEmail = newEmail;
        return true;
    }

    public String getFYPCoordPassword() {
        return this.password;
    }

    public Boolean setFYPCoordPassword(String newPassword) {
        if (newPassword.isEmpty()) {
            return false;
        }
        this.password = newPassword;
        return true;
    }


    
}
