package com.squares;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring boot entry point class
 * 
 * @author svingilis
 */
@SpringBootApplication
public class Application {

    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("GET", "POST", "DELETE");
            }
            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                // use Accept header instead
                configurer.favorParameter(false);
                configurer.favorPathExtension(false);
            }
        };
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}
