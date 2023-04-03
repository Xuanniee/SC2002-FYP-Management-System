public class Supervisor extends User{
    Supervisor(String userID, String name, String email){
        this.userID = userID;
        this.name = name;
        this.email = email;
    }
    public void createProject(){};
    public void viewOwnProject(){};
    public void modifyTitle(){};
    public void viewPendingRequests(){};
    public void viewCreatedProjects(){};
    public void requestTransfer(){};
}
