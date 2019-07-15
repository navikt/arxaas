package no.nav.arxaas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiControllerTest {

    private ApiController testRoot;

    @BeforeEach
    void setUp(){
        testRoot = new ApiController();
    }

    @Test
    void root__has_resources() {
        var response  = testRoot.root();
        assertTrue(response.hasLink("anonymize"));
        assertTrue(response.hasLink("analyze"));
    }
}