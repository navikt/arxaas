package no.oslomet.aaas.utils;


import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.AnonymizationPayload;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.DataHandle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        ARXDataFactory dataFactory = new ARXDataFactory(testPayload.getData(), testPayload.getMetaData());
        Data resultData = dataFactory.create();
        Assertions.assertNotNull(resultData);
        resultData.getHandle().iterator().forEachRemaining(strings -> Assertions.assertEquals(3, strings.length));
    }

    @Test
     void create_with_null_data(){

        Assertions.assertThrows(IllegalArgumentException.class, () -> new ARXDataFactory(null, testPayload.getMetaData()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ARXDataFactory(testPayload.getData(), null));
    }

    @Test
    void create_returnData_is_correct(){
        ARXDataFactory dataFactory = new ARXDataFactory(testPayload.getData(), testPayload.getMetaData());
        Data data = dataFactory.create();
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
        ARXDataFactory dataFactory = new ARXDataFactory(testPayload.getData(), testPayload.getMetaData());
        Data data = dataFactory.create();
        DataHandle handle = data.getHandle();
        String actual1 = String.valueOf(handle.getDefinition().getAttributeType("age"));
        String actual2 = String.valueOf(handle.getDefinition().getAttributeType("gender"));
        String actual3 = String.valueOf(handle.getDefinition().getAttributeType("zipcode"));

        Assertions.assertEquals("IDENTIFYING_ATTRIBUTE",actual1);
        Assertions.assertEquals("QUASI_IDENTIFYING_ATTRIBUTE",actual2);
        Assertions.assertEquals("QUASI_IDENTIFYING_ATTRIBUTE",actual3);
    }

    @Test
    void create_returnDataHierarchy_is_correct(){
        ARXDataFactory dataFactory = new ARXDataFactory(testPayload.getData(), testPayload.getMetaData());
        Data data = dataFactory.create();
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

}
