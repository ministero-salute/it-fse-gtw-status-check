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
package it.finanze.sanita.fse2.ms.gtwstatuscheckms.controller.handler;

import static it.finanze.sanita.fse2.ms.gtwstatuscheckms.config.Constants.Properties.MS_NAME;
import static it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.error.ErrorBuilderDTO.createConstraintError;
import static it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.error.ErrorBuilderDTO.createGenericError;
import static it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.error.ErrorBuilderDTO.createNoRecordFoundError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.Tracer;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.LogTraceInfoDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.error.base.ErrorResponseDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions.NoRecordFoundException;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;

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
		log.error("HANDLER handleGenericException()", ex);
		// Create error DTO
		ErrorResponseDTO out = createGenericError(getLogTraceInfo(), ex);
		// Set HTTP headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
		// Bye bye
		return new ResponseEntity<>(out, headers, out.getStatus());
	}

	protected LogTraceInfoDTO getLogTraceInfo() {
		LogTraceInfoDTO out = new LogTraceInfoDTO(null, null);
		SpanBuilder spanbuilder = tracer.spanBuilder(MS_NAME);
		
		if (spanbuilder != null) {
			out = new LogTraceInfoDTO(
					spanbuilder.startSpan().getSpanContext().getSpanId(), 
					spanbuilder.startSpan().getSpanContext().getTraceId());
		}
		return out;
	}
}