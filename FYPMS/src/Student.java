public class Student extends User{
    public Student(String userID, String name, String email){
        super(userID, email);
        this.userID = userID;
        this.name = name;
        this.email = email;
    }

    @Override
    public void printMenuOptions(){
        System.out.println("Currently Empty");
    }
}
