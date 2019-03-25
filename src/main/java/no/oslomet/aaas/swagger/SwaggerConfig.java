package no.oslomet.aaas.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // private static final Contact DEFAULT_CONTACT = new Contact("Sondre Halvorsen", "https://github.com/sonhal", "s305349@oslomet.no");
    private static final Contact DEFAULT_CONTACT = null;
    private static final String ABOUT =
            "ARXaaS is a data anonymization and analyzation web service. " +
            "The service follows RESTful patterns";
    private static final ApiInfo DEFAULT_API_INFO =
            new ApiInfo("ARXaaS",
                    ABOUT,
                    "1.0",
                    "urn:tos",
                    DEFAULT_CONTACT,
                    "MIT",
                    "https://github.com/oslomet-arx-as-a-service/AaaS/blob/master/LICENCE",
                    new ArrayList());
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<>(Collections.singletonList("application/json"));

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}
