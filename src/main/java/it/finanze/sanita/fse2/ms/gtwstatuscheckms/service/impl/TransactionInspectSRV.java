/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.gtwstatuscheckms.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.LastTransactionEventDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.TransactionSearchDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.enums.EventTypeEnum;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions.BusinessException;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions.NoRecordFoundException;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.TransactionEventsETY;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.mongo.ITransactionInspectRepo;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.service.ITransactionInspectSRV;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionInspectSRV implements ITransactionInspectSRV {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 4006570317802497296L;

	@Autowired
	private ITransactionInspectRepo transactionInspectRepo;

	@Override
	public List<TransactionEventsETY> findEventsByWorkflowInstanceId(final String workflowInstanceId) {
		try {
			return transactionInspectRepo.findEventsByWorkflowInstanceId(workflowInstanceId);
		} catch(Exception ex) {
			log.error("Error while find events by workflow instance id : " , ex);
			throw new BusinessException(ex);
		}

	}

	@Override
	public List<TransactionEventsETY> searchGenericEvents(final TransactionSearchDTO searchParametersDTO) {
		try {
			return transactionInspectRepo.searchGenericEvents(searchParametersDTO);
		} catch(Exception ex) {
			log.error("Error while search generic events: {}" , ex.getMessage());
			throw new BusinessException(ex);
		}
	}
	
	@Override
	public List<TransactionEventsETY> findEventsByTraceId(final String traceId) {
		try {
			return transactionInspectRepo.findEventsByTraceId(traceId);
		} catch(Exception ex) {
			log.error("Error while find events by transaction id: {}" , ex.getMessage());
			throw new BusinessException(ex);
		}
		
	}

	public LastTransactionEventDTO searchLastEventByWorkflowInstanceId(String workflowInstanceId) {
		TransactionEventsETY lastEvent = transactionInspectRepo.findLastEventByWorkflowInstanceId(workflowInstanceId);
		if (lastEvent == null) {
			throw new NoRecordFoundException("Record non trovato");
		}
		String transactionStatus = "";
		switch (EventTypeEnum.valueOf(lastEvent.getEventType())) {
			case VALIDATION:
			case PUBLICATION:
			case SEND_TO_INI:
			case REPLACE:
				if (Objects.equals(lastEvent.getEventStatus(), "SUCCESS")) {
					transactionStatus = "IN CODA";
				} else {
					transactionStatus = lastEvent.getEventStatus();
				}
				break;
			case SEND_TO_EDS:
				transactionStatus = lastEvent.getEventStatus();
				break;
			default:
				break;
		}

		log.debug("Transaction status reached: {}", transactionStatus);

		return LastTransactionEventDTO.builder()
				.transactionStatus(transactionStatus)
				.lastTransactionData(lastEvent)
				.build();
	}
}
