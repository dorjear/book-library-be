package com.tonyking.sample.booklibrary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Value("${bookapp.cors.origins}")
    private String[] allowedOrigins; // Adjust based on your needs

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Set the path pattern to apply CORS configuration
                        .allowedOrigins(allowedOrigins) // Set allowed origins
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Set allowed methods
                        .allowedHeaders("*") // Set allowed headers
                        .allowCredentials(true) // Whether to send cookies
                        .maxAge(3600); // Max age before a preflight request needs to be made again
            }
        };
    }
}
