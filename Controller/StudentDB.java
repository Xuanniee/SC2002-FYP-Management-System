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

public class StudentDB extends Database {

    private String filePath = String.join("", super.directory, "student list.txt");

    private File file;

    public ArrayList<Student> students;

    public StudentDB() {
        this.file = new File(filePath);
        this.students = new ArrayList<Student>();
        this.readFile();
    }

    public StudentDB(String filePath) {
        this.file = new File(filePath);
        this.students = new ArrayList<Student>();
        this.readFile();
    }

    /**
     * Reads student data from student_list.txt
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

                // If project is assigned to student, then print project information
                if (student.getIsAssigned()) {
                    String projectTitle = student.getAssignedProject().getProjectTitle();
                    pw.println(studentName + " | " + studentEmail + " | " + projectTitle);
                } else {
                    pw.println(studentName + " | " + studentEmail);
                }
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Student findStudent(String userID) {
        // Student targetStudent;
        for (Student student : students) {
            if (student.getUserID().equalsIgnoreCase(userID)) {
                return student;
            }
        }
        return null;
    }

    public void viewStudentProfile(Student currentStudent) {
        Student targetStudent = findStudent(currentStudent.getUserID());
        targetStudent.printProfile();
    }

    public ArrayList<Student> getStudentList() {
        return students;
    }

    public void viewRegisteredProject(Student currentStudent) {
        if (!currentStudent.getIsAssigned()) {
            System.out.println("You have not registered for any projects.");
        } else {
            for (Student student : students) {
                if (student.getUserID() == currentStudent.getUserID()) {
                    Project currentProject = student.getAssignedProject();
                    currentProject.printProjectDetails();
                }
            }
        }
    }
}
