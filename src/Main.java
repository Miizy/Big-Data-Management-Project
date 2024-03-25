import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Create a columnar database
        database db = new database();

        // Create columns for different data types
       column<String> months = new column<>();
       column<String> areas = new column<>();
       column<Integer> prices = new column<>();

       months.addValue("2018-07");
       months.addValue("2017-09");
       months.addValue("2019-11");
       months.addValue("2017-04");
       months.addValue("2018-10");
       months.addValue("2019-02");
       months.addValue("2018-08");
       months.addValue("2017-06");
       months.addValue("2019-12");
       months.addValue("2017-03");
       months.addValue("2018-05");
       months.addValue("2017-01");
       months.addValue("2019-07");
       months.addValue("2017-09");
       months.addValue("2018-11");
       months.addValue("2019-06");
       months.addValue("2017-02");
       months.addValue("2018-12");
       months.addValue("2018-10");
       months.addValue("2018-08");
        // Add data to columns
       areas.addValue("Bedok");
       areas.addValue("Bedok");
       areas.addValue("Bedok");
       areas.addValue("Bedok");
       areas.addValue("Bedok");
       areas.addValue("Bedok");
       areas.addValue("Clementi");
       areas.addValue("Clementi");
       areas.addValue("Clementi");
       areas.addValue("Clementi");
       areas.addValue("Hougang");
       areas.addValue("Hougang");
       areas.addValue("Hougang");
       areas.addValue("Hougang");
       areas.addValue("Hougang");
       areas.addValue("Yishun");
       areas.addValue("Yishun");
       areas.addValue("Yishun");
       areas.addValue("Yishun");
       areas.addValue("Yishun");

       prices.addValue(57);
       prices.addValue(82);
       prices.addValue(33);
       prices.addValue(15);
       prices.addValue(96);
       prices.addValue(49);
       prices.addValue(73);
       prices.addValue(25);
       prices.addValue(68);
       prices.addValue(41);
       prices.addValue(88);
       prices.addValue(12);
       prices.addValue(64);
       prices.addValue(37);
       prices.addValue(79);
       prices.addValue(55);
       prices.addValue(91);
       prices.addValue(78);
       prices.addValue(45);
       prices.addValue(70);

       String[] queries = db.getQuery("908");

//       for (int i=0; i<queries.length; i++) {
//          System.out.println(queries[i] + " ... ");
//       }
       int[] areaIndexes = areas.getIndexesSorted(queries[0]);
//       ArrayList areaIndexes = areas.getIndexesUnsorted(queries[0]);
//       for (int i=0; i<areaIndexes.length; i++) {
//          int curr = (int) areaIndexes[i];
//
//          System.out.println("area "+curr);
//       }

       ArrayList monthsIndexes = months.getMonthsIndexes(Arrays.copyOfRange(queries,1,4), areaIndexes);
//       for (int i=0; i<monthsIndexes.size(); i++) {
//          int curr = (int) monthsIndexes.get(i);
//
//          System.out.println("months "+curr);
//       }

       ArrayList<Double> priceValues = prices.getValues(monthsIndexes);

       System.out.println("Minimum price is " +
               db.getMinPrice(priceValues));

       System.out.println("Average price is " +
               db.getAvg(priceValues));

    }
}