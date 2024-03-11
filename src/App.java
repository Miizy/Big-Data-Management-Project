import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        Map<String, String[]> columnData = readCSV("ResalePricesSingapore.csv");
        String[] monthColumn = columnData.get("month");
    }

    public static Map<String, String[]> readCSV(String filePath) {
        Map<String, String[]> columnData = new HashMap<>();
        int lineCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((br.readLine()) != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        lineCount--;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            String[] headers = null;
            int currentLine = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (isFirstLine) {
                    headers = values;
                    for (String header : headers) {
                        columnData.put(header, new String[lineCount]);
                    }
                    isFirstLine = false;
                } else {
                    for (int i = 0; i < values.length; i++) {
                        columnData.get(headers[i])[currentLine] = values[i];
                    }
                    currentLine++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return columnData;
    }
}
