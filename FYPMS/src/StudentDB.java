import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StudentDB {
    private ArrayList<Student> students = new ArrayList<Student>();

    //When this class object is created, automatically read from text file and store into arraylist of student objects
    public StudentDB(){
        String fileName = "../data/student list.txt";
        String line;
        boolean isFirstLine = true;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // skip the first line
                }
                String[] values = line.split("\t"); // split the line by tabs
                int index = values[1].indexOf('@');
                students.add( new Student(values[1].substring(0,index),values[0],values[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*For testing purposes */
    public void viewDB(){
        for(int i = 0; i < students.size(); i++){
            students.get(i).viewDetails();
        }
    }

    public ArrayList<Student> getStudentList() {
        return students;
    }

    public Student getStudent(String userID) {
        int targetIndex = -1;
        for (int i = 0; i < students.size(); i += 1) {
            if (students.get(i).userID.equals(userID)) {
                targetIndex = i;
            }
        }

        if (targetIndex == -1) {
            return null;
        }
        
        return students.get(targetIndex);
    }
}