package no.oslomet.aaas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RootControllerTest {

    private ApiController testRoot;

    @BeforeEach
    void setUp(){
        testRoot = new ApiController();
    }

    @Test
    void api_root__has_resources() {
        var response  = testRoot.apiRoot();
        assertTrue(response.hasLink("anonymize"));
        assertTrue(response.hasLink("analyze"));
    }
}