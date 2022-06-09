package it.finanze.sanita.fse2.ms.gtwstatuscheckms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.config.Constants;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.config.LocalDateConverter;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.TransactionSearchDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.TransactionInspectResDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions.BusinessException;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.TransactionEventsETY;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.service.ITransactionInspectSRV;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.service.impl.TransactionInspectSRV;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = {Constants.ComponentScan.BASE})
@ActiveProfiles(Constants.Profile.TEST)
public class TransactionInspectTest extends AbstractTest {
	//TODO - Fare test transaction inspect
	
	//TODO - Magari prevedere un abstract per fare la chiamata rest una singola volta ed 
	//usarla in tutti e due i casi
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	ITransactionInspectSRV service;
	
	@Autowired
	TransactionInspectSRV srv;
	
	LocalDateConverter dateConverter = new LocalDateConverter();
	
	@BeforeEach
	void init() throws ParseException {
		cleanupCollection();
		List<TransactionEventsETY> transactionEventsETYList = initList();
		initCollection(transactionEventsETYList);
	}

	/**
	 * TransactionId1 => expected results 2
	 */
	@Test
	@DisplayName("Test happy path -> 200")
	void happyPath() {
		//Sviluppare un metodo che permette l'invocazione del ctl (Ad esempio con restTemplate, vedere dispatcher - publicationTest)
		//TransactionInspectResDTO -> assertEquals(sizeRecuperataDalDB , sizeCheRestituisceL'EP)
		ResponseEntity<TransactionInspectResDTO> response = getTransactionEvents(TestConstants.workflowInstanceId);
		assertEquals(200, response.getStatusCodeValue());
		assertNotEquals(null, response);
		assertNotEquals(null, response.getBody());
		assertNotEquals(null, response.getBody().getTransactionData());
		assertNotEquals(0, response.getBody().getTransactionData().size());
		assertEquals(4, response.getBody().getTransactionData().size());
	}
	
	@Test
	@DisplayName("Test no record found")
	void noRecordFoundTest() throws Exception{
		
		assertThrows(HttpClientErrorException.class, () -> getTransactionEvents(TestConstants.transactionIDNoFound) );
		
	}

	@Test
	@DisplayName("Test generic events -> 200 OK")
	void searchGenericEventsSuccessTest() {
		// Search by organization 1
		ResponseEntity<TransactionInspectResDTO> response = searchGenericTransactionEvents(
				TestConstants.dateFrom,
				TestConstants.dateTo,
				TestConstants.organization1,
				null,
				null,
				null,
				null
		);
		assertEquals(200, response.getStatusCodeValue());
		assertNotEquals(null, response);
		assertNotEquals(null, response.getBody());
		assertNotEquals(null, response.getBody().getTransactionData());
		assertNotEquals(0, response.getBody().getTransactionData().size());
		assertEquals(2, response.getBody().getTransactionData().size());

		// Search by activityType 1
		response = searchGenericTransactionEvents(
				TestConstants.dateFrom,
				TestConstants.dateTo,
				null,
				TestConstants.activityType1,
				null,
				null,
				null
		);
		assertEquals(200, response.getStatusCodeValue());
		assertNotEquals(null, response);
		assertNotEquals(null, response.getBody());
		assertNotEquals(null, response.getBody().getTransactionData());
		assertNotEquals(0, response.getBody().getTransactionData().size());
		assertEquals(2, response.getBody().getTransactionData().size());

		// Search by documentType 1
		response = searchGenericTransactionEvents(
				TestConstants.dateFrom,
				TestConstants.dateTo,
				null,
				null,
				null,
				null,
				null
		);
		assertEquals(200, response.getStatusCodeValue());
		assertNotEquals(null, response);
		assertNotEquals(null, response.getBody());
		assertNotEquals(null, response.getBody().getTransactionData());
		assertNotEquals(0, response.getBody().getTransactionData().size());
		assertEquals(4, response.getBody().getTransactionData().size());

		// Search by status1
		response = searchGenericTransactionEvents(
				TestConstants.dateFrom,
				TestConstants.dateTo,
				null,
				null,
				null,
				TestConstants.eventStatus1,
				null
		);
		assertEquals(200, response.getStatusCodeValue());
		assertNotEquals(null, response);
		assertNotEquals(null, response.getBody());
		assertNotEquals(null, response.getBody().getTransactionData());
		assertNotEquals(0, response.getBody().getTransactionData().size());
		assertEquals(4, response.getBody().getTransactionData().size());

		// Search by subject1
		response = searchGenericTransactionEvents(
				TestConstants.dateFrom,
				TestConstants.dateTo,
				null,
				null,
				null,
				null,
				TestConstants.subject1
		);
		assertEquals(200, response.getStatusCodeValue());
		assertNotEquals(null, response);
		assertNotEquals(null, response.getBody());
		assertNotEquals(null, response.getBody().getTransactionData());
		assertNotEquals(0, response.getBody().getTransactionData().size());
		assertEquals(2, response.getBody().getTransactionData().size());
	}
	
