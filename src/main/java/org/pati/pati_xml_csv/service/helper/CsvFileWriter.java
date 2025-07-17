package org.pati.pati_xml_csv.service.helper;

import lombok.extern.slf4j.Slf4j;
import org.pati.pati_xml_csv.exceptions.WriteToCsvException;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class CsvFileWriter {
    public void writeDataToCsv(String csvFilePath, Set<String> headers, Map<String, Map<String, String>> records) throws WriteToCsvException {

        List<String> sortedHeaders = prioritizeIdHeader(headers);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            writer.write(String.join(",", sortedHeaders));
            writer.newLine();

            for (Map<String, String> record : records.values()) {
                List<String> rowValues = new ArrayList<>();
                for (String header : sortedHeaders) {
                    String value = record.getOrDefault(header, "");
                    rowValues.add("\"" + value.replace("\"", "\"\"") + "\"");
                }
                writer.write(String.join(",", rowValues));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("WriteToCsvException occurred " + csvFilePath);
            throw new WriteToCsvException("An error occurred during writing data to csv file " + csvFilePath, e);
        }
    }

    private List<String> prioritizeIdHeader(Set<String> headers) {

        validateIdExistenceInSet(headers);

        List<String> sortedHeaders = new LinkedList<>();

        if (headers.contains("id")) sortedHeaders.add("id");
        for (String header : headers) {
            if (!sortedHeaders.contains(header)) {
                sortedHeaders.add(header);
            }
        }
        return sortedHeaders;
    }

    private void validateIdExistenceInSet(Set<String> headers) {

        if (!headers.contains("id")) {
            throw new IllegalStateException("Missing 'id' header in XML data.");
        }
    }
}
