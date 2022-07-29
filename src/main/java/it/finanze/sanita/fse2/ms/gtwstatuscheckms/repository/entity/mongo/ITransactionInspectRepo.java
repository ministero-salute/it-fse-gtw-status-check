package it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.mongo;

import java.io.Serializable;
import java.util.List;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.TransactionSearchDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.TransactionEventsETY;

public interface ITransactionInspectRepo extends Serializable {

	List<TransactionEventsETY> findEventsByWorkflowInstanceId(String workflowInstanceId);

	List<TransactionEventsETY> searchGenericEvents(TransactionSearchDTO searchParametersDTO);
	
	List<TransactionEventsETY> findEventsByTraceId(String traceId);

	TransactionEventsETY findLastEventByWorkflowInstanceId(String workflowInstanceId);
}
