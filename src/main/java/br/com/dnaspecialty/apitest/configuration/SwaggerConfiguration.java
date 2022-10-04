package br.com.dnaspecialty.apitest.configuration;


import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private static Predicate<RequestHandler> apis() {
        return RequestHandlerSelectors.basePackage("br.com.dnaspacialty.apitest.controller");
    }

    private static ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Api DnaSpecialty Backend")
                .contact(new Contact("API Test DNA Specialty", "http://www.dnaspecialty.com.br/",
                        "contato@frinfo.com.br"
                ))
                .description("API developed for evaluation in the company DNA Specialty.")
                .version("1.0.0").build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(SwaggerConfiguration.defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    private static List<SecurityReference> defaultAuth() {
        return Lists.newArrayList(new SecurityReference("Autorizacao", new AuthorizationScope[0]));
    }

}
