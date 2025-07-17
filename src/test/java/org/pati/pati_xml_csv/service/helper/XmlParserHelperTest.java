package org.pati.pati_xml_csv.service.helper;

import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class XmlParserHelperTest {

    @Test
    void testInitXmlReaderReturnsValidReader() throws IOException, XMLStreamException {
        XmlParserHelper helper = new XmlParserHelper();
        try (FileInputStream fis = new FileInputStream("src/test/resources/test.xml")) {

            XMLStreamReader reader = helper.initXmlReader(fis);

            assertNotNull(reader, "XMLStreamReader should not be null");
            assertEquals(XMLStreamReader.START_DOCUMENT, reader.getEventType(), "Should be at start of document");
        }
    }

    @Test
    void testInitXmlReaderThrowsExceptionOnInvalidInput() {
        XmlParserHelper helper = new XmlParserHelper();
        assertThrows(IOException.class, () -> {
            try (FileInputStream fis = new FileInputStream("non_existing_file.xml")) {
                helper.initXmlReader(fis);
            }
        });
    }
}