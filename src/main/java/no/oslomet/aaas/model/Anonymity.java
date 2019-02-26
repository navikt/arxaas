package no.oslomet.aaas.model;

/***
 * Model for Anonymity class. Sets the anonymous status of the dataset after anonymization.
 */
public enum Anonymity {
    ANONYMOUS,
    NOT_ANONYMOUS,
    UNKNOWN,
    PROBABLY_ANONYMOUS,
    PROBABLY_NOT_ANONYMOUS
}