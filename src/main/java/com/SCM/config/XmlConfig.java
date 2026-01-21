package com.SCM.config;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class XmlConfig implements WebMvcConfigurer{

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		
		configurer.favorParameter(true).parameterName("mediaType")
		          .defaultContentType(MediaType.APPLICATION_JSON)
		          .mediaType("json", MediaType.APPLICATION_JSON)
		          .mediaType("xml", MediaType.APPLICATION_XML);
		
	}
	
	

}
