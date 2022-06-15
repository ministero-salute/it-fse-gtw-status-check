package it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response;

import java.util.List;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.TransactionEventsETY;
import lombok.Getter;
import lombok.Setter;


/**
 * 
 * @author vincenzoingenito
 *
 *	DTO used to return inspect search result.
 */
@Getter
@Setter
public class TransactionSearchInspectDTO extends ResponseDTO {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = -1550024371939901939L;
	
	private List<TransactionEventsETY> transactionEventETY;
	
	public TransactionSearchInspectDTO() {
		super();
		transactionEventETY = null;
	}

	public TransactionSearchInspectDTO(final LogTraceInfoDTO traceInfo, final List<TransactionEventsETY> inTransactionEventETY) {
		super(traceInfo);
		transactionEventETY = inTransactionEventETY;
	}
	
}
