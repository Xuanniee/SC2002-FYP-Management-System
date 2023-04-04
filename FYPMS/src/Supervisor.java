public class Supervisor extends User{

    Supervisor(String userID, String name, String email){
        this.userID = userID;
        this.name = name;
        this.email = email;
    }

    /**
     * Method to allow User
     */
    public void createProject(String projectTitle){

    };
    public void viewOwnProject(){};
    public void modifyTitle(){};
    public void viewPendingRequests(){};
    public void viewCreatedProjects(){};
    public void requestTransfer(){};
}
