package no.oslomet.aaas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.oslomet.aaas.GenerateTestData;
import no.oslomet.aaas.model.Request;

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
class ApiDocsGeneration {

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
                .andDo(document("analyze-controller", preprocessRequest(prettyPrint()),
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
                .andDo(document("anonymize-controller", preprocessRequest(prettyPrint()),
                        requestFields(subsectionWithPath("data").description("Dataset to be anonymized"),
                                subsectionWithPath("attributes").description("Attributes of the dataset"),
                                subsectionWithPath("privacyModels").description("Privacy Models to be applied to the dataset")
                        )));
    }
}
