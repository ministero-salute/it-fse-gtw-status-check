package it.finanze.sanita.fse2.ms.gtwstatuscheckms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.TransactionSearchDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions.BusinessException;
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
	public List<TransactionEventsETY> findEventsByTransactionId(final String transactionId) {
		try {
			return transactionInspectRepo.findEventsByTransactionId(transactionId);
		} catch(Exception ex) {
			log.error("Error while find events by transaction id : " , ex);
			throw new BusinessException(ex);
		}
		
	}

	@Override
	public List<TransactionEventsETY> searchGenericEvents(final TransactionSearchDTO searchParametersDTO) {
		try {
			return transactionInspectRepo.searchGenericEvents(searchParametersDTO);
		} catch(Exception ex) {
			log.error("Error while search generic events : " , ex);
			throw new BusinessException(ex);
		}
	}

	@Override
	public List<TransactionEventsETY> findEventsByTraceId(final String traceId) {
		try {
			return transactionInspectRepo.findEventsByTraceId(traceId);
		} catch(Exception ex) {
			log.error("Error while find events by transaction id : " , ex);
			throw new BusinessException(ex);
		}
		
	}

}