	@Test
	@DisplayName("test generic events -> 404")
	void searchGenericTransactionEventsException() throws Exception {
		//Data Da sia maggiore della data odierna
		TransactionSearchDTO request = new TransactionSearchDTO();
		request.setDataDa(LocalDate.parse("2022-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		request.setDataA(LocalDate.parse("2022-05-26", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		
		try {
			searchGenericTransactionEvents(request.getDataDa(), request.getDataA(), null, null, null, null, null);
		} catch(HttpClientErrorException ex) {
			Assertions.assertEquals(ex.getRawStatusCode(), 404);
		}
		assertThrows(HttpClientErrorException.class, () -> searchGenericTransactionEvents(request.getDataDa(), request.getDataA(), null, null, null, null, null) );
		
		//Data Da sia maggiore della data A
		request.setDataDa(LocalDate.parse("2022-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		request.setDataA(LocalDate.parse("2022-10-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		
		try {
			searchGenericTransactionEvents(request.getDataDa(), request.getDataA(), null, null, null, null, null);
		} catch(HttpClientErrorException ex) {
			Assertions.assertEquals(ex.getRawStatusCode(), 404);
		}
		assertThrows(HttpClientErrorException.class, () -> searchGenericTransactionEvents(request.getDataDa(), request.getDataA(), null, null, null, null, null) );
		
		//Data A sia maggiore della data odierna
		request.setDataDa(LocalDate.parse("2022-05-26", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		request.setDataA(LocalDate.parse("2022-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		
		try {
			searchGenericTransactionEvents(request.getDataDa(), request.getDataA(), null, null, null, null, null);
		} catch(HttpClientErrorException ex) {
			Assertions.assertEquals(ex.getRawStatusCode(), 404);
		}

		assertThrows(HttpClientErrorException.class, () -> searchGenericTransactionEvents(request.getDataDa(), request.getDataA(), null, null, null, null, null) );
	}
	
	
	@Test
	@DisplayName("test date handler format exception")
	void dateConverterException() {
		LocalDate convertDate = null;
		
		// Data null
		try {
			convertDate = dateConverter.convert(null);

		} catch (Exception ex) {
			Assertions.assertNull(convertDate);
		}
		assertThrows(BusinessException.class, () -> dateConverter.convert(null));
		
		// Data del formato errato
		try {
			convertDate = dateConverter.convert("pippo");

		} catch (Exception ex) {
			Assertions.assertNull(convertDate);
		}
		assertThrows(BusinessException.class, () -> dateConverter.convert("pippo"));
		
	}
	
	@Test
	@DisplayName("test date handler format ok")
	void dateConverterOK() {
		
		// Data convertita correttamente	
		LocalDate convertDate = dateConverter.convert("2021-04-10");
		Assertions.assertNotNull(convertDate);	
		
		
		
	}
	
	
}
