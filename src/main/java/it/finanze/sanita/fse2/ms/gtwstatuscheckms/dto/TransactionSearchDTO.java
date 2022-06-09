package it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto;

import java.time.LocalDate;

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
	
	private String status;
	
	private String subject;
	
	private String organization;
	 
	private String tipoAttivita;
	
}
