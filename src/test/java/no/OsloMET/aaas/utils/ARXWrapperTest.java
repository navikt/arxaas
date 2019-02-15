package no.OsloMET.aaas.utils;

import no.oslomet.aaas.utils.ARXWrapper;
import org.deidentifier.arx.Data;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ARXWrapperTest {
    ARXWrapper arxWrapper;
    @Before
    public void initilze(){
        arxWrapper = new ARXWrapper();
    }

    @Test
    public void makedata() {
        String testData = "age, gender, zipcode\n34, male, 81667\n35, female, 81668\n36, male, 81669";
        Data.DefaultData expectedResult = Data.create();
        expectedResult.add("age", "gender", "zipcode");
        expectedResult.add("34", "male", "81667");
        expectedResult.add("35", "female", "81668");
        expectedResult.add("36", "male", "81669");

        String expectedResultValue1 = expectedResult.getHandle().getValue(0,0);
        String expectedResultValue2 = expectedResult.getHandle().getValue(1,1);
        String expectedResultValue3 = expectedResult.getHandle().getValue(2,2);

        Data result = arxWrapper.makedata(testData);
        String resultValue1 = result.getHandle().getValue(0,0);
        String resultValue2 = result.getHandle().getValue(1,1);
        String resultValue3 = result.getHandle().getValue(2,2);

        //System.out.println("r: " + resultValue1);
        //System.out.println("expected: " + expectedResultValue1);
        Assert.assertEquals(resultValue1,expectedResultValue1);
        //System.out.println("r: " + resultValue2);
        //System.out.println("expected: " + expectedResultValue2);
        Assert.assertEquals(resultValue2,expectedResultValue2);
        //System.out.println("r: " + resultValue3));
        //System.out.println("expected: " + expectedResultValue3 );
        Assert.assertEquals(resultValue3,expectedResultValue3 );
    }

    @Test
    public void getPrivacyModel(){

    }

    @Test
    public void defineAttri() {
    }

    @Test
    public void defineHeirarchy() {
    }

    @Test
    public void setKAnonymity() {
    }

    @Test
    public void setAnonymizer() {
    }

    @Test
    public void anonomize() {
    }
}