import Boundary.MainMenuUI;
import Controller.StudentDB;

public class MainApp {
    public static void main(String[] args) {
        try {
            MainMenuUI mainMenu = new MainMenuUI();
            mainMenu.displayMainMenu();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }
}
