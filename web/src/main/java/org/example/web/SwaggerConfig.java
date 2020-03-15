package org.example.web;

import org.example.web.annotation.DepartmentSwagger;
import org.example.web.annotation.UserSwagger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static springfox.documentation.builders.PathSelectors.any;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    protected Set<String> jsonProduces = newHashSet("application/json");
    protected Set<String> streamProduces = newHashSet("application/octet-stream");

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.MODEL)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }

    private final List<Parameter> globalParameters = newArrayList(
            new ParameterBuilder()
                    .name("pm-token")
                    .description("token")
                    .modelRef(new ModelRef("string"))
                    .parameterType("header")
                    .required(false)
                    .build());

    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("人员")
                .apiInfo(userInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(UserSwagger.class))
                .paths(any())
                .build()
                .genericModelSubstitutes(ResponseEntity.class)
                .globalOperationParameters(globalParameters)
                .useDefaultResponseMessages(false)
                .enableUrlTemplating(false)
                .produces(jsonProduces);
    }


    private ApiInfo userInfo() {
        return new ApiInfo("人员",
                "人员管理",
                "1.0.0", "no", null, "", "", new ArrayList<>());
    }

    @Bean
    public Docket departmentApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("部门")
                .apiInfo(departmentInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(DepartmentSwagger.class))
                .paths(any())
                .build()
                .genericModelSubstitutes(ResponseEntity.class)
                .globalOperationParameters(globalParameters)
                .useDefaultResponseMessages(false)
                .enableUrlTemplating(false)
                .produces(jsonProduces);
    }


    private ApiInfo departmentInfo() {
        return new ApiInfo("部门",
                "部门管理",
                "1.0.0", "no", null, "", "", new ArrayList<>());
    }
}