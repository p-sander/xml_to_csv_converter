package org.pati.pati_xml_csv.service.helper;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;

@NoArgsConstructor
@Component
public class XmlParserHelper {
    public XMLStreamReader initXmlReader(FileInputStream in) throws XMLStreamException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        inputFactory.setProperty(XMLInputFactory.IS_COALESCING, true);
        return inputFactory.createXMLStreamReader(in);
    }
}
