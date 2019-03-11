package no.oslomet.aaas.utils;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.AnonymizationPayload;
import org.deidentifier.arx.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class ARXWrapperTest {
    private ARXWrapper arxWrapper;
    private final ARXDataFactory dataFactory = new ARXDataFactory();

    @BeforeEach
    void initialize(){
        arxWrapper = new ARXWrapper(new ARXConfigurationSetter(), new ARXModelSetter());
    }

    private ARXConfiguration config = ARXConfiguration.create();
    private ARXAnonymizer anonymizer = new ARXAnonymizer();
    private AnonymizationPayload testPayload;


    @BeforeEach
    void generateTestData() {
        testPayload = GenerateTestData.zipcodeAnonymizePayload();
    }

    @Test
    void setData() {
        Data result = arxWrapper.createData(testPayload.getData());
        String actualValue1 = result.getHandle().getValue(0,0);
        String actualValue2 = result.getHandle().getValue(1,1);
        String actualValue3 = result.getHandle().getValue(2,2);

        Assertions.assertEquals("34",actualValue1);
        Assertions.assertEquals("female",actualValue2);
        Assertions.assertEquals("81669",actualValue3);
    }

    @Test
    void setAnonymizer() {
        arxWrapper.setAnonymizer(anonymizer);
        String actual1=String.valueOf(anonymizer.getMaximumSnapshotSizeDataset());
        String actual2=String.valueOf(anonymizer.getMaximumSnapshotSizeSnapshot());
        String actual3=String.valueOf(anonymizer.getHistorySize());

        Assertions.assertEquals("0.2",actual1);
        Assertions.assertEquals("0.2",actual2);
        Assertions.assertEquals("200",actual3);
    }

    @Test
    void anonymize() throws IOException {
            Data data = dataFactory.create(testPayload.getData(),testPayload.getMetaData());

            ARXResult testresult = arxWrapper.anonymize(anonymizer,config, testPayload,data);
            List<String[]> actual = arxWrapper.getAnonymizeData(testresult);

            String[][] rawData = {{"age", "gender", "zipcode" },
                    {"*", "male", "816**"},
                    {"*", "female", "816**"},
                    {"*", "male", "816**"},
                    {"*", "female", "816**"},
                    {"*", "male", "816**"},
                    {"*", "female", "816**"},
                    {"*", "male", "816**"},
                    {"*", "female", "816**"},
                    {"*", "male", "816**"},
                    {"*", "female", "816**"},
                    {"*", "male", "816**"}};
            List<String[]> expected = List.of(rawData);
            for(int x = 0; x<12;x++) {
                Assertions.assertArrayEquals(expected.get(x), actual.get(x));
            }
    }
}