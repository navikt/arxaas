package no.oslomet.aaas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.Request;

import no.oslomet.aaas.model.hierarchy.*;
import no.oslomet.aaas.model.hierarchy.RedactionBasedHierarchyBuilder.Order;
import no.oslomet.aaas.model.hierarchy.interval.Interval;
import no.oslomet.aaas.model.hierarchy.interval.IntervalBasedHierarchyBuilder;
import no.oslomet.aaas.model.hierarchy.interval.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class ApiDocsGenerationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;


    private Request request;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)).build();
        request = GenerateTestData.zipcodeRequestPayload();
    }

    @Test
    void headersExample() throws Exception {
        this.mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andDo(document("root",
                        responseHeaders(
                                headerWithName("Content-Type").description("The Content-Type of the payload, e.g. `application/hal+json`"))));
    }

    @Test
    void analyze_post() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String req = mapper.writeValueAsString(request);


        this.mockMvc.perform(post("/api/analyze")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(req))
                .andExpect(status().isOk())
                .andDo(document("analyze-controller", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(subsectionWithPath("data").description("Dataset to be anonymized"),
                                subsectionWithPath("attributes").description("Attributes of the dataset"),
                                subsectionWithPath("privacyModels").ignored()
                        )));
    }

    @Test
    void anonymize_post() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String req = mapper.writeValueAsString(request);


        this.mockMvc.perform(post("/api/anonymize")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(req))
                .andExpect(status().isOk())
                .andDo(document("anonymize-controller", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(subsectionWithPath("data").description("Dataset to be anonymized"),
                                subsectionWithPath("attributes").description("Attributes of the dataset"),
                                subsectionWithPath("privacyModels").description("Privacy Models to be applied to the dataset")
                        )));
    }


    @Test
    void hierarchy_inteval() throws Exception {
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
                new Range(81L, 100L, Long.MAX_VALUE / 4));


        HierarchyRequest intervalHierarchyRequest = new HierarchyRequest(
                getExampleData(),
                basedHierarchyBuilder);


        ObjectMapper mapper = new ObjectMapper();
        String req = mapper.writeValueAsString(intervalHierarchyRequest);


        this.mockMvc.perform(post("/api/hierarchy")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(req))
                .andExpect(status().isOk())
                .andDo(document("hierarchy-controller-intervalbased", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

    @Test
    void hierarchy_reduction() throws Exception {

        RedactionBasedHierarchyBuilder basedHierarchyBuilder
                = new RedactionBasedHierarchyBuilder(
                        ' ',
                '*',
                Order.RIGHT_TO_LEFT,
                Order.RIGHT_TO_LEFT);


        HierarchyRequest intervalHierarchyRequest = new HierarchyRequest(
                getExampleData(),
                basedHierarchyBuilder);


        ObjectMapper mapper = new ObjectMapper();
        String req = mapper.writeValueAsString(intervalHierarchyRequest);


        this.mockMvc.perform(post("/api/hierarchy")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(req))
                .andExpect(status().isOk())
                .andDo(document("hierarchy-controller-reductionbased", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

    private static String[] getExampleData(){

        String[] result = new String[10];
        for (int i=0; i< result.length; i++){
            result[i] = String.valueOf(i);
        }
        return result;
    }
}
