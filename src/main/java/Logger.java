import java.util.Objects;

public class Logger {
    public static void out(String format, Object ... args) {
        format += "\n";
        System.out.printf(format, args);
    }
}
