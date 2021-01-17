import java.io.File;
import java.util.*;

/**
 * Created by Maksym Savchenko on 1/16/21.
 */

public class CSVJoiner {

    public static void  main(String [] args) {

        Logger.out("CSV Joiner\n");

        // The program works with mandatory two arguments
        // See printHelp method for details
        if (args.length != 2) {
            printHelp();
            System.exit(1);
        }

        // Getting input command line arguments like names of files with csv data
        final String csvWorkersFileName = args[0];
        final String csvDepartmentsFileName = args[1];

        // Check that both files exist. Terminate the program for case if they aren't exist.
        Arrays.asList(csvWorkersFileName, csvDepartmentsFileName).forEach(name -> {
            if (!new File(name).exists()) {
                System.out.println("File '" + name + "' doesn't exist!");
                printHelp();
                System.exit(1);
            }
        });

        // Creating CSV parser
        Parser parser = new Parser(';');

        // Creating lists to save the parsed results
        List<String[]> listWorkers = new LinkedList<>();
        List<String[]> listDepartments = new LinkedList<>();

        // Parsing the files
        // Terminating the program in case of exceptions
        try {
            listWorkers = parser.parse(csvWorkersFileName);
            listDepartments = parser.parse(csvDepartmentsFileName);
        } catch (Exception e) {
            System.out.println("Exception: " + e.toString());
            System.exit(1);
        }

        // Show the content of parsed files as-is
        Logger.outList("csv workers file content:", listWorkers);
        Logger.outList("csv departments file content:", listDepartments);

        // Removing first lines from each file.
        // These lines have description of fields and this's not real line of data
        if (listWorkers.size() > 0) listWorkers.remove(0);
        if (listDepartments.size() > 0) listDepartments.remove(0);

        // Testing the parsed tables to check the correct amount of elements in rows:
        // 4 elements for Workers table and 2 for Departments.
        // Terminating the program for case of detection incorrect numbers of elements.
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

        // Converting the Department table to HashMap to simplify and speed-up joining process
        HashMap<String, String> departmentTable = new HashMap<>();
        listDepartments.forEach(row -> departmentTable.put(row[0], row[1]));

        // Joining
        List<String[]> listJoined = new LinkedList<>();
        Set<String> setDepartmentKeys = departmentTable.keySet();
        listWorkers.forEach(row -> {
            String[] rowDepartment = { row[0], row[1], row[2], setDepartmentKeys.contains(row[3]) ? departmentTable.get(row[3]) : "Unknown" };
            listJoined.add(rowDepartment);
        });

        // Show results
        Logger.outList("joined content:\n[id, name, surname, department]", listJoined);

        System.exit(0);
    }

    public static void printHelp() {
        System.out.println("Please use this tool with correct set of arguments:");
        System.out.println("  CSVJoiner <csv workers file> <csv departments file>");
    }
}
