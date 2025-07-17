package org.pati.pati_xml_csv.service.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pati.pati_xml_csv.model.ElementInfo;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class XmlElementHelperTest {

    private XmlElementHelper helper;
    private Stack<ElementInfo> stack;
    private Map<String, Map<String, String>> recordsById;
    private Set<String> headers;

    @BeforeEach
    void setUp() {
        helper = new XmlElementHelper();
        stack = new Stack<>();
        recordsById = new LinkedHashMap<>();
        headers = new LinkedHashSet<>();
    }

    @Test
    void shouldHandleStartElementAndReturnName() throws Exception {
        String xml = "<book name=\"title\"></book>";
        XMLStreamReader reader = createXmlReader(xml);

        while (reader.hasNext()) {
            int eventType = reader.next();
            if (eventType == XMLStreamReader.START_ELEMENT) {
                String columnKey = helper.handleStartElement(reader, stack, null);

                assertEquals("title", columnKey);
                assertFalse(stack.isEmpty());
                assertEquals("book", stack.peek().getName());
                return;
            }
        }

        fail("START_ELEMENT not found");
    }

    @Test
    void shouldHandleEndElementAndResetCurrentColumnKey() {
        stack.push(new ElementInfo("book", Map.of("name", "title")));
        String updatedKey = helper.handleEndElement(stack, "title");

        assertNull(updatedKey);
        assertTrue(stack.isEmpty());
    }

    @Test
    void shouldHandleCharactersAndPopulateRecord() throws Exception {
        stack.push(new ElementInfo("book", Map.of("id", "123")));
        XMLStreamReader reader = createXmlReader("<book>Book Title</book>");

        while (reader.hasNext()) {
            int eventType = reader.next();
            if (eventType == XMLStreamReader.CHARACTERS) {
                helper.handleCharacters(reader, stack, "title", recordsById, headers);

                assertTrue(recordsById.containsKey("123"));
                assertEquals("Book Title", recordsById.get("123").get("title"));
                assertTrue(headers.contains("title"));
                assertTrue(headers.contains("id"));
                return;
            }
        }

        fail("CHARACTERS event not found");
    }

    private XMLStreamReader createXmlReader(String xml) throws Exception {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        return factory.createXMLStreamReader(new StringReader(xml));
    }
}
