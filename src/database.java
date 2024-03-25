import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class database {
    private HashMap<String, column> columns;
    private HashMap<Character, String> allAreas;

    public database() {
        this.columns = new HashMap<>();
        this.allAreas = new HashMap<>();
        allAreas.put('0',"Ang Mo Kio");
        allAreas.put('1',"Bedok");
        allAreas.put('2',"Bukit Batok");
        allAreas.put('3',"Clementi");
        allAreas.put('4',"Choa Chu Kang");
        allAreas.put('5',"Hougang");
        allAreas.put('6',"Jurong West");
        allAreas.put('7',"Punggol");
        allAreas.put('8',"Woodlands");
        allAreas.put('9',"Yishun");
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
        if (month == 0) {
            ans[1] = ("201"+year+"-10");
            ans[2] = ("201"+year+"-11");
            ans[3] = ("201"+year+"-12");
        }
        else {
            ans[1] = ("201"+year + String.format("-%02d", month));
            ans[2] = ("201"+year + String.format("-%02d", month+1));
            ans[3] = ("201"+year + String.format("-%02d", month+2));
        }
        return ans;
    }

    public int getMinPrice (ArrayList<Integer> prices) {
        return Collections.min(prices);
    }

    public double getAvg (ArrayList<Integer> prices) {
        double average = prices.stream().mapToInt(val -> val).average().orElse(0.0);
        return average;
    }
    // Other methods for database operations
}