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
        database db = new database();
        db.readCSV("ResalePricesSingapore.csv");

        ArrayList<String[]> dataLines = new ArrayList<>();
        dataLines.add(new String[] {"year","month","town","category","value"});

        Scanner sc= new Scanner(System.in); //System.in is a standard input stream
        String matric;
        String query = "000";
        while (true) {
            System.out.print("Enter matriculation number for data processing, c to cancel: ");
            matric = sc.nextLine().trim(); // Trim to remove any leading or trailing whitespace

            if (matric.equals("c")) {
                System.out.println("Thank you, bye bye...");
                System.exit(0); // Exit the loop and thus end the program
            }

            if (matric.length() != 9) {
                System.out.println("Invalid input, matriculation number is of length 9...");
                continue; // Skip the rest of the loop and prompt again
            }
            query = matric.substring(5, 8);
            int queryAsInt = 0;
            try {
                queryAsInt = Integer.parseInt(query);
                System.out.println("Data Processing for matriculation number : " + matric);
                System.out.println("Using query : " + query);
                // Break the loop if everything is correct
                break;
            } catch (NumberFormatException e) {
                System.out.println("The extracted query is not a valid integer: " + queryAsInt + ", please try again...");
                // Continue the loop to prompt the user again
            }
        }

        while(true) {
            System.out.print("""
                Enter statistic to calculate or 0 to stop
                1. Minimum floor area
                2. Minimum price
                3. Average floor area
                4. Average price
                5. Standard Deviation of floor area
                6. Standard Deviation of price
                """);
            Character stat = sc.nextLine().charAt(0);
            if(stat=='0') {break;}
            String[] query_ans = db.calculateStatistics(query, stat);
            dataLines.add(query_ans);
        }

        sc.close();

        File csvOutputFile = new File(String.join("","ScanResult_",matric,".csv"));
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(App::convertToCSV)
                    .forEach(pw::println);
        }

        System.out.print("Output file created");

    }

    public static String convertToCSV(String[] data) {
        return Stream.of(data)
                .collect(Collectors.joining(","));
    }
}
