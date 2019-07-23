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
        identifier = new String[]{"[gender]","[zipcode]","[gender, zipcode]"};
        distinction = new double[]{0.18181818181818182,1.0,1.0};
        separation = new double[]{0.5454545454545454,1.0,1.0};
        testData = GenerateTestData.ageGenderZipcodeDataset2Quasi();
        pModel = ARXPopulationModel.create(testData.getHandle().getNumRows(), 0.01d);

    }

    private void assertDataIsCorrect(AttributeRisk attributeRisk, String[] identifier, double[] distinction, double[] separation){
        List<AttributeRisk.QuasiIdentifierRisk> quasiIdentifierRiskList = attributeRisk.getQuasiIdentifierRiskList();
        int index = 0;
        for(AttributeRisk.QuasiIdentifierRisk actual : quasiIdentifierRiskList){
            Assertions.assertEquals(identifier[index],actual.getIdentifier().toString());
            Assertions.assertEquals(distinction[index], actual.getDistinction());
            Assertions.assertEquals(separation[index], actual.getSeparation());
            index++;
        }
    }

    @Test
    void create_data_is_correct(){
        AttributeRisk result = AttributeRisk.create(testData.getHandle(), pModel);
        assertDataIsCorrect(result, identifier,distinction,separation);
    }

}