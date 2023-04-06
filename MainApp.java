import Boundary.MainMenuUI;

public class MainApp {
    public void main(String[] args) {
        try {
            MainMenuUI mainMenu = new MainMenuUI();
            mainMenu.displayMainMenu();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }
}
