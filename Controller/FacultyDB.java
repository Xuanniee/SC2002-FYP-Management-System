package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Entities.*;

public class FacultyDB extends Database {

    private String facultyFilePath = String.join("", super.directory, "faculty_list.txt");
    private String coordinatorFilePath = String.join("", super.directory, "FYP_coordinator.txt");
    private String projectFilePath = String.join("", super.directory, "rollover_project.txt");

    private File facultyFile;
    private File coordinatorFile;
    private File projectFile;

    public ArrayList<Faculty> faculties;
    public ArrayList<Project> projects;

    public FacultyDB() {
        this.facultyFile = new File(facultyFilePath);
        this.coordinatorFile = new File(coordinatorFilePath);
        this.projectFile = new File(projectFilePath);
        this.faculties = new ArrayList<Faculty>();
        this.readFile();
        this.readCoordinatorFile();
        this.projects = new ArrayList<Project>();
        this.readProjectFile();
    }

    public FacultyDB(String facultyFilePath, String coordinatorFilePath) {
        this.facultyFile = new File(facultyFilePath);
        this.coordinatorFile = new File(coordinatorFilePath);
        this.projectFile = new File(projectFilePath);
        this.faculties = new ArrayList<Faculty>();
        this.readFile();
        this.readCoordinatorFile();
        this.projects = new ArrayList<Project>();
        this.readProjectFile();
    }

    public void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(facultyFile));

            String facultyLine = br.readLine();
            facultyLine = br.readLine();
            String facultyName, facultyEmail, facultyID;
            String[] facultyData, temp;

            while (facultyLine != null) {
                facultyData = facultyLine.split(super.delimiter);
                facultyName = facultyData[0];
                facultyEmail = facultyData[1];

                temp = facultyData[1].split(super.emailDelimiter);
                facultyID = temp[0];

                faculties.add(new Faculty(facultyID, facultyName, facultyEmail));

                facultyLine = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readCoordinatorFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(coordinatorFile));

            String coordinatorLine = br.readLine();
            coordinatorLine = br.readLine();
            String coordinatorName;
            String[] coordinatorData;

            while (coordinatorLine != null) {
                coordinatorData = coordinatorLine.split(super.delimiter);
                coordinatorName = coordinatorData[0];
                for (Faculty faculty : faculties) {
                    if (faculty.getUserName().equalsIgnoreCase(coordinatorName)) {
                        faculty.setIsCoordinator(true);
                    }
                }
                coordinatorLine = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readProjectFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(projectFile));

            String projectLine = br.readLine();
            projectLine = br.readLine();
            String facultyName, projectTitle;
            String[] projectData;

            while (projectLine != null) {
                projectData = projectLine.split(super.delimiter);
                facultyName = projectData[0];
                projectTitle = projectData[1];

                // Add project to ArrayList of Projects
                projects.add(new Project(projectTitle));

                for (Faculty faculty : faculties) {
                    if (faculty.getUserName().equalsIgnoreCase(facultyName)) {
                        faculty.createProject(projectTitle);
                        break;
                    }
                }
                projectLine = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Faculty> getAllFaculties() {
        return this.faculties;
    }

    public ArrayList<Project> getAllProjects() {
        return this.projects;
    }

    public void updateFile() {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(facultyFile, false));
            PrintWriter pw = new PrintWriter(bf);
            for (Faculty faculty : faculties) {
                String facultyName = faculty.getUserName();
                String facultyEmail = faculty.getUserEmail();
                pw.println(facultyName + "," + facultyEmail);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
