//import com.opencsv.CSVReaderBuilder;
//import com.opencsv.CSVParser;
//import com.opencsv.CSVParserBuilder;

import java.io.File;
//import java.io.FileReader;
import java.util.*;

public class CSVJoiner {

    public final static char CSV_DELIMITER = ';';

    public static void  main(String [] args) {

        Logger.out("CSV Joiner");

        if (args.length != 2) {
            printHelp();
            System.exit(1);
        }

        final String csvWorkersFileName = args[0];
        final String csvDepartmentsFileName = args[1];

        Arrays.asList(csvWorkersFileName, csvDepartmentsFileName).forEach(name -> {
            if (!new File(name).exists()) {
                System.out.println("File '" + name + "' doesn't exist!");
                printHelp();
                System.exit(1);
            }
        });

        Parser parser = new Parser(';');

        try {
            List<String[]> listWorkers = parser.parse(csvWorkersFileName);
            List<String[]> listDepartments = parser.parse(csvDepartmentsFileName);
        } catch (Exception e) {
            System.out.println("Exception: " + e.toString());
            System.exit(1);
        }

            System.out.println("csv workers file content:");
            listWorkers.forEach(row -> System.out.println(Arrays.toString(row)));
            System.out.println();

            System.out.println("csv departments file content:");
            listDepartments.forEach(row -> System.out.println(Arrays.toString(row)));
            System.out.println();

            if (listWorkers.size() > 0) listWorkers.remove(0);
            if (listDepartments.size() > 0) listDepartments.remove(0);

            Arrays.asList(Arrays.asList(listWorkers, 4, csvWorkersFileName), Arrays.asList(listDepartments, 2, csvDepartmentsFileName)).forEach(element -> {
                List<String[]> csvFileContent = (List<String[]>)element.get(0);
                int amountOfFields = (int)element.get(1);
                String fileName = (String)element.get(2);
                csvFileContent.forEach(row -> {
                    if (row.length != amountOfFields) {
                        System.out.printf("Found a line in file '%s' with amount of elements isn't equal to %d: %s", fileName, amountOfFields, Arrays.toString(row));
                        System.exit(1);
                    }
                });
            });

            HashMap<String, String> departmentTable = new HashMap<>();
            listDepartments.forEach(row -> departmentTable.put(row[0], row[1]));

            List<String[]> listJoined = new LinkedList<>();
            Set<String> setDerparmentKeys = departmentTable.keySet();
            listWorkers.forEach(row -> {
                String[] rowDepartment = { row[0], row[1], row[2], setDerparmentKeys.contains(row[3]) ? departmentTable.get(row[3]) : "Unknown" };
                listJoined.add(rowDepartment);
            });

            System.out.println("joined content:");
            System.out.println("[id, name, surname, department]");
            listJoined.forEach(row -> System.out.println(Arrays.toString(row)));
            System.out.println();

        } catch (Exception e) {
            System.out.println("Exception: " + e.toString());
            System.exit(1);
        }

        System.exit(0);
    }

    public static void printHelp() {
        System.out.println("Please use this tool with correct set of arguments:");
        System.out.println("  CSVJoiner <csv workers file> <csv department file>");
    }


}
