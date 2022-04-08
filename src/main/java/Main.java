import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";

        // to print out info from csv-file
        List<Employee> list = parseCSV(columnMapping, fileName);
        for (Employee object : list) {
            System.out.println(object);
        }
        
        // to convert the list to JSON file
        String json = "data2.json";
        listToJson(list, json);
    }

    // method to parse csv-file
    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> employeeList = null;
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();
            employeeList = csv.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    // method to create a JSON file and write data there
    private static void listToJson(List<Employee> list, String tojson) {
        try (Writer file = new FileWriter(tojson, true)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
                gson.toJson(list, file);
                file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}