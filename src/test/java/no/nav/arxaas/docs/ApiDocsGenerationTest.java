package no.nav.arxaas.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.arxaas.GenerateIntegrationTestData;
import no.nav.arxaas.GenerateTestData;
import no.nav.arxaas.hierarchy.*;
import no.nav.arxaas.model.Request;

import no.nav.arxaas.hierarchy.RedactionBasedHierarchyBuilder.Order;
import no.nav.arxaas.hierarchy.interval.Interval;
import no.nav.arxaas.hierarchy.interval.IntervalBasedHierarchyBuilder;
import no.nav.arxaas.hierarchy.interval.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class ApiDocsGenerationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;


    private Request request;
    private Request analyzationRequest;
    private MockMultipartFile testData;
    private MockMultipartFile testMetaData;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)).build();
        request = GenerateTestData.zipcodeRequestPayload();
        analyzationRequest = GenerateTestData.zipcodeAnalyzationRequestPayload();

        testData = (MockMultipartFile) GenerateTestData.ageGenderZipcodeDatasetMultipartFile();

        String metaData = GenerateIntegrationTestData.testFormData_metadata_two_quasi();

        testMetaData = new MockMultipartFile("metadata", "","application/json", metaData.getBytes());
    }

    @Test
    void headersExample() throws Exception {
        this.mockMvc
                .perform(get("/api"))
                .andExpect(status().isOk())
                .andDo(document("root",
                        responseHeaders(
                                headerWithName("Content-Type").description("The Content-Type of the payload, e.g. `application/hal+json`")),
                        links(
                                linkWithRel("self").description("Link root resource"),
                                linkWithRel("anonymize").description("Link arxaas controller"),
                                linkWithRel("analyze").description("Link to analyze controller"))));
    }

    @Test
    void analyze_post() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String req = mapper.writeValueAsString(analyzationRequest);


        this.mockMvc.perform(post("/api/analyze")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req))
                .andExpect(status().isOk())
                .andDo(document("analyze-controller", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(subsectionWithPath("data").description("Dataset to be anonymized"),
                                subsectionWithPath("attributes").description("Attributes types of the dataset"),
                                subsectionWithPath("privacyModels").ignored(),
                                subsectionWithPath("suppressionLimit").ignored()
                        )));
    }

    @Test
    void anonymize_post() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String req = mapper.writeValueAsString(request);


        this.mockMvc.perform(post("/api/anonymize")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req))
                .andExpect(status().isOk())
                .andDo(document("anonymize-controller", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(subsectionWithPath("data").description("Dataset to be anonymized"),
                                subsectionWithPath("attributes").description("Attributes types and transformation models to be applied to the dataset"),
                                subsectionWithPath("privacyModels").description("Privacy Models to be applied to the dataset"),
                                subsectionWithPath("suppressionLimit").description("Suppression limit to be applied to the dataset")
                        )));
    }

    @Test
    void formdata_analyze_post() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/analyze/file")
                .file(testData)
                .file(testMetaData);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("formdata-analyze-controller",preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestParts(partWithName("file").description("Dataset CSV file to be analyzed"),
                                partWithName("metadata").description("Json object containing the metadata for the attributes types and transformation models to be applied to the dataset"))
                        ));
    }

    @Test
    void formdata_anonymize_post() throws Exception {

        MockMultipartFile genderHierarchy = (MockMultipartFile) GenerateTestData.genderHierarchyMultipartFile();
        MockMultipartFile zipcodeHierarchy = (MockMultipartFile) GenerateTestData.zipcodeHierarchyMultipartFile();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/anonymize/file")
                .file(testData)
                .file(testMetaData)
                .file(genderHierarchy)
                .file(zipcodeHierarchy);

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("formdata-anonymize-controller",preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestParts(partWithName("file").description("Dataset CSV file to be analyzed"),
                                partWithName("metadata").description("Json object containing the metadata for the attributes types and transformation models to be applied to the dataset"),
                                partWithName("hierarchies").description("Hierarchy CSV files containing the transformation models"))
                ));
    }



    @Test
    void hierarchy_inteval() throws Exception {

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
                new Range(81L, 100L, Long.MAX_VALUE / 4), IntervalBasedHierarchyBuilder.BuilderDataType.LONG);


        HierarchyRequest intervalHierarchyRequest = new HierarchyRequest(
                getExampleData(),
                basedHierarchyBuilder);


        ObjectMapper mapper = new ObjectMapper();
        String req = mapper.writeValueAsString(intervalHierarchyRequest);


        this.mockMvc.perform(post("/api/hierarchy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req))
                .andExpect(status().isOk())
                .andDo(document("hierarchy-controller-intervalbased", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                subsectionWithPath("column").description("List of values to create the hierarchy for"),
                                subsectionWithPath("builder").description("Object containing the different parameters on how to build the heirarchy for the dataset column"),
                                subsectionWithPath("builder.type").description("Hierarchy builder type to use when creating the hierarchy"),
                                subsectionWithPath("builder.intervals").description("List containing the different intervals to be generalized from and to"),
                                subsectionWithPath("builder.intervals[].from").description("Interval to generalize from"),
                                subsectionWithPath("builder.intervals[].to").description("Interval to generalize to"),
                                subsectionWithPath("builder.intervals[].label").description("Optional label to replace the default generalized interval values"),
                                subsectionWithPath("builder.levels").description("List containing parameters on how to generalize the created intervals"),
                                subsectionWithPath("builder.levels[].level").description("Transformation level to create a generalization"),
                                subsectionWithPath("builder.levels[].groups").description("List containing parameters on how to group the generalized column new values"),
                                subsectionWithPath("builder.levels[].groups[].grouping").description("Number of items to be grouped from the new generalized column values"),
                                subsectionWithPath("builder.levels[].groups[].label").description("Optional label to replace the default generalized value"),
                                subsectionWithPath("builder.lowerRange").description("Object containing parameters on how to define the lower range interval"),
                                subsectionWithPath("builder.lowerRange.snapFrom").description("Value to snap from when a lower value than this defined value is discoverd"),
                                subsectionWithPath("builder.lowerRange.bottomTopCodingFrom").description("Value to start bottom coding from"),
                                subsectionWithPath("builder.lowerRange.minMaxValue").description("If a value is discovered which is smaller than this value an exception will be raised."),
                                subsectionWithPath("builder.upperRange").description("Object containing parameters on how to define the upper range interval"),
                                subsectionWithPath("builder.upperRange.snapFrom").description("Value to snap from when a higher value than this defined value is discoverd"),
                                subsectionWithPath("builder.upperRange.bottomTopCodingFrom").description("Value to start top coding from"),
                                subsectionWithPath("builder.upperRange.minMaxValue").description("If a value is discovered which is larger than this value an exception will be raised."),
                                subsectionWithPath("builder.dataType").description("data type of the interval to generalize"))));
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
                .contentType(MediaType.APPLICATION_JSON)
                .content(req))
                .andExpect(status().isOk())
                .andDo(document("hierarchy-controller-redactionbased", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                subsectionWithPath("column").description("List of values to create the hierarchy for"),
                                subsectionWithPath("builder").description("Object containing the different parameters on how to build the heirarchy for the dataset column"),
                                subsectionWithPath("builder.type").description("Hierarchy builder type to use when creating the hierarchy"),
                                subsectionWithPath("builder.paddingCharacter").description("Character to use when padding the values"),
                                subsectionWithPath("builder.redactionCharacter").description("Character to use when redacting the values"),
                                subsectionWithPath("builder.paddingOrder").description("Direction in which to pad the values in the column"),
                                subsectionWithPath("builder.redactionOrder").description("Direction in which to redact symbols from the values in the column"))));
    }

    @Test
    void hierarchy_order() throws Exception {

        var testData = new String[]{"Oslo", "Bergen", "Stockholm", "London", "Paris"};

        Level nordicGroup = new Level(0, List.of(new Level.Group(3, "nordic-city")));
        Level midEuroGroup = new Level(0, List.of(new Level.Group(2, "mid-european-city")));

        OrderBasedHierarchyBuilder basedHierarchyBuilder = new OrderBasedHierarchyBuilder(List.of(nordicGroup, midEuroGroup));

        HierarchyRequest orderHierarchyRequest = new HierarchyRequest(
                testData,
                basedHierarchyBuilder);


        ObjectMapper mapper = new ObjectMapper();
        String req = mapper.writeValueAsString(orderHierarchyRequest);


        this.mockMvc.perform(post("/api/hierarchy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req))
                .andExpect(status().isOk())
                .andDo(document("hierarchy-controller-orderbased", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                subsectionWithPath("column").description("List of values to create the hierarchy for"),
                                subsectionWithPath("builder").description("Object containing the different parameters on how to build the heirarchy for the dataset column"),
                                subsectionWithPath("builder.type").description("Hierarchy builder type to use when creating the hierarchy"),
                                subsectionWithPath("builder.levels").description("List containing parameters on how to generalize the dataset column"),
                                subsectionWithPath("builder.levels[].level").description("Transformation level to create a generalization"),
                                subsectionWithPath("builder.levels[].groups").description("List containing parameters on how to group the dataset column"),
                                subsectionWithPath("builder.levels[].groups[].grouping").description("Number of items to be grouped from the dataset column values"),
                                subsectionWithPath("builder.levels[].groups[].label").description("Optional label to replace the default generalized value")
                        )));
    }

    @Test
    void hierarchy_date() throws Exception {
        var dateColumn = new String[]{"2020-07-16 15:28:024", "2019-07-16 16:38:025", "2019-07-16 17:48:025", "2019-07-16 18:48:025", "2019-06-16 19:48:025", "2019-06-16 20:48:025"};

        String stringDateFormat = "yyyy-MM-dd HH:mm:SSS";

        var granularities = List.of(
                DateBasedHierarchyBuilder.Granularity.SECOND_MINUTE_HOUR_DAY_MONTH_YEAR,
                DateBasedHierarchyBuilder.Granularity.MINUTE_HOUR_DAY_MONTH_YEAR,
                DateBasedHierarchyBuilder.Granularity.HOUR_DAY_MONTH_YEAR,
                DateBasedHierarchyBuilder.Granularity.DAY_MONTH_YEAR);

        DateBasedHierarchyBuilder builder = new DateBasedHierarchyBuilder(stringDateFormat, granularities);
        HierarchyRequest intervalHierarchyRequest = new HierarchyRequest(dateColumn, builder);
        ObjectMapper mapper = new ObjectMapper();
        String req = mapper.writeValueAsString(intervalHierarchyRequest);


        this.mockMvc.perform(post("/api/hierarchy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(req))
                .andExpect(status().isOk())
                .andDo(document("hierarchy-controller-datebased", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                subsectionWithPath("column").description("List of values to create the hierarchy for"),
                                subsectionWithPath("builder").description("Object containing the different parameters on how to build the heirarchy for the dataset column"),
                                subsectionWithPath("builder.type").description("Hierarchy builder type to use when creating the hierarchy"),
                                subsectionWithPath("builder.granularities").description("List of Date granularities to create the hierarchy after"),
                                subsectionWithPath("builder.dateFormat").description("SimpleDateFormat string describing how the date values should be parsed"))));
    }

    private static String[] getExampleData(){

        String[] result = new String[10];
        for (int i=0; i< result.length; i++){
            result[i] = String.valueOf(i);
        }
        return result;
    }
}
