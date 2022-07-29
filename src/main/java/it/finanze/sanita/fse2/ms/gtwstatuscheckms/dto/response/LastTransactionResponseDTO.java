package it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.TransactionEventsETY;
import lombok.Data;

@Data
public class LastTransactionResponseDTO extends ResponseDTO {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = -7747265284310662589L;
	private String transactionStatus;

	@Schema(description = "Ultimo evento trovato")
	private transient TransactionEventsETY lastTransactionData;

	public LastTransactionResponseDTO() {
		super();
		lastTransactionData = null;
	}

	public LastTransactionResponseDTO(final LogTraceInfoDTO traceInfo, final String transactionStatus, final TransactionEventsETY lastTransactionData) {
		super(traceInfo);
		this.transactionStatus = transactionStatus;
		this.lastTransactionData = lastTransactionData;
	}
}
