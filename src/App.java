import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        // Map<String, String[]> columnData = readCSV("ResalePricesSingapore.csv");
        // String[] monthColumn = columnData.get("month");

        database db = new database();
        readCSV("ResalePricesSingapore.csv", db);

        Scanner sc= new Scanner(System.in); //System.in is a standard input stream
        ArrayList<String[]> dataLines = new ArrayList<>();
        dataLines.add(new String[] {"year","month","town","category","value"});
        System.out.print("Enter matric number\n");
        String matric = sc.nextLine();
        String query = matric.substring(5,8);

        while(true) {
            System.out.print("""
                Enter statistic to calculate or 0 to stop
                1. Min area
                2. Min price
                3. Avg area
                4. Avg price
                5. Std Dev area
                6. Std Dev price
                """);
            Character stat = sc.nextLine().charAt(0);
            if(stat=='0') {break;}
            String[] query_ans = db.calculateStatistics(query, stat);
            dataLines.add(query_ans);
        }


        File csvOutputFile = new File(String.join("","ScanResult_",matric,".csv"));
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(App::convertToCSV)
                    .forEach(pw::println);
        }

    }

//    public static Map<String, String[]> readCSV(String filePath) {
//        Map<String, String[]> columnData = new HashMap<>();
//        int lineCount = 0;
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            while ((br.readLine()) != null) {
//                lineCount++;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        lineCount--;
//
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            boolean isFirstLine = true;
//            String[] headers = null;
//            int currentLine = 0;
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//                if (isFirstLine) {
//                    headers = values;
//                    for (String header : headers) {
//                        columnData.put(header, new String[lineCount]);
//                    }
//                    isFirstLine = false;
//                } else {
//                    for (int i = 0; i < values.length; i++) {
//                        columnData.get(headers[i])[currentLine] = values[i];
//                    }
//                    currentLine++;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return columnData;
//    }

    public static String convertToCSV(String[] data) {
        return Stream.of(data)
                .collect(Collectors.joining(","));
    }

    public static void readCSV(String filePath, database db) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            column<String> month = new column<>();
            column<String> town = new column<>();
            // column<String> flatType = new column<>();
            // column<String> block = new column<>();
            // column<String> streetName = new column<>();
            // column<String> storeyRange = new column<>();
            column<Double> floorAreaSqm = new column<>();
            // column<String> flatModel = new column<>();
            // column<Integer> leaseCommenceDate = new column<>();
            column<Double> resalePrice = new column<>();

            String line;
            br.readLine(); // skip header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                month.addValue(values[0]);
                town.addValue(values[1]);
                // flatType.addValue(values[2]);
                // block.addValue(values[3]);
                // streetName.addValue(values[4]);
                // storeyRange.addValue(values[5]);
                floorAreaSqm.addValue(Double.parseDouble(values[6]));
                // flatModel.addValue(values[7]);
                // leaseCommenceDate.addValue(Integer.parseInt(values[8]));
                resalePrice.addValue(Double.parseDouble(values[9]));
            }
            db.addColumn("month", month);
            db.addColumn("town", town);
            // db.addColumn("flatType", flatType);
            // db.addColumn("block", block);
            // db.addColumn("streetName", streetName);
            // db.addColumn("storeyRange", storeyRange);
            db.addColumn("floorAreaSqm", floorAreaSqm);
            // db.addColumn("flatModel", flatModel);
            // db.addColumn("leaseCommenceDate", leaseCommenceDate);
            db.addColumn("resalePrice", resalePrice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
