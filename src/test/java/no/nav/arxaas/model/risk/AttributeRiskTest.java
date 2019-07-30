package no.nav.arxaas.model.risk;

import no.nav.arxaas.GenerateTestData;
import org.deidentifier.arx.ARXPopulationModel;
import org.deidentifier.arx.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class AttributeRiskTest {

    private String[] identifier;
    private double[] distinction;
    private double[] separation;
    private Data testData;
    private ARXPopulationModel pModel;

    @BeforeEach
    void setUp(){
        identifier = new String[]{"[zipcode]"};
        distinction = new double[]{1.0};
        separation = new double[]{1.0};
        testData = GenerateTestData.ageGenderZipcodeDataset2Quasi();
        pModel = ARXPopulationModel.create(testData.getHandle().getNumRows(), 0.01d);

    }

    private void assertDataIsCorrect(AttributeRisk attributeRisk, String[] identifier, double[] distinction, double[] separation){
        List<AttributeRisk.QuasiIdentifierRisk> quasiIdentifierRiskList = attributeRisk.getQuasiIdentifierRiskList();

        AttributeRisk.QuasiIdentifierRisk actual = quasiIdentifierRiskList.get(0);
            Assertions.assertEquals(identifier[0],actual.getIdentifier().toString());
            Assertions.assertEquals(distinction[0], actual.getDistinction());
            Assertions.assertEquals(separation[0], actual.getSeparation());
    }

    @Test
    void create_data_is_correct(){
        AttributeRisk result = AttributeRisk.create(testData.getHandle(), pModel);
        assertDataIsCorrect(result, identifier,distinction,separation);
    }

}