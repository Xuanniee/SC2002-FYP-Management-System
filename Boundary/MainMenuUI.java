package Boundary;

import java.util.Scanner;

import Controller.FacultyManager;
import Controller.StudentManager;

public class MainMenuUI {

    private Scanner sc = new Scanner(System.in);

    private StudentManager studentManager = new StudentManager();
    private FacultyManager facultyManager = new FacultyManager();

    public void displayMainMenu() {
        System.out.println("");
        int choice;

        do {
            System.out.println(""); // print empty line
            System.out.println("88888888888  oooo    oooo   oooooooooo.  oooo         ooo   ooooooo.      ");
            System.out.println("`88          od8y    y8bo  `888'   `Y8b  8888.       .88'  888   888.     ");
            System.out.println(" 88           888    888    888     888  88 88b     d'88    '888          ");
            System.out.println(" 8Y88888888     88888       888oooo888'  88  Y88  8P  88       '888       ");
            System.out.println(" 88              888        888          88   `888'   88          88b     ");
            System.out.println(" 88              888        888          88     Y     88   888    88b     ");
            System.out.println(" 8o              ooo        o88         o88o         o88o   8oo8888b'     ");

            System.out.println("+-------------------------------------------------------+");
            System.out.println("|              Welcome to FYPMS Main Menu               |");
            System.out.println("|-------------------------------------------------------|");
            System.out.println("| 1. Student                                            |");
            System.out.println("| 2. Faculty                                            |");
            System.out.println("|-------------------------------------------------------|");
            System.out.println("|             Enter 0 to Exit Program                   |");
            System.out.println("+-------------------------------------------------------+");
            System.out.println(""); // print empty line

            System.out.print("Your choice is: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: // Student -- Display student menu
                    System.out.println("Entering Student's portal...");
                    System.out.println(""); // print empty line
                    displayStudentMenu();
                    break;

                case 2: // Faculty -- Display faculty menu
                    System.out.println("Entering Faculty's portal...");
                    System.out.println(""); // print empty line
                    displayFacultyMenu();
                    break;

                case 3: // Exit program
                    System.out.println("Exiting Portal...");
                    System.exit(0);
                    break;

                default: //
                    System.out.println("Invalid choice. Please re-enter your option.");
                    System.out.println(""); // print empty line
                    break;
            }

        } while (choice != 0);

    }

    public void displayStudentMenu() {

        int choice;

        while (!studentManager.checkLoggedin()) {
            System.out.println("Redirecting to Student Portal Login Page");
            System.out.println(""); // print empty line
            studentManager.triggerLogin();
        }

        do {
            System.out.println(""); // print empty line
            System.out.println("+-------------------------------------------------------+");
            System.out.println("|                   Student Portal                      |");
            System.out.println("|-------------------------------------------------------|");
            System.out.println("| 1. View Profile                                       |");
            System.out.println("| 2. View All Projects                                  |");
            System.out.println("| 3. View Registered Project                            |");
            System.out.println("| 4. Request to Register for a Project                  |");
            System.out.println("| 5. Request to Change Title of Project                 |");
            System.out.println("| 6. Request to Deregister from Project                 |");
            System.out.println("| 7. View Request History                               |");
            System.out.println("|-------------------------------------------------------|");
            System.out.println("|           Enter 0 to go back to Main Menu             |");
            System.out.println("+-------------------------------------------------------+");
            System.out.println(""); // print empty line
            System.out.print("Choice chosen is: ");

            choice = sc.nextInt();

            if (choice != 0) {
                studentManager.processStudentChoice(choice);
            }
        } while (choice != 0);

        studentManager.triggerLogout();
    }

    public void displayFacultyMenu() {

    }

}
