package com.oen.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.oen.core", "com.oen.scheduler"})
@EnableAutoConfiguration
@EnableJpaRepositories(
	basePackages = {"com.oen.core.domain"},
	entityManagerFactoryRef = "entityManagerFactory",
	transactionManagerRef = "transactionManager")
public class OenSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OenSchedulerApplication.class, args);
	}
}
