package org.pati.pati_xml_csv.service.helper;

import lombok.NoArgsConstructor;
import org.pati.pati_xml_csv.model.ElementInfo;

import javax.xml.stream.XMLStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@NoArgsConstructor
public class XmlElementHelper {

    private String handleStartElement(XMLStreamReader reader, Stack<ElementInfo> stack, String currentColumnKey) {
        Map<String, String> attributes = new HashMap<>();

        for (int i = 0; i < reader.getAttributeCount(); i++) {
            attributes.put(reader.getAttributeLocalName(i), reader.getAttributeValue(i));
        }

        stack.push(new ElementInfo(reader.getLocalName(), attributes));

        //get xxx data from tag attribute name="xxx"
        if (attributes.containsKey("name")) {
            return attributes.get("name");
        }

        return currentColumnKey;
    }

    public String handleEndElement(Stack<ElementInfo> stack, String currentColumnKey) {
        if (stack.isEmpty()) return currentColumnKey;

        ElementInfo endedElement = stack.pop();
        if (endedElement.getAttributes().containsKey("name")
                && endedElement.getOneAttribute("name").equals(currentColumnKey)) {
            return null;
        }

        return currentColumnKey;
    }

    // use stack for storing all elements
    private String findRecordId(Stack<ElementInfo> stack) {
        if (stack.size() > 1) {
            for (int i = stack.size() - 2; i >= 0; i--) {
                String id = stack.get(i).getOneAttribute("id");
                if (id != null) {
                    return id;
                }
            }
        }
        if (!stack.isEmpty()) {
            String id = stack.peek().getOneAttribute("id");
            if (id != null) {
                return id;
            }
        }
        return null;
    }

    private String constructHeader(Stack<ElementInfo> stack, String columnKey) {
        if (columnKey != null) {
            return columnKey;
        }
        return stack.peek().getName();
    }
}
