package org.pati.pati_xml_csv.service.helper;

import lombok.extern.slf4j.Slf4j;
import org.pati.pati_xml_csv.exceptions.ParseXmlException;
import org.pati.pati_xml_csv.model.ElementInfo;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

@Slf4j
public class XmlFileParser {
    private final XmlElementHelper elementHelper;
    private final XmlParserHelper xmlParserHelper;

    public XmlFileParser() {
        this.elementHelper = new XmlElementHelper();
        this.xmlParserHelper = new XmlParserHelper();
    }

    public Map<String, Map<String, String>> parseXmlFile(String xmlFilePath, Set<String> headers) throws ParseXmlException {
        Map<String, Map<String, String>> recordsById = new LinkedHashMap<>();
        Stack<ElementInfo> elementStack = new Stack<>();

        try (FileInputStream in = new FileInputStream(xmlFilePath)) {
            XMLStreamReader reader = xmlParserHelper.initXmlReader(in);
        } catch (IOException | XMLStreamException e) {
            log.error("ParseXmlException occurred for file: {}", xmlFilePath, e);
            throw new ParseXmlException("XML parsing error in " + xmlFilePath, e);
        }

        return recordsById;
    }

}
