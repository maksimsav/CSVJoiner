import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class LoggerTest {
    @Test
    void testOut() {
        Logger.out("testing the logger");   // no exceptions should be
        Assert.assertTrue(true);
    }

    @Test
    void testOutList() {
         Logger.outList("testing outList method", new LinkedList<String[]>());    // no exceptions should be
         Assert.assertTrue(true);
    }
}