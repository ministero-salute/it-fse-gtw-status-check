package it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model to save validation events.
 */
@Document(collection = "transaction_data")
@Data
@NoArgsConstructor
public class TransactionEventsETY {

    @Id
	private String id;
	
	@Field(name = "eventType")
	private String eventType;

	@Field(name = "eventDate")
	private Date eventDate;  
	
	@Field(name = "eventStatus")
	private String eventStatus;
	
	@Field(name = "message")
	private String message;

	@Field(name = "identificativoDocumento")
	private String identificativoDocumento;

	@Field(name = "subject")
	private String subject;

	@Field(name = "subjectRole")
	private String subjectRole;
	
	@Field(name = "tipoAttivita")
	private String tipoAttivita;
	
	@Field(name = "organizzazione")
	private String organizzazione;
	
	@Field(name = "workflowInstanceId")
	private String workflowInstanceId;
	
	@Field(name = "traceId")
	private String traceId;
	
	@Field(name = "issuer")
	private String issuer;
	
	
    
}
