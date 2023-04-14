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

public class FYPcoordDB extends Database {

    private String filePath = String.join("", super.directory, "FYP coordinator.txt");

    private File file;

    private ArrayList<FYPCoordinator> fypCoordinators = new ArrayList<FYPCoordinator>();

    public FYPcoordDB() {
        this.file = new File(filePath);
        this.fypCoordinators = new ArrayList<FYPCoordinator>();
        this.readFile();
    }

    public FYPcoordDB(String filePath) {
        this.file = new File(filePath);
        this.fypCoordinators = new ArrayList<FYPCoordinator>();
        this.readFile();
    }

    /**
     * Reads FYPCoordinator data from FYP coordinator.txt
     */
    public void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String coordinatorLine = br.readLine();
            coordinatorLine = br.readLine();
            String coordinatorName, coordinatorEmail, coordinatorID;
            String[] coordinatorData, temp;

            while (coordinatorLine != null) {
                coordinatorData = coordinatorLine.split(super.delimiter);
                coordinatorName = coordinatorData[0];
                coordinatorEmail = coordinatorData[1];

                temp = coordinatorData[1].split(super.emailDelimiter);
                coordinatorID = temp[0];

                fypCoordinators.add(new FYPCoordinator(coordinatorID, coordinatorName, coordinatorEmail));

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
            for (FYPCoordinator currentCoordinator : fypCoordinators) {
                String coordinatorName = currentCoordinator.getUserName();
                String coordinatorEmail = currentCoordinator.getUserEmail();

                pw.println(coordinatorName + delimiter + coordinatorEmail);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FYPCoordinator findFypCoordinator(String userID) {
        FYPCoordinator targetFypCoordinator = fypCoordinators.get(0);
        for (FYPCoordinator fypCoordinator : fypCoordinators) {
            if (fypCoordinator.getUserID().equalsIgnoreCase(userID)) {
                targetFypCoordinator = fypCoordinator;
            }
        }
        return targetFypCoordinator;
    }

    public ArrayList<FYPCoordinator> getFypCoordinatorsList() {
        return fypCoordinators;
    }
}