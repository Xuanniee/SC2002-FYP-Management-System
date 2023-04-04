import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FYPcoordDB {
    public FYPcoordDB() {
        String fileName = "../data/FYP coordinator.txt";
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