
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
package it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.mongo.impl;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.config.DbPropertyCFG;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.TransactionSearchDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions.BusinessException;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.TransactionEventsETY;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.mongo.ITransactionInspectRepo;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.utility.DateUtility;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.utility.StringUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static it.finanze.sanita.fse2.ms.gtwstatuscheckms.enums.EventTypeEnum.PUBLICATION;
import static it.finanze.sanita.fse2.ms.gtwstatuscheckms.enums.EventTypeEnum.REPLACE;
import static it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.TransactionEventsETY.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@Repository
public class TransactionInspectRepo implements ITransactionInspectRepo {

	private static final String EVENT_DATE = "eventDate";
	
	private static final String ERROR_MSG = "Error while find events by workflow instance id : ";

	
	@Autowired
	private transient MongoTemplate mongoTemplate;
	
	@Autowired
	private DbPropertyCFG limitConfig;
	
	@Override
	public List<TransactionEventsETY> findEventsByWorkflowInstanceId(final String workflowInstanceId) {
		List<TransactionEventsETY> out = null;
		try {
			Query query = new Query();
			query.addCriteria(where("workflow_instance_id").is(workflowInstanceId));
			query.with(Sort.by(Sort.Direction.ASC, EVENT_DATE));
			
			if(limitConfig.getLimitConfig()==null || limitConfig.getLimitConfig().equals(0)) {
				query.limit(100);
			} else {
				query.limit(limitConfig.getLimitConfig());
			}
			out = mongoTemplate.find(query, TransactionEventsETY.class);
		} catch(Exception ex) {
			log.error(ERROR_MSG , ex);
			throw new BusinessException(ex);
		}
		return out;
		
	}

	@Override
	public List<TransactionEventsETY> searchGenericEvents(TransactionSearchDTO searchParametersDTO) {
		List<TransactionEventsETY> out = null;
		try {
			Query query = new Query();
			
			Date dataDa = DateUtility.setDateTo0000(java.sql.Date.valueOf(searchParametersDTO.getDataDa()));
			Date dataA = DateUtility.setDateTo2359(java.sql.Date.valueOf(searchParametersDTO.getDataA()));
			
			Criteria criteria = new Criteria();
			criteria.andOperator(where(EVENT_DATE).gte(dataDa).lte(dataA));
			
			if(!StringUtility.isNullOrEmpty(searchParametersDTO.getStatus())) {
				criteria.and("eventStatus").is(searchParametersDTO.getStatus());	
			}
			
			if(!StringUtility.isNullOrEmpty(searchParametersDTO.getSubject())) {
				criteria.and("subject").is(searchParametersDTO.getSubject());
			}
			
			if(!StringUtility.isNullOrEmpty(searchParametersDTO.getOrganization())) {
				criteria.and("organizzazione").is(searchParametersDTO.getOrganization());
			}
			
			if(!StringUtility.isNullOrEmpty(searchParametersDTO.getTipoAttivita())) {
				criteria.and("tipoAttivita").is(searchParametersDTO.getTipoAttivita());
			}
			
			query.addCriteria(criteria);
			query.with(Sort.by(Sort.Direction.ASC, EVENT_DATE));
			out = mongoTemplate.find(query, TransactionEventsETY.class);
		} catch(Exception ex) {
			log.error("Error while search generic events : " , ex);
			throw new BusinessException(ex);
		}
		return out;
	}
	
	@Override
	public List<TransactionEventsETY> findEventsByTraceId(final String traceId) {
		List<TransactionEventsETY> out = null;
		try {
			Query query = new Query();
			query.addCriteria(where("traceId").is(traceId));
			query.with(Sort.by(Sort.Direction.ASC, EVENT_DATE));
			out = mongoTemplate.find(query, TransactionEventsETY.class);
		} catch(Exception ex) {
			log.error(ERROR_MSG , ex);
			throw new BusinessException(ex);
		}
		return out;
		
	}

	@Override
	public TransactionEventsETY findLastEventByWorkflowInstanceId(String workflowInstanceId) {
		TransactionEventsETY out = null;
		try {
			Query query = new Query();
			query.addCriteria(where("workflow_instance_id").is(workflowInstanceId));
			query.with(Sort.by(Sort.Direction.DESC, EVENT_DATE));
			out = mongoTemplate.findOne(query, TransactionEventsETY.class);
		} catch(Exception ex) {
			log.error(ERROR_MSG , ex);
			throw new BusinessException(ex);
		}
		return out;
	}
	
	@Override
	public List<TransactionEventsETY> findByIdDocumento(final String idDocumento) {
		List<TransactionEventsETY> out;
		Query query = new Query(
			where(FIELD_ID_DOC).is(idDocumento).
			and(FIELD_EVENT_TYPE).in(PUBLICATION, REPLACE).
			and(FIELD_EVENT_STATUS).is(EVENT_STATUS_SUCCESS)
		);
		try {
			out = mongoTemplate.find(query, TransactionEventsETY.class);
		} catch(Exception ex) {
			log.error("Error while search generic events : " , ex);
			throw new BusinessException(ex);
		}
		return out;
	}
	 
}
