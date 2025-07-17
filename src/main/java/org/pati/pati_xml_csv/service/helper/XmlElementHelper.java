package org.pati.pati_xml_csv.service.helper;

import lombok.NoArgsConstructor;
import org.pati.pati_xml_csv.model.ElementInfo;

import java.util.Stack;

@NoArgsConstructor
public class XmlElementHelper {

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
