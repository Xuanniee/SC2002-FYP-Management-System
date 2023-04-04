public class User {
    protected String userID;
    protected String password = "password";
    protected String name;
    protected String email;

    public Boolean checkPassword(String inputPassword) {
        // Returns True if the Password matches
        return this.password.equals(inputPassword);
    }

    /*For testing purposes */
    public void viewDetails(){
        System.out.println(userID + " " + name + " " + email + "\n");
    }

    public String getName() {
        return this.name;
    }
}
