package no.oslomet.aaas.analyzer;

import no.oslomet.aaas.analyzer.ARXReIdentificationRiskFactory;
import no.oslomet.aaas.model.ReIdentificationRisk;
import org.deidentifier.arx.*;
import org.deidentifier.arx.criteria.KAnonymity;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.*;


public class ARXReIdentificationRiskFactoryTest {

    //----------------Preparing test Data -------------------------//
    private Data.DefaultData data = Data.create();
    private ARXConfiguration config = ARXConfiguration.create();
    private ARXAnonymizer anonymizer = new ARXAnonymizer();
    private ARXResult anonymizeResult;
    private DataHandle testData;
    private DataHandle testResultData;
    private ARXPopulationModel pModel;

    @Before
    public void generateTestData() {
        data.add("age", "gender", "zipcode");
        data.add("34", "male", "81667");
        data.add("35", "female", "81668");
        data.add("36", "male", "81669");
        data.add("37", "female", "81670");
        data.add("38", "male", "81671");
        data.add("39", "female", "81672");
        data.add("40", "male", "81673");
        data.add("41", "female", "81674");
        data.add("42", "male", "81675");
        data.add("43", "female", "81676");
        data.add("44", "male", "81677");

        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        data.getDefinition().setAttributeType("age", AttributeType.IDENTIFYING_ATTRIBUTE);
        data.getDefinition().setAttributeType("gender", AttributeType.QUASI_IDENTIFYING_ATTRIBUTE);
        data.getDefinition().setAttributeType("zipcode", AttributeType.QUASI_IDENTIFYING_ATTRIBUTE);

        AttributeType.Hierarchy.DefaultHierarchy hierarchy = AttributeType.Hierarchy.create();
        hierarchy.add("81667", "8166*", "816**", "81***", "8****", "*****");
        hierarchy.add("81668", "8166*", "816**", "81***", "8****", "*****");
        hierarchy.add("81669", "8166*", "816**", "81***", "8****", "*****");
        hierarchy.add("81670", "8167*", "816**", "81***", "8****", "*****");
        hierarchy.add("81671", "8167*", "816**", "81***", "8****", "*****");
        hierarchy.add("81672", "8167*", "816**", "81***", "8****", "*****");
        hierarchy.add("81673", "8167*", "816**", "81***", "8****", "*****");
        hierarchy.add("81674", "8167*", "816**", "81***", "8****", "*****");
        hierarchy.add("81675", "8167*", "816**", "81***", "8****", "*****");
        hierarchy.add("81676", "8167*", "816**", "81***", "8****", "*****");
        hierarchy.add("81677", "8167*", "816**", "81***", "8****", "*****");

        data.getDefinition().setAttributeType("zipcode", hierarchy);

        config.addPrivacyModel(new KAnonymity(4));
        config.setSuppressionLimit(0.02d);

        anonymizer.setMaximumSnapshotSizeDataset(0.2);
        anonymizer.setMaximumSnapshotSizeSnapshot(0.2);
        anonymizer.setHistorySize(200);
        try {
            anonymizeResult = anonymizer.anonymize(data,config);
        } catch (IOException e) {
            e.printStackTrace();
        }

        testData=data.getHandle();
        testResultData= anonymizeResult.getOutput();

        pModel= ARXPopulationModel.create(data.getHandle().getNumRows(), 0.01d);
    }
    //--------------------------------------------------------

    //-------------------------Test against re-identification risk for dataset before anonymisation----------------//

    @Test
    public void create__risk_is_correct() {
        ReIdentificationRisk actual = ARXReIdentificationRiskFactory.create(testData,pModel);
        Assertions.assertEquals(beforeAnonymization(),actual);
    }

    //-------------------------Test against re-identification risk for dataset after anonymisation----------------//

    @Test
    public void create__anonymized_data_risk_is_correct() {
        ReIdentificationRisk actual = ARXReIdentificationRiskFactory.create(testResultData,pModel);
        Assertions.assertEquals(afterAnonymization(),actual);
    }


    private static ReIdentificationRisk beforeAnonymization(){
        Map<String,String > expected = new HashMap<>();
        expected.put("Prosecutor_attacker_success_rate","100.0");
        expected.put("records_affected_by_highest_prosecutor_risk","100.0");
        expected.put("sample_uniques","100.0");
        expected.put("estimated_prosecutor_risk","100.0");
        expected.put("population_model","ZAYATZ");
        expected.put("highest_journalist_risk","100.0");
        expected.put("records_affected_by_lowest_risk","100.0");
        expected.put("estimated_marketer_risk","100.0");
        expected.put("Journalist_attacker_success_rate","100.0");
        expected.put("highest_prosecutor_risk","100.0");
        expected.put("estimated_journalist_risk","100.0");
        expected.put("lowest_risk","100.0");
        expected.put("Marketer_attacker_success_rate","100.0");
        expected.put("average_prosecutor_risk","100.0");
        expected.put("records_affected_by_highest_journalist_risk","100.0");
        expected.put("population_uniques","100.0");
        expected.put("quasi_identifiers","[zipcode, gender]");
        return new ReIdentificationRisk(expected);
    }


    private static ReIdentificationRisk afterAnonymization(){
        Map<String,String> expected = new HashMap<>();
        expected.put("Prosecutor_attacker_success_rate","18.181818181818183");
        expected.put("records_affected_by_highest_prosecutor_risk","45.45454545454545");
        expected.put("sample_uniques","0.0");
        expected.put("estimated_prosecutor_risk","20.0");
        expected.put("population_model","DANKAR");
        expected.put("highest_journalist_risk","20.0");
        expected.put("records_affected_by_lowest_risk","54.54545454545454");
        expected.put("estimated_marketer_risk","18.181818181818183");
        expected.put("Journalist_attacker_success_rate","18.181818181818183");
        expected.put("highest_prosecutor_risk","20.0");
        expected.put("estimated_journalist_risk","20.0");
        expected.put("lowest_risk","16.666666666666664");
        expected.put("Marketer_attacker_success_rate","18.181818181818183");
        expected.put("average_prosecutor_risk","18.181818181818183");
        expected.put("records_affected_by_highest_journalist_risk","45.45454545454545");
        expected.put("population_uniques","0.0");
        expected.put("quasi_identifiers","[zipcode, gender]");
        return new ReIdentificationRisk(expected);
    }
}