package br.univesp.diarioclasse.seguranca;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value("${cors.allowedOrigins}")
	private String corsOrigin;
	   
	@Override
	    
	public void addCorsMappings(CorsRegistry registry) {        
		registry.addMapping("/**")
		.allowedOrigins(corsOrigin)	        
		.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")
		.exposedHeaders("*"); 
	}
}
