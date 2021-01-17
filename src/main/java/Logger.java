import java.util.Arrays;
import java.util.List;

/**
 * Created by Maksym Savchenko on 1/16/21.
 */

public class Logger {
    public static void out(String format, Object ... args) {
        format += "\n";
        System.out.printf(format, args);
    }

    public static void outList(String message, List<String[]> list) {
        System.out.println(message);
        list.forEach(row -> System.out.println(Arrays.toString(row)));
        System.out.println();
    }
}
