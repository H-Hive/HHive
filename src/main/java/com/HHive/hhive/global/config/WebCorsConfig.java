package com.HHive.hhive.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebCorsConfig implements WebMvcConfigurer {

    private final String FRONT_URL = "http://hhive.s3-website.ap-northeast-2.amazonaws.com";

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        String LOCALHOST_URL_FOR_TEST = "http://localhost:8082";

        String FRONT_URL = "http://hhive.s3-website.ap-northeast-2.amazonaws.com";

        registry.addMapping("/**")
//                .allowedOrigins(FRONT_URL)
                .allowedOrigins(LOCALHOST_URL_FOR_TEST)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
                .allowedHeaders("Origin", "Content-Type", "Accept")
                .allowCredentials(true)
                .maxAge(3600);
    }

}
