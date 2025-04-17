/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 * 
 * Copyright (C) 2023 Ministero della Salute
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package it.finanze.sanita.fse2.ms.gtwstatuscheckms.config.openapi;

import java.util.regex.Pattern;

import javax.xml.validation.Schema;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@SecurityScheme(
	name = "bearerAuth", 
	type = SecuritySchemeType.HTTP, 
	bearerFormat = "JWT", 
	scheme = "bearer", 
	description = "JWT Authorization header using the Bearer scheme. Example: \"Authorization: Bearer {token} [RFC8725](https://tools.ietf.org/html/RFC8725).\"")
public class OpenApiCFG {


	  public OpenApiCFG() {
	  }

	  @Bean
		public OpenApiCustomiser openApiCustomiser() {
			return openApi -> openApi.getComponents().getSchemas().values().forEach( s -> s.setAdditionalProperties(false));
		}
		
		@Bean
		public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
			return openApi -> {
				for (final Server server : openApi.getServers()) {
	                final Pattern pattern = Pattern.compile("^https://.*");
	                if (!pattern.matcher(server.getUrl()).matches()) {
	                    server.addExtension("x-sandbox", true);
	                }
	            }
				
				openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
					ApiResponses apiResponses = operation.getResponses();
					
					Schema<Object> errorResponseSchema = new Schema<>();
					errorResponseSchema.setName("Error");
					errorResponseSchema.set$ref("#/components/schemas/ErrorResponseDTO");
					MediaType media =new MediaType();
					media.schema(errorResponseSchema);
					ApiResponse apiResponse = new ApiResponse().description("default")
					        .content(new Content()
		                                .addMediaType(org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE, media));
					apiResponses.addApiResponse("default", apiResponse);
				}));
			};
		}
	}
