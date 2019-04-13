package no.oslomet.aaas.controller;

import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.hierarchies.ARXHierarchy;
import no.oslomet.aaas.model.analytics.RiskProfile;
import no.oslomet.aaas.model.hierarchy.HierarchyRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HierarchyControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HierarchyRequest hierarchyRequest;

    @BeforeEach
    void setUp() {
        var testdata = new String[] {"81667", "81668", "81669", "81670", "81671", "81672"};
        hierarchyRequest = new HierarchyRequest(testdata, HierarchyRequest.HierarchyBuilder.REDUCTIONBASED, Map.of("redactionCharacter", "*", "paddingCharacter", " "));
    }

    @Test
    void redactionHierarchy() {
        ResponseEntity<ARXHierarchy> responseEntity = restTemplate.postForEntity("/api/hierarchy",hierarchyRequest, ARXHierarchy.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.OK , responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        List.of(resultData.hierarchy).forEach(strings -> System.out.println(Arrays.toString(strings)));
    }
}