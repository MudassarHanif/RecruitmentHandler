package com.hr.recruitmenthandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.hr.recruitmenthandler")
@EnableJpaRepositories
public class RecruitmenthandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecruitmenthandlerApplication.class, args);
	}
}
