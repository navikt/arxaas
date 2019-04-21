package no.oslomet.aaas.model;

import no.oslomet.aaas.model.risk.RiskProfile;


/***
 * Model class for the response object from anonymisation process.
 */
public class AnonymizationResultPayload {

    private final AnonymizeResult anonymizeResult;
    private final RiskProfile riskProfile;

    /***
     * Setter method for the response object from the anonymisation and analysation process.
     * @param anonymizeResult model {@link AnonymizeResult} containing the anonymized dataset and the metadata
     *                        used for the anonymization
     * @param riskProfile contains the analysis data from the anonymized dataset.
     */
    public AnonymizationResultPayload(AnonymizeResult anonymizeResult,
                                      RiskProfile riskProfile) {
        this.anonymizeResult = anonymizeResult;
        this.riskProfile = riskProfile;

    }

    /***
     * Getter method for the {@link AnonymizeResult} model class containing the anonymized dataset and the metadata used
     * for the anonymization.
     * @return Object of {@link AnonymizeResult} containing the dataset and metadata after the anonymization process
     */
    public AnonymizeResult getAnonymizeResult() {
        return anonymizeResult;
    }



    /***
     * Getter method for the analysis of the anonymized dataset.
     * @return RiskProfile containing the analysis data of the anonymized dataset
     */
    public RiskProfile getRiskProfile() {
        return riskProfile;
    }



}
