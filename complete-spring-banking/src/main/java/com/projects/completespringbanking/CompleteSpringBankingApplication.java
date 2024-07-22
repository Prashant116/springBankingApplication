package com.projects.completespringbanking;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title="Secure Banking Application",
				description = "Secured restful banking API",
				version = "1.0",
				contact =@Contact(
						name = "Prashant Puri",
						email = "Prashantpuri116@gmail.com",
						url="https://www.puriprashant.com.np"
				),
				license = @License(
						name="Prashant Puri @2024",
						url="https://www.puriprashant.com.np"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Java Restful API for banking application",
				url="https://www.puriprashant.com.np"
		)
)
public class CompleteSpringBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompleteSpringBankingApplication.class, args);
	}

}

