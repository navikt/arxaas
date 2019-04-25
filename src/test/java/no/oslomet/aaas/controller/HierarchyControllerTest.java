package no.oslomet.aaas.controller;


import no.oslomet.aaas.model.hierarchy.*;
import no.oslomet.aaas.model.hierarchy.interval.Interval;
import no.oslomet.aaas.model.hierarchy.interval.IntervalBasedHierarchyBuilder;
import no.oslomet.aaas.model.hierarchy.RedactionBasedHierarchyBuilder.Order;
import no.oslomet.aaas.model.hierarchy.interval.Range;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HierarchyControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HierarchyRequest hierarchyRequest;
    private String[] testdata;

    @BeforeEach
    void setUp() {
        testdata = new String[] {"81667", "81668", "81669", "81670", "81671", "81672"};
        hierarchyRequest = new HierarchyRequest(
                testdata,
                new RedactionBasedHierarchyBuilder(
                        ' ',
                        '*',
                        Order.RIGHT_TO_LEFT,
                        Order.RIGHT_TO_LEFT));
    }

    @Test
    void redactionHierarchy() {
        ResponseEntity<Hierarchy> responseEntity = restTemplate.postForEntity("/api/hierarchy",hierarchyRequest, Hierarchy.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.OK , responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assert resultData != null;
        Assertions.assertNotNull(resultData.getHierarchy());
    }

    @Test
    void  intervalHierarchy(){
        String[][] expected = {
                {"0", "young", "[0, 4[", "*"},
                {"1", "young", "[0, 4[", "*"},
                {"2", "adult", "[0, 4[", "*"},
                {"3", "adult", "[0, 4[", "*"},
                {"4", "old", "[4, 8[", "*"},
                {"5", "old", "[4, 8[", "*"},
                {"6", "old", "[4, 8[", "*"},
                {"7", "old", "[4, 8[", "*"},
                {"8", "very-old", "[8, 12[", "*"},
                {"9", "very-old", "[8, 12[", "*"}};

        List<Interval> labeledIntervals = List.of(
                new Interval(0L,2L, "young"),
                new Interval(2L, 4L, "adult"),
                new Interval(4L, 8L, "old"),
                new Interval(8L, Long.MAX_VALUE, "very-old"));

        var testLevels = List.of(new Level(0, List.of(new Level.Group(2))));


        IntervalBasedHierarchyBuilder basedHierarchyBuilder = new IntervalBasedHierarchyBuilder(
                labeledIntervals,
                testLevels,
                new Range(0L, 0L, Long.MIN_VALUE / 4),
                new Range(81L, 100L, Long.MAX_VALUE / 4),
                IntervalBasedHierarchyBuilder.BuilderDataType.LONG);


        HierarchyRequest intervalHierarchyRequest = new HierarchyRequest(
                getExampleData(),
                basedHierarchyBuilder);

        ResponseEntity<Hierarchy> responseEntity = restTemplate.postForEntity("/api/hierarchy",intervalHierarchyRequest, Hierarchy.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.OK , responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assert resultData != null;
        Assertions.assertArrayEquals(expected,resultData.getHierarchy());
    }

    @Test
    void orderHierarchy(){
        String[][] expected =
                {{"Oslo", "nordic-city", "*",},
                        {"Bergen", "nordic-city", "*",},
                        {"Stockholm", "nordic-city", "*",},
                        {"London", "mid-european-city", "*",},
                        {"Paris", "mid-european-city", "*",}};

        var testData = new String[]{"Oslo", "Bergen", "Stockholm", "London", "Paris"};

        Level nordicGroup = new Level(0, List.of(new Level.Group(3, "nordic-city")));
        Level midEuroGroup = new Level(0, List.of(new Level.Group(2, "mid-european-city")));
        OrderBasedHierarchyBuilder basedHierarchyBuilder = new OrderBasedHierarchyBuilder(List.of(nordicGroup, midEuroGroup));

        HierarchyRequest hierarchyRequest = new HierarchyRequest(
                testData,
                basedHierarchyBuilder);
        ResponseEntity<Hierarchy> responseEntity = restTemplate.postForEntity("/api/hierarchy",hierarchyRequest, Hierarchy.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.OK , responseEntity.getStatusCode());
        var resultData = responseEntity.getBody();
        assert resultData != null;
        Assertions.assertArrayEquals(expected, resultData.getHierarchy());

    }


    @Test
    void intervalHierarchyWithEmptyInterval_should_return_bad_request(){
        List<Interval> labeledIntervals = List.of();
        var testLevels = List.of(new Level(0, List.of(new Level.Group(2))));
        IntervalBasedHierarchyBuilder basedHierarchyBuilder = new IntervalBasedHierarchyBuilder(
                labeledIntervals,
                testLevels, null, null, IntervalBasedHierarchyBuilder.BuilderDataType.DOUBLE);

        String[] testData = {"3.3", "4.1", "5", "6.2", "7.232", "8.22"};

        HierarchyRequest hierarchyRequest = new HierarchyRequest(
                testData,
                basedHierarchyBuilder);
        ResponseEntity<Hierarchy> responseEntity = restTemplate.postForEntity("/api/hierarchy",hierarchyRequest, Hierarchy.class);
        assertNotNull(responseEntity);
        assertSame(HttpStatus.BAD_REQUEST , responseEntity.getStatusCode());

    }

    private static String[] getExampleData(){

        String[] result = new String[10];
        for (int i=0; i< result.length; i++){
            result[i] = String.valueOf(i);
        }
        return result;
    }
}