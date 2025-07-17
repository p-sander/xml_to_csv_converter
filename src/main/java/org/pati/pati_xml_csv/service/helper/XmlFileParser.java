package org.pati.pati_xml_csv.service.helper;

import lombok.extern.slf4j.Slf4j;
import org.pati.pati_xml_csv.exceptions.ParseXmlException;
import org.pati.pati_xml_csv.model.ElementInfo;

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
        return recordsById;
    }

}
