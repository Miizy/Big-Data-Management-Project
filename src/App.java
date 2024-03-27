import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        // Map<String, String[]> columnData = readCSV("ResalePricesSingapore.csv");
        // String[] monthColumn = columnData.get("month");

        database db = new database();
        readCSV("ResalePricesSingapore.csv", db);
        String[] queries = db.getQuery("875");
        // int[] areaIndexes = db.getColumn("town").getIndexesSorted(queries[0]);
        // ArrayList monthsIndexes = db.getColumn("month").getMonthsIndexes(Arrays.copyOfRange(queries, 1, 4), areaIndexes);
        // ArrayList<Double> priceValues = db.getColumn("resalePrice").getValues(monthsIndexes);

        System.out.println("Statistic for resale flats in " +
                queries[0] + " from " + queries[1] + " to " +queries[3]);

        // System.out.println("Minimum price is " +
        //         db.getMinPrice(priceValues));

        // System.out.println("Average price is " +
        //         db.getAvg(priceValues));
        db.calculateStatistics("875");
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
