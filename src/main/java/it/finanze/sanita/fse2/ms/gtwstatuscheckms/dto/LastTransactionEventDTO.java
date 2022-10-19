/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.TransactionEventsETY;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LastTransactionEventDTO implements Serializable {
    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = -7747265284310662589L;

    private String transactionStatus;

    @Schema(description = "Ultimo evento trovato")
    private transient TransactionEventsETY lastTransactionData;
}
