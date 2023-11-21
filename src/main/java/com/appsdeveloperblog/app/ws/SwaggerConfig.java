package com.appsdeveloperblog.app.ws;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
@EnableWebMvc
//@EnableSwagger2
@Configuration
public class SwaggerConfig {
    Contact contact = new Contact(
            "Salim Zakaeri",
            "https://muhammadsalimzakari.netlify.app/",
            "salimzakari6@gmail.com"
    );

    List<VendorExtension> vendorExtensions = new ArrayList<>();

    @Bean
    public Docket apiDocket() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .protocols(new HashSet<>(Arrays.asList("HTTP","HTTPs")))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.appsdeveloperblog.app.ws")).paths(PathSelectors.any())
                .build();

        return docket;

    }
}
