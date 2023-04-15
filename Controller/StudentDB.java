package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Entities.Student;
import Entities.Project;

/**
 * Represents a Database of all students in the FYP Management System.
 * 
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public class StudentDB extends Database {
    /**
     * Represents the file path of the Database of Student Users in FYPMS.
     */
    private String filePath = String.join("", super.directory, "student_list.txt");

    /**
     * Represents the database file of all the Student Users
     */
    private File file;

    /**
     * Array List of Student Users in FYPMS.
     */
    public ArrayList<Student> students;

    /**
     * Constructor
     * Creates a Database storing all the Students in FYPMS when the Application is
     * first initialised.
     */
    public StudentDB() {
        this.file = new File(filePath);
        this.students = new ArrayList<Student>();
        this.readFile();
    }

    /**
     * Reads in Student Information from provided file.
     */
    public void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String studentLine = br.readLine();
            studentLine = br.readLine();
            String studentName, studentEmail, studentID;
            String[] studentData, temp;

            while (studentLine != null) {
                studentData = studentLine.split(super.delimiter);
                studentName = studentData[0];
                studentEmail = studentData[1];

                temp = studentData[1].split(super.emailDelimiter);
                studentID = temp[0];

                students.add(new Student(studentID, studentName, studentEmail));

                studentLine = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes updated student data to student_list.txt
     */
    public void updateFile() {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(file, false));
            PrintWriter pw = new PrintWriter(bf);
            for (Student student : students) {
                String studentName = student.getUserName();
                String studentEmail = student.getUserEmail();

                pw.println(studentName + delimiter + studentEmail);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches and Retrieves a particular Student by the provided UserID
     * 
     * @param userID Student's UserID
     * @return Searched Student
     */
    public Student findStudent(String userID) {
        // Student targetStudent;
        for (Student student : students) {
            if (student.getUserID().equalsIgnoreCase(userID)) {
                return student;
            }
        }
        return null;
    }

    /**
     * Prints the Profile particulars of a given Student.
     * 
     * @param currentStudent Target Student
     */
    public void viewStudentProfile(Student currentStudent) {
        Student targetStudent = findStudent(currentStudent.getUserID());
        targetStudent.printProfile();
    }

    /**
     * Retrieves the ArrayList of all the Student Users in FYPMS.
     * 
     * @return Arraylist of Student Users
     */
    public ArrayList<Student> getStudentList() {
        return students;
    }

    /**
     * Prints the Project Details of the Allocated Project to a particular Student
     * User.
     * 
     * @param currentStudent Target Student
     */
    public void viewRegisteredProject(Student currentStudent) {
        for (Student student : students) {
            if (student.getUserID() == currentStudent.getUserID()) {
                Project currentProject = student.getAssignedProject();
                currentProject.printProjectDetails();
            }
        }
    }
}
