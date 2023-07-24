package com.connect.sport.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class AuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://localhost:3000");
		config.addAllowedOrigin("http://localhost:4200");
		config.addAllowedOrigin("http://localhost:19000");
		config.addAllowedOrigin("exp://192.168.0.111:19000");
		config.addAllowedHeader("Origin");
		config.addAllowedHeader("Access-Control-Allow-Origin");
		config.addAllowedHeader("Content-Type");
		config.addAllowedHeader("Accept");
		config.addAllowedHeader("Jwt-Token");
		config.addAllowedHeader("Authorization");
		config.addAllowedHeader("Origin, Accept");
		config.addAllowedHeader("X-Requested-With");
		config.addAllowedHeader("Access-Control-Request-Method");
		config.addAllowedHeader("Access-Control-Request-Headers");
		config.addExposedHeader("Origin");
		config.addExposedHeader("Content-Type");
		config.addExposedHeader("Accept");
		config.addExposedHeader("Jwt-Token");
		config.addExposedHeader("Authorization");
		config.addExposedHeader("Access-Control-Allow-Origin");
		config.addExposedHeader("Access-Control-Allow-Credentials");
		config.addExposedHeader("Filename");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("PATCH");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("OPTIONS");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
