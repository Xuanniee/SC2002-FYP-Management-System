import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StudentDB {
    public static void main(String[] args) {
        String fileName = "../data/student list.txt";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t"); // split the line by tabs
                for (String value : values) {
                    System.out.print(value + "\t");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}