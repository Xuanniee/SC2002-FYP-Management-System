package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Entities.ProjectAllocation;

public class ProjectAllocationDB extends Database {
    private String filePath = String.join("", super.directory, "project_allocation_list.txt");

    private File file;

    private ArrayList<ProjectAllocation> projectAllocationList = new ArrayList<ProjectAllocation>();

    public ProjectAllocationDB() {
        this.file = new File(filePath);
        this.readFile();
    }

    public void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String projectAllocation = br.readLine();
            String supervisorID, projectID, studentID;
            String[] projectAllocationData;

            while (projectAllocation != null) {
                projectAllocationData = projectAllocation.split(super.delimiter);
                studentID = projectAllocationData[0];
                projectID = projectAllocationData[1];
                supervisorID = projectAllocationData[2];
                projectAllocationList.add(new ProjectAllocation(studentID, projectID, supervisorID));
                projectAllocation = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateFile() {

    }

    public void addAllocation(ProjectAllocation pa) {
        projectAllocationList.add(pa);
    }

    public void removeAllocation(ProjectAllocation pa) {
        projectAllocationList.remove(pa);
    }

    public ArrayList<ProjectAllocation> getAllocationList() {
        return projectAllocationList;
    }
}