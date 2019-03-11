package no.oslomet.aaas.model;

import java.util.List;

/***
 *Model class for AnonymizationPayload containing the tabular dataset and a metadata object containing the settings for
 * the anonymization process.
 */
public class AnonymizationPayload {
    private List<String[]> data;
    private MetaData metaData;

    /***
     * Getter method for dataset to anonymize.
     * @return String containing the tabular dataset
     */
    public List<String[]> getData() {
        return data;
    }

    /***
     * Setter method for the tabular dataset to be anonymized.
     * @param data String containing the tabular dataset to be anonymized
     */
    public void setData(List<String[]> data) {
        this.data = data;
    }

    /***
     * Getter method for the model class {@link MetaData}.
     * @return Object of  {@link MetaData} containing anonymization and analysation settings for the dataset
     */
    public MetaData getMetaData() {
        return metaData;
    }

    /***
     * Setter method for the model class {@link MetaData}.
     * @param metaData Object of {@link MetaData} containing anonymization and analysation settings for the dataset
     */
    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }
}

