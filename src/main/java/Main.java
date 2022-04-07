import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "fromdata.csv";

        // to print out info from csv-file
        List<Employee> list = parseCSV(columnMapping, fileName);
        for (Employee object : list) {
            System.out.println(object);
        }

        // to convert the list to JSON file
        listToJson(list,"todata.json");
    }

    // method to parse csv-file
    private static List<Employee> parseCSV(String[] columnMapping, String fileName) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(fileName));
        ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Employee.class);
        strategy.setColumnMapping(columnMapping);
        CsvToBean<Employee> csv = new CsvToBean<>();
        return csv.parse(strategy, reader);
    }

    // method to create a JSON file and write data there
    private static void listToJson(List<Employee> list, String fileNameJson) {
        try (Writer file = new FileWriter(fileNameJson, true)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            gson.toJson(list, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}