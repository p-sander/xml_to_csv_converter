package org.pati.pati_xml_csv.service;
// src/main/java/org/pati/pati_xml_csv/service/ConversionService.java
// src/main/java/org/pati/pati_xml_csv/service/ConversionService.java

import lombok.extern.slf4j.Slf4j;
import org.pati.pati_xml_csv.service.helper.CsvFileWriter;
import org.pati.pati_xml_csv.service.helper.XmlFileParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


@Service
@Slf4j
public class ConversionService {
    private final CsvFileWriter csvFileWriter;
    private final XmlFileParser xmlParser;

    public ConversionService() {
        this.csvFileWriter = new CsvFileWriter();
        this.xmlParser = new XmlFileParser();
    }

    public void convertXmlToCsv(String xmlFilePath, String csvFilePath) {
        Set<String> headers = new LinkedHashSet<>();
        Map<String, Map<String, String>> recordsById;

        try {
            recordsById = xmlParser.parseXmlFile(xmlFilePath, headers);
            csvFileWriter.writeDataToCsv(csvFilePath, headers, recordsById);
        } catch (IOException e) {
            log.error("Error", e);
            throw new RuntimeException("Conversion failed", e);
        }
    }

}
