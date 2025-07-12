package org.pati.pati_xml_csv.component;

import lombok.AllArgsConstructor;
import org.pati.pati_xml_csv.servie.ConversionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class XmlToCsvRunner implements CommandLineRunner {

    private final ConversionService conversionService;
    @Override
    public void run(String... args) throws Exception {

    }
}
