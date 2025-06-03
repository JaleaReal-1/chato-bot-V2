package com.infotel.chatbot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // permite CORS para cualquier ruta estática en /modelos/**
        registry.addMapping("/modelos/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "OPTIONS");

        // también permitimos CORS para el API
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
