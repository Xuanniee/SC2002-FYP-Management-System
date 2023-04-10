package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Entities.FYPCoordinator;

public class FYPcoordDB {
    private ArrayList<FYPCoordinator> fypCoordinators = new ArrayList<FYPCoordinator>();

    // Reading and Saving Details of FYP Coordinator
    public FYPcoordDB() {
        String fileName = "./Data/FYP coordinator.txt";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t"); // split the line by tabs

                int indexUserId = values[1].indexOf("@");
                fypCoordinators.add(new FYPCoordinator(values[1].substring(0, indexUserId), values[0], values[1]));
                for (String value : values) {
                    System.out.print(value + "\t");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FYPCoordinator findFypCoordinator(String userID) {
        FYPCoordinator targetFypCoordinator = fypCoordinators.get(0);
        for (FYPCoordinator fypCoordinator : fypCoordinators) {
            if (fypCoordinator.getFYPCoordID().equalsIgnoreCase(userID)) {
                targetFypCoordinator = fypCoordinator;
            }
        }
        return targetFypCoordinator;
    }

    public ArrayList<FYPCoordinator> getFypCoordinatorsList() {
        return fypCoordinators;
    }
}