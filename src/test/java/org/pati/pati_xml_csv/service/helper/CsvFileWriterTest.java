package org.pati.pati_xml_csv.service.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CsvFileWriterTest {

    private CsvFileWriter csvFileWriter;
    private File tempFile;

    @BeforeEach
    void setUp() throws Exception {
        csvFileWriter = new CsvFileWriter();
        tempFile = File.createTempFile("test", ".csv");
        tempFile.deleteOnExit();
    }

    @Test
    void shouldWriteDataToCsvCorrectly() throws Exception {
        Set<String> headers = new LinkedHashSet<>(Arrays.asList("id", "name", "desc"));
        Map<String, Map<String, String>> records = new LinkedHashMap<>();

        Map<String, String> record1 = new HashMap<>();
        record1.put("id", "1");
        record1.put("name", "Book");
        record1.put("desc", "Test");

        records.put("1", record1);

        csvFileWriter.writeDataToCsv(tempFile.getAbsolutePath(), headers, records);

        List<String> lines = readAllLines(tempFile);
        assertEquals("id,name,desc", lines.get(0));
        assertEquals("\"1\",\"Book\",\"Test\"", lines.get(1));
    }

    @Test
    void shouldEscapeQuotesInValues() throws Exception {
        Set<String> headers = new LinkedHashSet<>(Arrays.asList("id", "desc"));
        Map<String, Map<String, String>> records = new HashMap<>();

        Map<String, String> record = new HashMap<>();
        record.put("id", "2");
        record.put("desc", "He said \"Hello\"");

        records.put("2", record);

        csvFileWriter.writeDataToCsv(tempFile.getAbsolutePath(), headers, records);
        List<String> lines = readAllLines(tempFile);

        assertEquals("\"2\",\"He said \"\"Hello\"\"\"", lines.get(1));
    }

    @Test
    void shouldThrowExceptionWhenIdHeaderMissing() {
        Set<String> headers = new LinkedHashSet<>(Arrays.asList("name"));
        Map<String, Map<String, String>> records = new HashMap<>();

        assertThrows(IllegalStateException.class, () -> {
            csvFileWriter.writeDataToCsv(tempFile.getAbsolutePath(), headers, records);
        });
    }

    private List<String> readAllLines(File file) throws Exception {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String l;
            while ((l = reader.readLine()) != null) {
                lines.add(l);
            }
        }
        return lines;
    }
}
