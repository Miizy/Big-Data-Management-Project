import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class database {
    private HashMap<String, column> columns;
    public HashMap<Character, String> allAreas;

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
    }

    public void addColumn(String columnName, column col) {
        columns.put(columnName, col);
    }

    public column getColumn(String columnName) {
        return columns.get(columnName);
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

    public Double getMinPrice (ArrayList<Double> prices) {
        return Collections.min(prices);
    }

    public double getAvg (ArrayList<Double> prices) {
        double average = prices.stream().mapToDouble(val -> val).average().orElse(0.0);
        return average;
    }
    // Other methods for database operations

    public void calculateStatistics(String query) {
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

        double minArea = Collections.min(areaList);
        double minPrice = Collections.min(priceList);
        double avgArea = areaList.stream().mapToDouble(val -> val).average().orElse(0.0);
        double avgPrice = priceList.stream().mapToDouble(val -> val).average().orElse(0.0);
        double stdDevArea = calculateStandardDeviation(areaList, avgArea);
        double stdDevPrice = calculateStandardDeviation(priceList, avgPrice);

        System.out.println("Statistics for " + queryTown + " from " + monthStart + " to " + monthEnd + ":");
        System.out.println("Min Floor Area: " + minArea);
        System.out.println("Min Price: " + minPrice);
        System.out.println("Average Floor Area: " + avgArea);
        System.out.println("Average Price: " + avgPrice);
        System.out.println("Standard Deviation of Floor Area: " + stdDevArea);
        System.out.println("Standard Deviation of Price: " + stdDevPrice);
    }

    private double calculateStandardDeviation(ArrayList<Double> list, double mean) {
        double temp = 0;
        for (double a : list)
            temp += (mean - a) * (mean - a);
        return Math.sqrt(temp / list.size());
    }
}