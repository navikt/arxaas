package no.oslomet.aaas.utils;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.AnonymizationPayload;
import org.deidentifier.arx.Data;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ARXModelSetterTest {

    private ARXModelSetter arxModelSetter;

    @Before
    public void initialize(){
        arxModelSetter = new ARXModelSetter();
    }

    private Data data = Data.create();
    private AnonymizationPayload testPayload;

    @Before
    public void generateTestData() {
        testPayload = GenerateTestData.zipcodeAnonymizePayload();
    }

    @Test
    public void setSensitivityModels() {
        arxModelSetter.setAttributeTypes(data, testPayload);
        String actual = String.valueOf(data.getDefinition().getAttributeType("age"));

        Assert.assertEquals("IDENTIFYING_ATTRIBUTE",actual);
    }

    @Test
    public void setHierarchies(){
        data = arxModelSetter.setHierarchies(data, testPayload);
        String[][] actual = data.getDefinition().getHierarchy("zipcode");
        String actualResult1 = actual[0][0];
        String actualResult2 = actual[6][3];
        String actualResult3 = actual[10][5];

        Assert.assertEquals("81667",actualResult1);
        Assert.assertEquals("81***",actualResult2);
        Assert.assertEquals("*****",actualResult3);
    }
}
