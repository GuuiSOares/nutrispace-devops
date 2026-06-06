package com.nutrispace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI nutriSpaceOpenAPI() {
		final String schemeName = "bearerAuth";
		return new OpenAPI()
				.info(new Info()
						.title("NutriSpace API")
						.description("API REST para monitoramento de estufas biológicas espaciais — Global Solution FIAP")
						.version("1.0.0")
						.contact(new Contact().name("NutriSpace Team").email("contato@nutrispace.dev")))
				.addSecurityItem(new SecurityRequirement().addList(schemeName))
				.components(new Components().addSecuritySchemes(schemeName,
						new SecurityScheme()
								.name(schemeName)
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")));
	}
}
