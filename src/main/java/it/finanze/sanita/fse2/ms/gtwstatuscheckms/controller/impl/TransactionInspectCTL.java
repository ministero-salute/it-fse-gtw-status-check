package it.finanze.sanita.fse2.ms.gtwstatuscheckms.controller.impl;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.controller.ITransactionInspectCTL;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.TransactionSearchDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.TransactionInspectResDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.TransactionSearchInspectDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions.NoRecordFoundException;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.TransactionEventsETY;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.service.facade.ITransactionInspectFacadeSRV;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.utility.ValidationUtility;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TransactionInspectCTL extends AbstractCTL implements ITransactionInspectCTL {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = -745041092971509373L;
	
	@Autowired
	private ITransactionInspectFacadeSRV transactionInspectSRV;
	
	@Override
	public TransactionInspectResDTO getEvents(String workflowInstanceId, HttpServletRequest request) {
		final List<TransactionEventsETY> result = transactionInspectSRV.findEventsByWorkflowInstanceId(workflowInstanceId);
		
		if(result==null || result.isEmpty()) {
			throw new NoRecordFoundException("Record non trovato");
		}
		return new TransactionInspectResDTO(getLogTraceInfo(), result); 
	}

	@Override
	public TransactionSearchInspectDTO searchGenericEvents(LocalDate dataDa, LocalDate dataA, String status, String subject,
			String organization, String tipoAttivita, HttpServletRequest request) {
		log.info("Search generic event START ");
		ValidationUtility.checkRange(dataDa, dataA);
		
		TransactionSearchDTO searchDTO = TransactionSearchDTO.builder().
				dataA(dataA).
				dataDa(dataDa).
				status(status).
				subject(subject).
				organization(organization).
				tipoAttivita(tipoAttivita).
				build();
		
		final List<TransactionEventsETY> result = transactionInspectSRV.searchGenericEvents(searchDTO);
		return new TransactionSearchInspectDTO(getLogTraceInfo(), result);
	}
 
}
