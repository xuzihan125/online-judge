package com.oj.ojBackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Knife4j api documentation config
 * https://doc.xiaominfo.com/knife4j/documentation/get_start.html
 */
@Configuration
@EnableSwagger2
@Profile({"dev", "test"})
public class Knife4jConfig {

    @Bean
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("api document")
                        .description("oj-backend")
                        .version("1.0")
                        .build())
                .select()
                // set controller scan directory
                .apis(RequestHandlerSelectors.basePackage("com.oj.ojBackend.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}