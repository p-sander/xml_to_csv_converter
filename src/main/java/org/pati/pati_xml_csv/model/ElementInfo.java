package org.pati.pati_xml_csv.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ElementInfo {

    private String name;

    private Map<String, String> attributes;

    public String getOneAttribute(String attrName) {
        return attributes.get(attrName);
    }

}
