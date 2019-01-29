package no.OsloMET.aaas;

import no.oslomet.aaas.AaaSApplication;
import org.junit.Assert;
import org.junit.Test;

public class AaaSApplicationTest {

    @Test
    public void getMessage() {
        AaaSApplication obj = new AaaSApplication();
        Assert.assertEquals("Hello mkyong", obj.getMessage("mkyong"));
    }
}