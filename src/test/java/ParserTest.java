import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    @Test
    void testParser() throws Exception {

        File tempFile = File.createTempFile("csvjoiner-", ".csv");
        tempFile.deleteOnExit();

        PrintWriter printWriter = new PrintWriter(tempFile);
        printWriter.printf("1,2,3\na,b,c");
        printWriter.close();

        Parser parser = new Parser();
        List<String []> content =  parser.parse(tempFile.getAbsolutePath());

        Assert.assertEquals(content.size(), 2);

        Assert.assertEquals(content.get(0).length, 3);
        Assert.assertEquals(content.get(1).length, 3);

        Assert.assertEquals(content.get(0)[0], "1");
        Assert.assertEquals(content.get(1)[2], "c");
    }
}