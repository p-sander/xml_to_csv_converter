package org.pati.pati_xml_csv.component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pati.pati_xml_csv.service.ConversionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class XmlToCsvRunner implements CommandLineRunner {

    private final ConversionService conversionService;

    @Override
    public void run(String... args) throws Exception {

        String xmlFilePathBook = "src/main/resources/xmlFilesToConvertToCsv/bookstore.xml";
        String csvFilePathBook = "src/main/resources/convertedCsv/bookstore.csv";
        conversionService.convertXmlToCsv(xmlFilePathBook, csvFilePathBook);

        String xmlFilePathItems = "src/main/resources/xmlFilesToConvertToCsv/items.xml";
        String csvFilePathItems = "src/main/resources/convertedCsv/items.csv";
        conversionService.convertXmlToCsv(xmlFilePathItems, csvFilePathItems);

        String xmlFilePathDevices = "src/main/resources/xmlFilesToConvertToCsv/devices.xml";
        String csvFilePathDevices = "src/main/resources/convertedCsv/devices.csv";
        conversionService.convertXmlToCsv(xmlFilePathDevices, csvFilePathDevices);

        log.info("Conversion successful!");
    }
}
