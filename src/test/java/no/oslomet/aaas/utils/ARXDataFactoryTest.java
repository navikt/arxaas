package no.oslomet.aaas.utils;


import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.AnonymizationPayload;
import no.oslomet.aaas.model.AttributeTypeModel;
import no.oslomet.aaas.model.MetaData;
import org.deidentifier.arx.AttributeType;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.DataHandle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static no.oslomet.aaas.model.AttributeTypeModel.*;

/**
 * JOB: Coverts data from payload to fully configured ARX Data object
 *
 * DATA: dataset from payload, attribute types, hierarchies for fields in the dataset, datatypes for fields
 *
 * BEHAVIOUR: create ARX Data object.
 */
class ARXDataFactoryTest {

    private AnonymizationPayload testPayload;


    @BeforeEach
    void generateTestData() {
        testPayload = GenerateTestData.zipcodeAnonymizePayload();
    }

    @Test
     void create_data_shape_is_correct(){
        ARXDataFactory dataFactory = new ARXDataFactory();
        Data resultData = dataFactory.create(testPayload);
        Assertions.assertNotNull(resultData);
        resultData.getHandle().iterator().forEachRemaining(strings -> Assertions.assertEquals(3, strings.length));
    }

    @Test
     void create_with_null_data(){
        testPayload.setMetaData(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> (new ARXDataFactory()).create(testPayload));
        testPayload.setMetaData(new MetaData());
        testPayload.setData(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> (new ARXDataFactory()).create(testPayload));
    }

    @Test
    void create_returnData_is_correct(){
        ARXDataFactory dataFactory = new ARXDataFactory();
        Data data = dataFactory.create(testPayload);
        DataHandle handle = data.getHandle();
        List<String[]> actual = new ArrayList<>();
        handle.iterator().forEachRemaining(actual::add);

        String[][] rawData = {{"age", "gender", "zipcode" },
                {"34", "male", "81667"},
                {"35", "female", "81668"},
                {"36", "male", "81669"},
                {"37", "female", "81670"},
                {"38", "male", "81671"},
                {"39", "female", "81672"},
                {"40", "male", "81673"},
                {"41", "female", "81674"},
                {"42", "male", "81675"},
                {"43", "female", "81676"},
                {"44", "male", "81677"}};
        List<String[]> expected = List.of(rawData);
        for(int x = 0; x<12;x++) {
            Assertions.assertArrayEquals(expected.get(x), actual.get(x));
        }
    }

    @Test
    void create_returnDataAttribute_is_correct(){
        ARXDataFactory dataFactory = new ARXDataFactory();
        Data data = dataFactory.create(testPayload);
        DataHandle handle = data.getHandle();
        AttributeType actual1 = handle.getDefinition().getAttributeType("age");
        AttributeType actual2 = handle.getDefinition().getAttributeType("gender");
        AttributeType actual3 = handle.getDefinition().getAttributeType("zipcode");

        Assertions.assertEquals(IDENTIFYING.getAttributeType(),actual1);
        Assertions.assertEquals(QUASIIDENTIFYING.getAttributeType(),actual2);
        Assertions.assertEquals(QUASIIDENTIFYING.getAttributeType(),actual3);
    }

    @Test
    void create_returnDataHierarchy_is_correct(){
        ARXDataFactory dataFactory = new ARXDataFactory();
        Data data = dataFactory.create(testPayload);
        DataHandle handle = data.getHandle();
        String [][] actual = handle.getDefinition().getHierarchy("zipcode");
        String [][] expected ={
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

        Assertions.assertArrayEquals(expected,actual);
    }

    @Test
    void create_returnDataAttribute_is_not_overwritten_by_hierarchy(){
        ARXDataFactory dataFactory = new ARXDataFactory();

        MetaData testMetaData = new MetaData();
        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        Map<String, AttributeTypeModel> testMapAttribute = new HashMap<>();
        testMapAttribute.put("age",IDENTIFYING);
        testMapAttribute.put("gender",SENSITIVE);
        testMapAttribute.put("zipcode",INSENSITIVE);
        testMetaData.setAttributeTypeList(testMapAttribute);

        //Defining Hierarchy for a give column name
        Map<String ,String[][]> testMapHierarchy = new HashMap<>();
        String [][] testHeirarchy = {
                {"81667", "8166*", "816**", "81***", "8****", "*****"}
                        };
        testMapHierarchy.put("zipcode",testHeirarchy);
        testMapHierarchy.put("age",testHeirarchy);
        testMapHierarchy.put("gender",testHeirarchy);
        testMetaData.setHierarchy(testMapHierarchy);

        testPayload.setMetaData(testMetaData);

        Data data = dataFactory.create(testPayload);
        DataHandle handle = data.getHandle();
        AttributeType actual1 = handle.getDefinition().getAttributeType("age");
        AttributeType actual2 = handle.getDefinition().getAttributeType("gender");
        AttributeType actual3 = handle.getDefinition().getAttributeType("zipcode");

        Assertions.assertEquals(IDENTIFYING.getAttributeType(),actual1);
        Assertions.assertEquals(SENSITIVE.getAttributeType(),actual2);
        Assertions.assertEquals(INSENSITIVE.getAttributeType(),actual3);
    }

}
