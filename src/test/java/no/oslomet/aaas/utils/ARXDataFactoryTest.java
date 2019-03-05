package no.oslomet.aaas.utils;


import no.oslomet.aaas.model.AttributeTypeModel;
import no.oslomet.aaas.model.MetaData;
import org.deidentifier.arx.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static no.oslomet.aaas.model.AttributeTypeModel.IDENTIFYING;
import static no.oslomet.aaas.model.AttributeTypeModel.QUASIIDENTIFYING;


/**
 * JOB: Coverts data from payload to fully configured ARX Data object
 *
 * DATA: dataset from payload, attribute types, hierarchies for fields in the dataset, datatypes for fields
 *
 * BEHAVIOUR: create ARX Data object.
 */
public class ARXDataFactoryTest {

    String testData;
    MetaData testMetaData;
    String testDataWithSemicolons;

    @BeforeEach
    public void generateTestData() {
        testData = "age, gender, zipcode\n" +
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

        testDataWithSemicolons = "age; gender; zipcode\n" +
                "34; male; 81667\n" +
                "35; female; 81668\n" +
                "36; male; 81669\n" +
                "37; female; 81670\n" +
                "38; male; 81671\n" +
                "39; female; 81672\n" +
                "40; male; 81673\n" +
                "41; female; 81674\n" +
                "42; male; 81675\n" +
                "43; female ; 81676\n" +
                "44; male; 81677";

        testMetaData = new MetaData();

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


    }

    @Test
    public void create_data_shape_is_correct(){
        ARXDataFactory dataFactory = new ARXDataFactory(testData, testMetaData);
        Data resultData = dataFactory.create();
        Assertions.assertNotNull(resultData);
        resultData.getHandle().iterator().forEachRemaining(strings -> Assertions.assertEquals(3, strings.length));
    }

    @Test
    public void create_with_null_data(){

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ARXDataFactory dataFactory = new ARXDataFactory(null, testMetaData);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ARXDataFactory dataFactory = new ARXDataFactory(testData, null);
        });
    }

    @Test
    public void create_with_semicolon_data(){
        ARXDataFactory dataFactory = new ARXDataFactory(testDataWithSemicolons, testMetaData);
        Data resultData = dataFactory.create();
        Assertions.assertNotNull(resultData);
        resultData.getHandle().iterator().forEachRemaining(strings -> Assertions.assertEquals(3, strings.length));
    }


}
