package no.nav.arxaas.utils;

import no.nav.arxaas.model.Request;
import org.deidentifier.arx.Data;

/**
* Public contract to be fulfilled by data arxaas and analysation classes
*/
public interface DataFactory {

    /***
     * Returns an ARX {@link Data} object created from the provided payload.
     * @param payload Model object containing the data to be analysed and anonymized, and parameters to use in analysation and arxaas
     * @return an ARX {@link Data} object containing a tabular dataset and attribute types assigned to each dataset field
     */
    Data create(Request payload);
}
