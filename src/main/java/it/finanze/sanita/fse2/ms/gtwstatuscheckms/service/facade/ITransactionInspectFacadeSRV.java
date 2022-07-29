package it.finanze.sanita.fse2.ms.gtwstatuscheckms.service.facade;

import java.io.Serializable;
import java.util.List;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.LastTransactionEventDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.TransactionSearchDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.TransactionEventsETY;

public interface ITransactionInspectFacadeSRV extends Serializable {

	List<TransactionEventsETY> findEventsByWorkflowInstanceId(String workflowInstanceId);

	List<TransactionEventsETY> searchGenericEvents(TransactionSearchDTO searchParametersDTO);
	
	List<TransactionEventsETY> findEventsByTraceId(String traceId);

	LastTransactionEventDTO searchLastEventByWorkflowInstanceId(String workflowInstanceId);
}
