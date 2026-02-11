package com.SCM.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// ::Made by VivekG

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        Path imagesDir = Paths.get("")
                .toAbsolutePath()
                .resolve("bin/webapps/scm/WEB-INF/classes/static")
                .normalize();
        
        registry.addResourceHandler("/api/images/**")
                .addResourceLocations(imagesDir.toUri().toString())
                .setCachePeriod(3600);
    }
}
