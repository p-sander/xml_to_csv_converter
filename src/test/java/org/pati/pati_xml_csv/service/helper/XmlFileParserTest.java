package org.pati.pati_xml_csv.service.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class XmlFileParserTest {

    private XmlFileParser parser;
    private Set<String> headers;

    @BeforeEach
    void setUp() {
        parser = new XmlFileParser(new XmlElementHelper(), new XmlParserHelper());
        headers = new LinkedHashSet<>();
    }

    @Test
    void shouldParseSimpleXmlFile() throws Exception {
        String xmlContent = "<books><book id=\"1\"><name>Java</name></book></books>";
        String path = "test-books.xml";

        try (FileWriter writer = new FileWriter(path)) {
            writer.write(xmlContent);
        }

        Map<String, Map<String, String>> records = parser.parseXmlFile(path, headers);

        assertEquals(1, records.size());
        assertTrue(records.containsKey("1"));
        assertEquals("Java", records.get("1").get("name"));
        assertTrue(headers.contains("name"));
        assertTrue(headers.contains("id"));

        new java.io.File(path).delete();
    }
}
