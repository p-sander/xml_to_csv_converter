package org.pati.pati_xml_csv.exceptions;

import java.io.IOException;

public class ParseXmlException extends IOException {
    public ParseXmlException(String message) {
        super(message);
    }

    public ParseXmlException(String message, Throwable cause) {
        super(message, cause);
    }
}


