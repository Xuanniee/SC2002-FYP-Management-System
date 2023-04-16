package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Entities.FYPCoordinator;

/**
 * Represents a Database of all FYP Coordinators in FYP Management System.
 * 
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class FYPcoordDB extends Database {
    /**
     * Represents the file path of the Database of FYP Coordinator in FYPMS.
     */
    private String filePath = String.join("", super.directory, "FYP coordinator.txt");

    /**
     * Represents the file containing the database of all FYP Coordinator
     */
    private File file;

    /**
     * ArrayList containing all the FYP Coordinator in FYPMS
     */
    private ArrayList<FYPCoordinator> fypCoordinators = new ArrayList<FYPCoordinator>();

    /**
     * Creates a FYPCoordDB Object that stores all the FYP Coordinator when FYPMS is
     * first initalised.
     */
    public FYPcoordDB() {
        this.file = new File(filePath);
        this.fypCoordinators = new ArrayList<FYPCoordinator>();
        this.readFile();
    }

    /**
     * Reads FYPCoordinator data from the text file.
     */
    public void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String coordinatorLine = br.readLine();
            coordinatorLine = br.readLine();
            String coordinatorName, coordinatorEmail, coordinatorID, coordinatorPassword;
            String[] coordinatorData, temp;

            while (coordinatorLine != null) {
                coordinatorData = coordinatorLine.split(super.delimiter);
                coordinatorName = coordinatorData[0];
                coordinatorEmail = coordinatorData[1];
                coordinatorPassword = coordinatorData[2];

                temp = coordinatorData[1].split(super.emailDelimiter);
                coordinatorID = temp[0];

                fypCoordinators.add(new FYPCoordinator(coordinatorID, coordinatorName, coordinatorEmail, coordinatorPassword));

                coordinatorLine = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes updated FYPCoordinator data to FYP Coordinator.txt
     */
    public void updateFile() {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(file, false));
            PrintWriter pw = new PrintWriter(bf);
            // Write Headers
            pw.println("Name" + "\t" + "Email" + "\t" + "Password");
            for (FYPCoordinator currentCoordinator : fypCoordinators) {
                String coordinatorName = currentCoordinator.getUserName();
                String coordinatorEmail = currentCoordinator.getUserEmail();
                String coordinatorPassword = currentCoordinator.getPassword();

                pw.println(coordinatorName + delimiter + coordinatorEmail + delimiter + coordinatorPassword);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Search and Retrieves a particular FYP Coordinator based on their User ID
     * 
     * @param userID FYP Coordinator User ID
     * @return Target FYP Coordinator
     */
    public FYPCoordinator findFypCoordinator(String userID) {
        FYPCoordinator targetFypCoordinator = fypCoordinators.get(0);
        for (FYPCoordinator fypCoordinator : fypCoordinators) {
            if (fypCoordinator.getUserID().equalsIgnoreCase(userID)) {
                targetFypCoordinator = fypCoordinator;
            }
        }
        return targetFypCoordinator;
    }

    /**
     * Retrieves the ArrayList of all FYP Coordinators in FYPMS.
     * 
     * @return ArrayList<FYPCoordinator>
     */
    public ArrayList<FYPCoordinator> getFypCoordinatorsList() {
        return fypCoordinators;
    }
}