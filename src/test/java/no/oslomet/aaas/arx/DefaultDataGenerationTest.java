package no.oslomet.aaas.arx;

import org.deidentifier.arx.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultDataGenerationTest {

    @Test
    public void generateDefaultDataFromList(){
        Data.DefaultData data = Data.create();

        String[][] rawData = new String[10][3];
        rawData[0][0] ="id";
        rawData[0][1] ="name";
        rawData[0][2] ="age";

        for (int i=1; i< 10; i++){
            rawData[i][0]=String.valueOf(i);
            rawData[i][1]="name"+i;
            rawData[i][2]= String.valueOf((10+i));
        }
        List.of(rawData).iterator().forEachRemaining(data::add);

        data.getHandle().iterator().forEachRemaining(strings -> System.out.println(Arrays.toString(strings)));
    }
}
