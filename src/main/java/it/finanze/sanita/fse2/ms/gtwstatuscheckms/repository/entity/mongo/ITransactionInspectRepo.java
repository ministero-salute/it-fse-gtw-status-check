package it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.mongo;

import java.io.Serializable;
import java.util.List;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.TransactionSearchDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.TransactionEventsETY;

public interface ITransactionInspectRepo extends Serializable {

	List<TransactionEventsETY> findEventsByTransactionId(String transactionId);
	
	List<TransactionEventsETY> searchGenericEvents(TransactionSearchDTO searchParametersDTO);
}
