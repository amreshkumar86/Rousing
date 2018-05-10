package com.oen.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ComponentScan(basePackages = {"com.oen.core", "com.oen.api"})
@EnableAutoConfiguration
@EnableJpaRepositories(
		basePackages = {"com.oen.core.domain"},
		entityManagerFactoryRef = "entityManagerFactory",
		transactionManagerRef = "transactionManager"
		)

public class ApiApplicationStart extends WebMvcConfigurerAdapter {
	
	 @Value("${token.header}")
	 private String tokenHeader;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplicationStart.class, args);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins("*")
		.allowedMethods("PUT", "DELETE", "OPTIONS", "GET", "POST")
		.allowedHeaders("Origin", "Content-Type", "Accept", tokenHeader)
		.exposedHeaders("Origin", "Content-Type", "Accept", tokenHeader)
		//.allowCredentials(false)
		.maxAge(3600);
	}
}
