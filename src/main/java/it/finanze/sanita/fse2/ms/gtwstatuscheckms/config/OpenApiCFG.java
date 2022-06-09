package it.finanze.sanita.fse2.ms.gtwstatuscheckms.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
	info = @Info(
			extensions = {
				@Extension(properties = {
					@ExtensionProperty(name = "x-api-id", value = "1"),
					@ExtensionProperty(name = "x-summary", value = "Search for transactions by id or query parameters")
				})
			},
			title = "Gateway Status Checker",
			version = "1.0.0", 
			description = "Search for transactions by id or query parameters",
			termsOfService = "${docs.info.termsOfService}", 
			contact = @Contact(name = "${docs.info.contact.name}", url = "${docs.info.contact.url}", email = "${docs.info.contact.mail}")
	),
	servers = {
		@Server(
			description = "Gateway Status Checker Development URL",
			url = "http://localhost:8017",
			extensions = {
				@Extension(properties = {
					@ExtensionProperty(name = "x-sandbox", parseValue = true, value = "true")
				})
			}
		)
	})
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OpenApiCFG {

	public OpenApiCFG() {
	}

	@Bean
	public OpenAPI customOpenAPI() {
		final String securitySchemeName = "bearerAuth";
		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(
						new Components().addSecuritySchemes(securitySchemeName,
								new io.swagger.v3.oas.models.security.SecurityScheme()
										.name(securitySchemeName)
										.scheme("bearer")
										.bearerFormat("JWT")));
	}

	@Bean
	public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
				ApiResponses apiResponses = operation.getResponses();

				Schema<Object> errorResponseSchema = new Schema<>();
				errorResponseSchema.setName("Error");
				errorResponseSchema.set$ref("#/components/schemas/ErrorResponseDTO");
				MediaType media = new MediaType();
				media.schema(errorResponseSchema);
				ApiResponse apiResponse = new ApiResponse().description("default")
						.content(new Content()
								.addMediaType(org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE,
										media));
				apiResponses.addApiResponse("default", apiResponse);
			}));
		};
	}
}
