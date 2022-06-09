package it.finanze.sanita.fse2.ms.gtwstatuscheckms.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.ErrorResponseDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.TransactionInspectResDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.TransactionSearchInspectDTO;

/**
*
* Controller for Transactions' Inspection.
*/
@RequestMapping(path = "/v1.0.0")
@Tag(name = "Servizio ispezione transazioni")
public interface ITransactionInspectCTL {
 
	@RequestMapping(value = "/{workflowInstanceId}",method = RequestMethod.GET)
	@ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TransactionInspectResDTO.class)))
	@Operation(summary = "Recupero eventi della transazione", description = "Recupera il dettaglio degli eventi tramite l'id della transazione.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Eventi recuperati", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TransactionInspectResDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Record not found", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))) })
	TransactionInspectResDTO getEvents(@PathVariable(required = true, name = "workflowInstanceId") String workflowInstanceId,HttpServletRequest request);

	@RequestMapping(value = "/search-events",method = RequestMethod.GET)
	@ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TransactionSearchInspectDTO.class)))
	@Operation(summary = "Ricerca eventi", description = "Permette il recupero degli eventi dati i filtri in input.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Eventi recuperati", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TransactionSearchInspectDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Record not found", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))) })
	TransactionSearchInspectDTO searchGenericEvents(LocalDate dataDa, LocalDate dataA, @RequestParam(required = false) String status, @RequestParam(required = false) String subject,
			@RequestParam(required = false)String organization, @RequestParam(required = false)String tipoAttivita,
			HttpServletRequest request);
	
}
