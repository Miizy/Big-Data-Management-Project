import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class database {
    private HashMap<String, column> columns;
    public HashMap<Character, String> allAreas;
    public HashMap<Character, String> allStats;

    public database() {
        this.columns = new HashMap<>();
        this.allAreas = new HashMap<>();
        allAreas.put('0',"ANG MO KIO");
        allAreas.put('1',"BEDOK");
        allAreas.put('2',"BUKIT BATOK");
        allAreas.put('3',"CLEMENTI");
        allAreas.put('4',"CHOA CHU KANG");
        allAreas.put('5',"HOUGANG");
        allAreas.put('6',"JURONG WEST");
        allAreas.put('7',"PUNGGOL");
        allAreas.put('8',"WOODLANDS");
        allAreas.put('9',"YISHUN");
        this.allStats = new HashMap<>();
        allStats.put('1',"Minimum Area");
        allStats.put('2',"Minimum Price");
        allStats.put('3',"Average Area");
        allStats.put('4',"Average Price");
        allStats.put('5',"Standard Deviation of Area");
        allStats.put('6',"Standard Deviation of Price");
    }

    public void addColumn(String columnName, column col) {
        columns.put(columnName, col);
    }

    public String[] getQuery(String query) {
        String[] ans = new String[4];
        ans[0] = allAreas.get(query.charAt(0));
        int month = Character.getNumericValue(query.charAt(1));
        Character year = query.charAt(2);
        String startYear;
        if (year <= '3') {
            startYear = "202";
        } else {
            startYear = "201";
        }
        if (month == 0) {
            ans[1] = (startYear+year+"-10");
            ans[2] = (startYear+year+"-11");
            ans[3] = (startYear+year+"-12");
        }
        else {
            ans[1] = (startYear+year + String.format("-%02d", month));
            ans[2] = (startYear+year + String.format("-%02d", month+1));
            ans[3] = (startYear+year + String.format("-%02d", month+2));
        }
        return ans;
    }

    public String[] calculateStatistics(String query, Character choice) {
        String[] queryDetails = getQuery(query);
        String queryTown = queryDetails[0];
        String monthStart = queryDetails[1];
        String monthEnd = queryDetails[3];
        
        column<String> months = (column<String>)columns.get("month");
        column<String> towns = (column<String>)columns.get("town");
        column<Double> floorAreaSqm = (column<Double>)columns.get("floorAreaSqm");
        column<Double> resalePrice = (column<Double>)columns.get("resalePrice");
        
        ArrayList<Integer> validIndices = new ArrayList<>();
        for (int i = 0; i < months.getSize(); i++) {
            String month = months.getValue(i);
            String town = towns.getValue(i);
            if ((month.compareTo(monthStart) >= 0) && (month.compareTo(monthEnd) <= 0) && town.equals(queryTown)) {
                validIndices.add(i);
            }
        }
        
        // Calculate statistics for the filtered indices
        ArrayList<Double> areaList = floorAreaSqm.getValues(validIndices);
        ArrayList<Double> priceList = resalePrice.getValues(validIndices);

        double stat = 0;
        String statFormatted = "Empty qualified entries";
        if(!validIndices.isEmpty()){
            switch (choice) {
                case '1': // minimum area
                    stat = Collections.min(areaList);
                    break;
                case '2': // minimum price
                    stat = Collections.min(priceList);
                    break;
                case '3': // avg area
                    stat = areaList.stream().mapToDouble(val -> val).average().orElse(0.0);
                    break;
                case '4': // avg price
                    stat = priceList.stream().mapToDouble(val -> val).average().orElse(0.0);
                    break;
                case '5': // std dev area
                    stat = calculateStandardDeviation(areaList, areaList.stream().mapToDouble(val -> val).average().orElse(0.0));
                    break;
                case '6': // std dev price
                    stat = calculateStandardDeviation(priceList, priceList.stream().mapToDouble(val -> val).average().orElse(0.0));
                    break;
            }
            statFormatted = String.format("%.2f", stat);
        }

        String[] ans = new String[]{
                monthStart.substring(0,4),
                monthStart.substring(5,7),
                queryTown,
                allStats.get(choice),
                statFormatted
        };

        return ans;
    }

    private double calculateStandardDeviation(ArrayList<Double> list, double mean) {
        double temp = 0;
        for (double a : list)
            temp += (mean - a) * (mean - a);
        return Math.sqrt(temp / list.size());
    }

    public void readCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            column<String> month = new column<>();
            column<String> town = new column<>();
            column<Double> floorAreaSqm = new column<>();
            column<Double> resalePrice = new column<>();

            String line;
            br.readLine(); // skip header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                month.addValue(values[0]);
                town.addValue(values[1]);
                floorAreaSqm.addValue(Double.parseDouble(values[6]));
                resalePrice.addValue(Double.parseDouble(values[9]));
            }
            addColumn("month", month);
            addColumn("town", town);
            addColumn("floorAreaSqm", floorAreaSqm);
            addColumn("resalePrice", resalePrice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}