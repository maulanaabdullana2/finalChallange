package com.codeid.eshopay_backend.config;
import io.swagger.v3.oas.models.Components;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer removeSchemas() {
        return openApi -> {
            Components components = openApi.getComponents();
            if (components != null) {
                components.setSchemas(null); 
            }
        };
    }
}
