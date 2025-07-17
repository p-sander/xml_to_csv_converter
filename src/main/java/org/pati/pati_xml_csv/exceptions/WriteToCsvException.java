package org.pati.pati_xml_csv.exceptions;

import java.io.IOException;

public class WriteToCsvException extends IOException {
    public WriteToCsvException(String message) {
        super(message);
    }

    public WriteToCsvException(String message, Throwable cause) {
        super(message, cause);
    }
}