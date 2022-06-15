package it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.mongo.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.TransactionSearchDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions.BusinessException;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.TransactionEventsETY;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.mongo.ITransactionInspectRepo;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.utility.DateUtility;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.utility.StringUtility;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TransactionInspectRepo implements ITransactionInspectRepo {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 5151052155295400479L;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<TransactionEventsETY> findEventsByTransactionId(final String transactionId) {
		List<TransactionEventsETY> out = null;
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("transactionId").is(transactionId));
			query.with(Sort.by(Sort.Direction.ASC, "eventDate"));
			out = mongoTemplate.find(query, TransactionEventsETY.class);
		} catch(Exception ex) {
			log.error("Error while find events by transaction id : " , ex);
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
			criteria.andOperator(Criteria.where("eventDate").gte(dataDa).lte(dataA));
			
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
			query.with(Sort.by(Sort.Direction.ASC, "eventDate"));
			out = mongoTemplate.find(query, TransactionEventsETY.class);
		} catch(Exception ex) {
			log.error("Error while search generic events : " , ex);
			throw new BusinessException(ex);
		}
		return out;
	}
	
}
