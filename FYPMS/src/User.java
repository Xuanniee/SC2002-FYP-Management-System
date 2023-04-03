public class User {
    protected String userID;
    protected String password = "password";
    protected String name;
    protected String email;

    /*For testing purposes */
    public void viewDetails(){
        System.out.println(userID + " " + name + " " + email + "\n");
    }
}
