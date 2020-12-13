package com.takeaway.challenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * @author Naveen Kumashi
 */

public class SpringdocConfig {
	@Bean
    public OpenAPI customOpenAPI(@Value("Employee Service") 
    	String appDesciption, @Value("0.0.1-SNAPSHOT") String appVersion) {
     return new OpenAPI()
          .info(new Info()
          .title("Employee Service API")
          .version(appVersion)
          .description(appDesciption)
          .termsOfService("http://swagger.io/terms/")
          .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
