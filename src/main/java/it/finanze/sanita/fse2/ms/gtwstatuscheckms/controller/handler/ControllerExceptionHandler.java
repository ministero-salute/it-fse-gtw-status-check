/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.gtwstatuscheckms.controller.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import brave.Tracer;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.error.base.ErrorResponseDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.LogTraceInfoDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions.NoRecordFoundException;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;

import static it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.error.ErrorBuilderDTO.*;
import static it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.error.ErrorBuilderDTO.createGenericError;

/**
 *	Exceptions Handler.
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {


	/**
	 * Tracker log.
	 */
	@Autowired
	private Tracer tracer;

	/**
	 * No record found exception.
	 * 
	 * @param ex		exception
	 * @param request	request
	 * @return			
	 */
	@ExceptionHandler(value = {ValidationException.class})
	protected ResponseEntity<ErrorResponseDTO> handleValidationException(final ValidationException ex, final WebRequest request) {
		// Log me
		log.warn("HANDLER handleValidationException()");
		log.error("HANDLER handleValidationException()", ex);
		// Create error DTO
		ErrorResponseDTO out = createConstraintError(getLogTraceInfo(), ex);
		// Set HTTP headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
		// Bye bye
		return new ResponseEntity<>(out, headers, out.getStatus());
	}
	
	/**
	 * No record found exception.
	 * 
	 * @param ex		exception
	 * @param request	request
	 * @return			
	 */
	@ExceptionHandler(value = {NoRecordFoundException.class})
	protected ResponseEntity<ErrorResponseDTO> handleNoRecordFoundException(final NoRecordFoundException ex, final WebRequest request) {
		// Log me
		log.warn("HANDLER handleNoRecordFoundException()");
		log.error("HANDLER handleNoRecordFoundException()", ex);
		// Create error DTO
		ErrorResponseDTO out = createNoRecordFoundError(getLogTraceInfo(), ex);
		// Set HTTP headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
		// Bye bye
		return new ResponseEntity<>(out, headers, out.getStatus());
	}

	/**
	 * Management generic exception.
	 * 
	 * @param ex		exception
	 * @param request	request
	 * @return			
	 */
	@ExceptionHandler(value = {Exception.class})
	protected ResponseEntity<ErrorResponseDTO> handleGenericException(final Exception ex, final WebRequest request) {
		// Log me
		log.warn("HANDLER handleGenericException()");
		log.error("HANDLER handleGenericException()", ex);
		// Create error DTO
		ErrorResponseDTO out = createGenericError(getLogTraceInfo(), ex);
		// Set HTTP headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
		// Bye bye
		return new ResponseEntity<>(out, headers, out.getStatus());
	}

	private LogTraceInfoDTO getLogTraceInfo() {
		return new LogTraceInfoDTO(
				tracer.currentSpan().context().spanIdString(), 
				tracer.currentSpan().context().traceIdString());
	}

}