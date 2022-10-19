/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSearchDTO {

	private LocalDate dataDa;
	
	private LocalDate dataA;
	
	@Size(min = 0, max = 100)
	private String status;
	
	@Size(min = 0, max = 100)
	private String subject;
	
	@Size(min = 0, max = 100)
	private String organization;
	
	@Size(min = 0, max = 100)
	private String tipoAttivita;
	
}
