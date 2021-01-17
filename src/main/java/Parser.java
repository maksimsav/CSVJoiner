import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.util.List;

public class Parser {

    private CSVParser csvParser;

    public Parser() {
        this(',');
    }

    public Parser(char delimiter) {
        csvParser = new CSVParserBuilder().withSeparator(delimiter).build();
    }

    public List<String []> parse(String fileName) throws Exception {
        return new CSVReaderBuilder(new FileReader(fileName)).withCSVParser(csvParser).build().readAll();
    }

}
