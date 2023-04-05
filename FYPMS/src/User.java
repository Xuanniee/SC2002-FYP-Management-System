import java.util.ArrayList;
import enums.UserType;

public class User {
    protected String userID;
    protected String password = "password";
    protected String name;
    protected String email;
    protected UserType userType;

    // Public Constructors for the 3 User Types
    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
        this.userType = UserType.UNKNOWN;
    }

    public Boolean checkPassword(String inputPassword) {
        // Returns True if the Password matches
        return this.password.equals(inputPassword);
    }

    public UserType authenticateUser(String userID, String password, ArrayList<Supervisor> supervisorList, ArrayList<Student> studentList) {
        // Determine if the User is a Student, Professor, or FYP Coord
        for (int i = 0; i < supervisorList.size(); i += 1){
            // If User is Found
            if (supervisorList.get(i).userID.equals(userID) && supervisorList.get(i).password.equals(password)) {
                this.userType = UserType.FACULTY;
            }
        }

        if (this.userType == UserType.UNKNOWN) {
            // If not Faculty, then check if Student
            for (int i = 0; i < studentList.size(); i += 1) {
                if (studentList.get(i).userID.equals(userID) && studentList.get(i).equals(password)) {
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
        System.out.println(userID + " " + name + " " + email + "\n");
    }

    public String getName() {
        return this.name;
    }
}
