package no.oslomet.aaas.utils;

import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.AttributeTypeModel;
import no.oslomet.aaas.model.MetaData;
import no.oslomet.aaas.model.PrivacyModel;
import org.deidentifier.arx.Data;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static no.oslomet.aaas.model.AttributeTypeModel.IDENTIFYING;
import static no.oslomet.aaas.model.AttributeTypeModel.QUASIIDENTIFYING;
import static no.oslomet.aaas.model.PrivacyModel.*;

public class ARXModelSetterTest {

    private ARXModelSetter arxModelSetter;

    @Before
    public void initialize(){
        arxModelSetter = new ARXModelSetter();
    }

    //-------------------------preparing test payload----------------------------//
    private Data data = Data.create();
    private AnonymizationPayload testPayload = new AnonymizationPayload();
    private MetaData testMetaData = new MetaData();

    @Before
    public void generateTestData() {
        String testData ="age, gender, zipcode\n" +
                "34, male, 81667\n" +
                "35, female, 81668\n" +
                "36, male, 81669\n" +
                "37, female, 81670\n" +
                "38, male, 81671\n" +
                "39, female, 81672\n" +
                "40, male, 81673\n" +
                "41, female, 81674\n" +
                "42, male, 81675\n" +
                "43, female , 81676\n" +
                "44, male, 81677";

        testPayload.setData(testData);

        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        Map<String, AttributeTypeModel> testMapAttribute = new HashMap<>();
        testMapAttribute.put("age",IDENTIFYING);
        testMapAttribute.put("gender",QUASIIDENTIFYING);
        testMapAttribute.put("zipcode",QUASIIDENTIFYING);
        testMetaData.setAttributeTypeList(testMapAttribute);

        //Defining Hierarchy for a give column name
        Map<String ,String[][]> testMapHierarchy = new HashMap<>();
        String [][] testHeirarchy = new String[][]{
                {"81667", "8166*", "816**", "81***", "8****", "*****"}
                ,{"81668", "8166*", "816**", "81***", "8****", "*****"}
                ,{"81669", "8166*", "816**", "81***", "8****", "*****"}
                ,{"81670", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81671", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81672", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81673", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81674", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81675", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81676", "8167*", "816**", "81***", "8****", "*****"}
                ,{"81677", "8167*", "816**", "81***", "8****", "*****"}
        };
        testMapHierarchy.put("zipcode",testHeirarchy);
        testMetaData.setHierarchy(testMapHierarchy);

        //Define K-anonymity
        Map<PrivacyModel,Map<String,String>> testMapPrivacy = new HashMap<>();
        Map<String,String> testMapValue = new HashMap<>();
        testMapValue.put("k","5");
        testMapPrivacy.put(KANONYMITY,testMapValue);
        testMetaData.setModels(testMapPrivacy);

        testPayload.setMetaData(testMetaData);
    }
    //------------------------------------------------------------------------//

    @Test
    public void setSensitivityModels() {
        arxModelSetter.setSensitivityModels(data, testPayload);
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
