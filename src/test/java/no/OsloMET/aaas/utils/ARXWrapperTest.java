package no.OsloMET.aaas.utils;

import no.oslomet.aaas.utils.ARXWrapper;
import org.deidentifier.arx.Data;
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
        String testData = "age, gender, zipcode\n34, male, 81667\n35, female, 81668\n36, male, 81669\n37, female, 81670\n38, male, 81671\n39, female, 81672\n40, male, 81673\n41, female, 81674\n42, male, 81675\n43, female, 81676\n44, male, 81677";
        Data result = arxWrapper.makedata(testData);
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