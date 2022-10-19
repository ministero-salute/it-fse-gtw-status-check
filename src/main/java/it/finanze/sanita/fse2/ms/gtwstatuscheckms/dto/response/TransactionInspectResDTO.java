/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response;

import java.util.List;

import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.TransactionEventsETY;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author vincenzoingenito
 *
 *	DTO used to return inspect result.
 */
@Getter
@Setter
public class TransactionInspectResDTO extends ResponseDTO {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = -1550024371939901939L;
	
	@Schema(description = "Eventi trovati")
	@Size(min = 0, max = 1000)
	private List<TransactionEventsETY> transactionData;
	
	public TransactionInspectResDTO() {
		super();
		transactionData = null;
	}

	public TransactionInspectResDTO(final LogTraceInfoDTO traceInfo, final List<TransactionEventsETY> inTransactionData) {
		super(traceInfo);
		transactionData = inTransactionData;
	}
	
}
