
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
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.LogTraceInfoDTO;
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
		final LogTraceInfoDTO traceInfoDTO = getLogTraceInfo();
		
		log.info("[START] {}() with arguments {}={}, {}={}", "getEvents",
    			"traceId", traceInfoDTO.getTraceID(),
        		"wif", workflowInstanceId
        		);

		final List<TransactionEventsETY> result = transactionInspectSRV.findEventsByWorkflowInstanceId(workflowInstanceId);

		if(result==null || result.isEmpty()) {
			throw new NoRecordFoundException("Record non trovato");
		}
		
		log.info("[EXIT] {}() with arguments {}={}, {}={}", "getEvents",
    			"traceId", traceInfoDTO.getTraceID(),
        		"wif", workflowInstanceId
        		);
		return new TransactionInspectResDTO(getLogTraceInfo(), result);
	}

	@Override
	public LastTransactionResponseDTO getLastEvent(String workflowInstanceId, HttpServletRequest request) {
		final LogTraceInfoDTO traceInfoDTO = getLogTraceInfo();

		log.info("[START] {}() with arguments {}={}, {}={}", "getLastEvent",
    			"traceId", traceInfoDTO.getTraceID(),
        		"wif", workflowInstanceId
        		);
		final LastTransactionEventDTO lastEvent = transactionInspectSRV.searchLastEventByWorkflowInstanceId(workflowInstanceId);
		
		log.info("[EXIT] {}() with arguments {}={}, {}={}", "getLastEvent",
    			"traceId", traceInfoDTO.getTraceID(),
        		"wif", workflowInstanceId
        		);
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
	
	@Override
	public TransactionInspectResDTO searchByIdDocumento(String idDocumento, HttpServletRequest request) {
		log.info("Search generic event START");
		
		final List<TransactionEventsETY> result = transactionInspectSRV.searchByIdDocumento(idDocumento);
		
		if(result.isEmpty()) {
			throw new NoRecordFoundException("Id documento non presente");
		}
		return new TransactionInspectResDTO(getLogTraceInfo(), result);
	}
}
