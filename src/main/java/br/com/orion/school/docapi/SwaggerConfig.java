package br.com.orion.school.docapi;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        .select()
            .apis(RequestHandlerSelectors.basePackage("br.com.orion.school.endpoint"))
                .paths(PathSelectors.regex("/v1.*")).build()
                .globalOperationParameters(Collections.singletonList(
                        new ParameterBuilder()
                                .name("Authorization")
                                .description("Bearer token")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(true)
                .build()
            ))
        .apiInfo(metaData());
    }
    
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
        .title("Orion API Documentation")
        .description("First documentation using Swagger")
        .version("1.0.0")
        .build();

    }

}