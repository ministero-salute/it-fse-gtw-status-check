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
			case SEND_TO_EDS:
				if (Objects.equals(lastEvent.getEventStatus(), "SUCCESS")) {
					transactionStatus = "IN CODA";
				} else {
					transactionStatus = lastEvent.getEventStatus();
				}
				break;
			case EDS_WORKFLOW:
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
	
	@Override
	public List<TransactionEventsETY> searchPublicationByIdDocumento(final String idDocumento) {
		try {
			return transactionInspectRepo.findPublicationByIdDocumento(idDocumento);
		} catch(Exception ex) {
			log.error("Error while search generic events: {}" , ex.getMessage());
			throw new BusinessException(ex);
		}
	}
}
