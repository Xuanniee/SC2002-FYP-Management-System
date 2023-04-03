package Controller;

import Entities.*;

public class StudentManager {

    private StudentLoginManager studentLoginManager = new StudentLoginManager();
    private StudentDB studentDB = new StudentDB();
    private ProjectDB projectDB = new ProjectDB();

    public void processStudentChoice(int choice) {

        switch (choice) {
            case 1:
                System.out.println("View Student Profile...");
                studentDB.viewStudentProfile(getCurrentStudent());
                break;

            case 2:
                System.out.println("View All Projects...");
                projectDB.viewAvailableProjects();
                break;

            case 3:
                System.out.println("View Registered Project...");
                studentDB.viewRegisteredProject(getCurrentStudent());
                break;

            case 4:
                System.out.println("Request to Register for a Project...");
                // systemSettingController.displaySystemSetting();
                break;

            case 5:
                System.out.println("Request to Change Title of Registered Project...");
                // loginController.displaySignup();
                break;

            case 6:
                System.out.println("Request to Deregister from Registered Project...");
                // loginController.displaySignup();
                break;

            case 7:
                System.out.println("View all Request History...");
                studentDB.viewRequestHistory(getCurrentStudent());
                break;

            case 0:
                System.out.println("Returning to homepage...");
                break;

            default:
                System.out.println("Please enter a valid choice");
                break;
        }

    }

    public boolean checkLoggedin() {
        if (studentLoginManager.getIsLoggedIn() == true) {
            System.out.println("");
            System.out.println(
                    "You are currently logged in as [" + studentLoginManager.currentStudent.getUserName() + "]");
            return true;
        }
        System.out.println("");
        return false;
    }

    public void triggerLogin() {
        studentLoginManager.displayLogin();
    }

    public void triggerLogout() {
        studentLoginManager.logout();
    }

    public Student getCurrentStudent() {
        return studentLoginManager.getCurrentStudent();
    }

}
