package no.OsloMET.aaas.utils;

import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.PrivacyModel;
import no.oslomet.aaas.model.SensitivityModel;
import no.oslomet.aaas.model.MetaData;
import no.oslomet.aaas.utils.ARXWrapper;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.Data;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static no.oslomet.aaas.model.PrivacyModel.KANONYMITY;
import static no.oslomet.aaas.model.SensitivityModel.IDENTIFYING;

public class ARXWrapperTest {
    ARXWrapper arxWrapper;

    @Before
    public void initilze(){
        arxWrapper = new ARXWrapper();
    }

    @Test
    public void makedata() {
        String testData = "age, gender, zipcode\n34, male, 81667\n35, female, 81668\n36, male, 81669";

        String expectedResultValue1 = "34";
        String expectedResultValue2 = "female";
        String expectedResultValue3 = "81669";

        Data result = arxWrapper.makedata(testData);
        String resultValue1 = result.getHandle().getValue(0,0);
        String resultValue2 = result.getHandle().getValue(1,1);
        String resultValue3 = result.getHandle().getValue(2,2);

        Assert.assertEquals(resultValue1,expectedResultValue1);
        Assert.assertEquals(resultValue2,expectedResultValue2);
        Assert.assertEquals(resultValue3,expectedResultValue3 );
    }

    @Test
    public void setSuppression(){
        ARXConfiguration testdata = ARXConfiguration.create();
        arxWrapper.setsuppressionlimit(testdata);

        String result = String.valueOf(testdata.getSuppressionLimit());

        String expectedResult = "0.02";

        Assert.assertEquals(result,expectedResult);
    }

    @Test
    public void setSensitivityModels() {
        String testvalues = "age, gender, zipcode\n34, male, 81667\n35, female, 81668\n36, male, 81669";
        Data testData = arxWrapper.makedata(testvalues);
        AnonymizationPayload testpayload = new AnonymizationPayload();
        MetaData testMetaData = new MetaData();
        Map<String,SensitivityModel> testMap = new HashMap<>();
        testMap.put("age",IDENTIFYING);
        testMetaData.setSensitivityList(testMap);
        testpayload.setMetaData(testMetaData);

        arxWrapper.setSensitivityModels(testData,testpayload);

        String result = String.valueOf(testData.getDefinition().getAttributeType("age"));
        String expectedResult = "IDENTIFYING_ATTRIBUTE";
        Assert.assertEquals(result,expectedResult);
    }

    @Test
    public void setPrivacyModelsKAnon(){

        ARXConfiguration testConfig = ARXConfiguration.create();
        AnonymizationPayload testpayload = new AnonymizationPayload();
        MetaData testMetaData = new MetaData();
        Map<PrivacyModel,Map<String,String>> testMap = new HashMap<>();
        Map<String,String> testMapValue = new HashMap<>();
        testMapValue.put("k","5");
        testMap.put(KANONYMITY,testMapValue);
        testMetaData.setModels(testMap);
        testpayload.setMetaData(testMetaData);

        arxWrapper.setPrivacyModels(testConfig,testpayload);

        String result = String.valueOf(testConfig.getPrivacyModels());

        String expectedResult = "[5-anonymity]";

        Assert.assertEquals(result,expectedResult);


    }

    @Test
    public void setAnonymizer() {
    }

    @Test
    public void anonomize() {
    }
}