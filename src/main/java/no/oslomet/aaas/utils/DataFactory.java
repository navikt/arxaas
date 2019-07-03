package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.Request;
import org.deidentifier.arx.Data;

/**
* Public contract to be fulfilled by data anonymization and analysation classes
*/
public interface DataFactory {

    /***
     * Returns an ARX {@link Data} object created from the provided payload.
     * @param payload Model object containing the data to be analysed and anonymized, and parameters to use in analysation and anonymization
     * @return an ARX {@link Data} object containing a tabular dataset and attribute types assigned to each dataset field
     */
    Data create(Request payload);
}