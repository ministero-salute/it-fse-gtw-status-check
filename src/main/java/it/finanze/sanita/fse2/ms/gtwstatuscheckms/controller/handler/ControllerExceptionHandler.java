package it.finanze.sanita.fse2.ms.gtwstatuscheckms.controller.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import brave.Tracer;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.ErrorResponseDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.LogTraceInfoDTO;
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
		log.info("HANDLER handleValidationException");
		Integer status = 400;
		ErrorResponseDTO out = new ErrorResponseDTO(getLogTraceInfo(), "/msg/validation","Errore di validazione" ,ex.getMessage(), status, "/msg/validation");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
		return new ResponseEntity<>(out, headers, HttpStatus.NOT_FOUND);
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
		log.info("HANDLER handleNoRecordFoundException");
		Integer status = 500;
		String detailedMessage = "Record non trovato, assicurarsi che i dati inseriti siano corretti";
		ErrorResponseDTO out = new ErrorResponseDTO(getLogTraceInfo(), "/msg/not-found", ex.getMessage(),detailedMessage , status, "/msg/not-found");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
		return new ResponseEntity<>(out, headers, HttpStatus.NOT_FOUND);
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
		log.info("HANDLER handleGenericException");
		Integer status = 500;

		ErrorResponseDTO out = new ErrorResponseDTO(getLogTraceInfo(), "/msg/generic-error", "Errore generico", "Errore generico", status, "/msg/generic-error");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);

		return new ResponseEntity<>(out, headers, status);
	}

	private LogTraceInfoDTO getLogTraceInfo() {
		return new LogTraceInfoDTO(
				tracer.currentSpan().context().spanIdString(), 
				tracer.currentSpan().context().traceIdString());
	}

}