package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Entities.*;

public class FacultyDB {

    private ArrayList<Supervisor> supervisors = new ArrayList<Supervisor>();

    // When this class object is created, automatically read from text file and
    // store into arraylist of supervisor objects
    public FacultyDB() {
        String fileName = "./Data/faculty_list.txt";
        String line;
        boolean isFirstLine = true;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // skip the first line
                }
                String[] values = line.split("\t"); // split the line by tabs
                // System.out.println(values[0]);
                // System.out.println(values[1]);
                int index = values[1].indexOf('@');
                // System.out.println(index);
                supervisors.add(new Supervisor(values[1].substring(0, index), values[0], values[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* For testing purposes */
    public void viewDB() {
        for (int i = 0; i < supervisors.size(); i++) {
            supervisors.get(i).viewDetails();
        }
    }

    /**
     * Returns the Target Supervisor so long as they are present
     * 
     * @param userID
     * @return
     */
    public Supervisor findSupervisor(String userID) {
        int targetIndex = -1;
        for (int i = 0; i < supervisors.size(); i += 1) {
            if (supervisors.get(i).getUserID().equalsIgnoreCase(userID)) {
                targetIndex = i;
            }
        }

        if (targetIndex == -1) {
            return null;
        }

        return supervisors.get(targetIndex);
    }

    /**
     * Get Supervisor List
     */
    public ArrayList<Supervisor> getSupervisorList() {
        return supervisors;
    }
}
