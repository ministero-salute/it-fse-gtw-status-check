/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.TransactionEventsETY;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LastTransactionResponseDTO extends ResponseDTO {

	private String transactionStatus;

	@Schema(description = "Ultimo evento trovato")
	private TransactionEventsETY lastTransactionData;

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
