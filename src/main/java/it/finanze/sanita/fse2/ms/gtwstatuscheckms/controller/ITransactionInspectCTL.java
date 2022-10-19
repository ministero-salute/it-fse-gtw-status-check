/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.gtwstatuscheckms.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.LastTransactionResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.error.base.ErrorResponseDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.TransactionInspectResDTO;

/**
*
* Controller for Transactions' Inspection.
*/
@RequestMapping(path = "/v1")
@Tag(name = "Servizio ispezione transazioni")
public interface ITransactionInspectCTL {

	@GetMapping(value = "/{workflowInstanceId}")
	@ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TransactionInspectResDTO.class)))
	@Operation(summary = "Recupero eventi del workflow instance id", description = "Recupera il dettaglio degli eventi tramite il workflow instance id.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Eventi recuperati", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TransactionInspectResDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Record not found", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))) })
	TransactionInspectResDTO getEvents(@PathVariable(required = true, name = "workflowInstanceId") @Schema(description = "Identificativo del workflow") @Size(min = 0, max = 1000) String workflowInstanceId,HttpServletRequest request);

	@GetMapping(value = "/last-state-trasaction/{workflowInstanceId}")
	@ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LastTransactionResponseDTO.class)))
	@Operation(summary = "Recupero dell'ultimo evento associato al workflow instance id", description = "Recupera il dettaglio dell'ultimo evento tramite il workflow instance id.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Eventi recuperati", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LastTransactionResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Record not found", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))) })
	LastTransactionResponseDTO getLastEvent(@PathVariable(required = true, name = "workflowInstanceId") @Schema(description = "Identificativo del workflow") @Size(min = 0, max = 1000) String workflowInstanceId, HttpServletRequest request);

	@GetMapping(value = "/search-events")
	@ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TransactionInspectResDTO.class)))
	@Operation(summary = "Ricerca eventi", description = "Permette il recupero degli eventi dati i filtri in input.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Eventi recuperati", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TransactionInspectResDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Record not found", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))) })
	TransactionInspectResDTO searchGenericEvents(LocalDate dataDa, LocalDate dataA, @RequestParam(required = false) @Size(min = 0, max = 100) String status, @RequestParam(required = false) @Size(min = 0, max = 100) String subject,
			@RequestParam(required = false) @Size(min = 0, max = 100) String organization, @RequestParam(required = false) @Size(min = 0, max = 100) String tipoAttivita,
			HttpServletRequest request);
	
	@GetMapping(value = "/search/{traceId}")
	@ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TransactionInspectResDTO.class)))
	@Operation(summary = "Recupero eventi del trace id", description = "Recupera il dettaglio degli eventi tramite il trace id.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Eventi recuperati", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TransactionInspectResDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Record not found", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))) })
	TransactionInspectResDTO getEventsByTraceId(@PathVariable(required = true, name = "traceId") String traceId,HttpServletRequest request);
	
}
