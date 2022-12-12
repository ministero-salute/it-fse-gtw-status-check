/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.gtwstatuscheckms.controller.impl;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.controller.ITransactionInspectCTL;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.LastTransactionEventDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.TransactionSearchDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.LastTransactionResponseDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.TransactionInspectResDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions.NoRecordFoundException;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.TransactionEventsETY;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.service.facade.ITransactionInspectFacadeSRV;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.utility.DateUtility;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TransactionInspectCTL extends AbstractCTL implements ITransactionInspectCTL {

	@Autowired
	private ITransactionInspectFacadeSRV transactionInspectSRV;

	@Override
	public TransactionInspectResDTO getEvents(String workflowInstanceId, HttpServletRequest request) {
		log.info("Get events START");
		final List<TransactionEventsETY> result = transactionInspectSRV.findEventsByWorkflowInstanceId(workflowInstanceId);

		if(result==null || result.isEmpty()) {
			throw new NoRecordFoundException("Record non trovato");
		}
		return new TransactionInspectResDTO(getLogTraceInfo(), result);
	}

	@Override
	public LastTransactionResponseDTO getLastEvent(String workflowInstanceId, HttpServletRequest request) {
		log.info("Search last event START");
		final LastTransactionEventDTO lastEvent = transactionInspectSRV.searchLastEventByWorkflowInstanceId(workflowInstanceId);
		return new LastTransactionResponseDTO(getLogTraceInfo(), lastEvent.getTransactionStatus(), lastEvent.getLastTransactionData());
	}

	@Override
	public TransactionInspectResDTO searchGenericEvents(LocalDate dataDa, LocalDate dataA, String status, String subject,
			String organization, String tipoAttivita, HttpServletRequest request) {
		log.info("Search generic event START");
		DateUtility.checkRange(dataDa, dataA);
		
		TransactionSearchDTO searchDTO = TransactionSearchDTO.builder().
				dataA(dataA).
				dataDa(dataDa).
				status(status).
				subject(subject).
				organization(organization).
				tipoAttivita(tipoAttivita).
				build();
		
		final List<TransactionEventsETY> result = transactionInspectSRV.searchGenericEvents(searchDTO);
		return new TransactionInspectResDTO(getLogTraceInfo(), result);
	}
 
	@Override
	public TransactionInspectResDTO getEventsByTraceId(String traceId, HttpServletRequest request) {
		final List<TransactionEventsETY> result = transactionInspectSRV.findEventsByTraceId(traceId);
		
		if(result == null || result.isEmpty()) {
			throw new NoRecordFoundException("Record non trovato");
		}
		return new TransactionInspectResDTO(getLogTraceInfo(), result); 
	}
}
