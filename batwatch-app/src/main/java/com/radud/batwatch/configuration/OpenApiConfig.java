package com.radud.batwatch.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Batwatch API")
                        .version("0.0.1")
                        .description("API for managing reports of bat sightings, along with tracking and resolution of conflicts.")
                        .contact(new Contact().name("Radu Dumitru"))
                );
    }
}
