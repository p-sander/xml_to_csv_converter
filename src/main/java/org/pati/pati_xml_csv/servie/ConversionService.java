package org.pati.pati_xml_csv.servie;

import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ConversionService {

    public void convertXmlToCsvStax(String xmlFilePath, String csvFilePath) {

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();

        Set<String> headers = new LinkedHashSet<>();
        List<String> csvRows = new ArrayList<>();
        Map<String, String> currentElementData = new HashMap<>();

        try (InputStream in = Files.newInputStream(Paths.get(xmlFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {

            XMLStreamReader reader = inputFactory.createXMLStreamReader(in);
            StringBuffer currentCsvRow = new StringBuffer();

            while (reader.hasNext()) {
                int eventType = reader.next();
                String currentElement = null;

                switch (eventType) {
                    case XMLStreamConstants.START_ELEMENT:
                        currentElement = reader.getLocalName();
                        if (currentElement != null) {

                            headers.add(currentElement);

                            for (int i = 0; i < reader.getAttributeCount(); i++) {
                                String attributeName = reader.getAttributeLocalName(i);
                                String attributeValue = reader.getAttributeValue(i);
                                headers.add(attributeName);
                                currentElementData.put(attributeName, attributeValue);
                            }
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        if (currentElement != null && !reader.isWhiteSpace()) {
                            String text = reader.getText().trim();
                            if (currentElementData.containsKey(currentElement)) {
                                String updatedValue = currentElementData.get(currentElement) + text;
                                currentElementData.put(currentElement, updatedValue);
                            } else {
                                currentElementData.put(currentElement, text);
                            }
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        currentElement = reader.getLocalName();
                        if (currentElement != null) {
                            for (String header : headers) {
                                if (currentElementData.containsKey(header)) {
                                    currentCsvRow.append(currentElementData.get(header)).append(",");
                                } else {
                                    currentCsvRow.append(",");
                                }
                            }
                            currentCsvRow.setLength(currentCsvRow.length() - 1);
                            csvRows.add(currentCsvRow.toString());
                            currentCsvRow.setLength(0);
                            currentElementData.clear();
                        }
                        break;
                }
            }

            writer.write(String.join(",", headers) + "/n");
            for (String row : csvRows) {
                writer.write(row + "/n");
            }

        } catch (IOException | XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }
}