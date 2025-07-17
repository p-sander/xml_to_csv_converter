package org.pati.pati_xml_csv.service;

import org.junit.jupiter.api.Test;
import org.pati.pati_xml_csv.service.helper.CsvFileWriter;
import org.pati.pati_xml_csv.service.helper.XmlFileParser;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ConversionServiceTest {

    static class FakeCsvFileWriter extends CsvFileWriter {
        String receivedCsvPath;
        Set<String> receivedHeaders;
        Map<String, Map<String, String>> receivedRecords;

        @Override
        public void writeDataToCsv(String csvFilePath, Set<String> headers, Map<String, Map<String, String>> recordsById) {
            this.receivedCsvPath = csvFilePath;
            this.receivedHeaders = new LinkedHashSet<>(headers);
            this.receivedRecords = new HashMap<>(recordsById);
        }
    }

    static class FakeXmlFileParser extends XmlFileParser {
        private final Map<String, Map<String, String>> recordsToReturn;
        private final Set<String> headersToAdd;
        private boolean simulateFailure = false;

        public FakeXmlFileParser(Map<String, Map<String, String>> recordsToReturn, Set<String> headersToAdd) {
            super(null, null);
            this.recordsToReturn = recordsToReturn;
            this.headersToAdd = headersToAdd;
        }

        public void simulateFailure(boolean value) {
            this.simulateFailure = value;
        }

        @Override
        public Map<String, Map<String, String>> parseXmlFile(String xmlFilePath, Set<String> headers) {
            if (simulateFailure) {
                throw new RuntimeException("Simulated wrapper", new IOException("Simulated IO"));
            }
            headers.addAll(headersToAdd);
            return recordsToReturn;
        }

    }

    @Test
    void shouldConvertXmlToCsvCorrectly() {
        // Arrange
        Set<String> expectedHeaders = new LinkedHashSet<>(Arrays.asList("id", "name"));
        Map<String, Map<String, String>> expectedRecords = new LinkedHashMap<>();
        expectedRecords.put("1", Map.of("id", "1", "name", "Alice"));

        FakeCsvFileWriter fakeWriter = new FakeCsvFileWriter();
        FakeXmlFileParser fakeParser = new FakeXmlFileParser(expectedRecords, expectedHeaders);

        ConversionService service = new ConversionService(fakeWriter, fakeParser);

        // Act
        service.convertXmlToCsv("input.xml", "output.csv");

        // Assert
        assertEquals("output.csv", fakeWriter.receivedCsvPath);
        assertEquals(expectedHeaders, fakeWriter.receivedHeaders);
        assertEquals(expectedRecords, fakeWriter.receivedRecords);
    }

}
