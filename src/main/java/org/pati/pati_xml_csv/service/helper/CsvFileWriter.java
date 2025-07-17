package org.pati.pati_xml_csv.service.helper;


import org.pati.pati_xml_csv.exceptions.WriteToCsvException;

import lombok.extern.slf4j.Slf4j;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


@Slf4j
public class CsvFileWriter {
    public void writeDataToCsv(String csvFilePath, Set<String> headers, Map<String, Map<String, String>> records) throws WriteToCsvException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            writer.write(String.join(",", headers));
            writer.newLine();

            for (Map<String, String> record : records.values()) {
                List<String> rowValues = new ArrayList<>();
                for (String header : headers) {
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

        List<String> sortedHeaders = new LinkedList<>();
        if (headers.contains("id")) {
            sortedHeaders.add("id");
        }
        for (String header : headers) {
            if (!header.equals("id")) {
                sortedHeaders.add(header);
            }
        }
        return sortedHeaders;
    }


}
