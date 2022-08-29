package it.finanze.sanita.fse2.ms.gtwstatuscheckms.utility;

import java.time.LocalDate;
import java.time.Period;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions.ValidationException;

import static it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.error.ErrorInstance.*;

/**
 * 
 * @author vincenzoingenito
 *
 *  Validazione.
 */
public final class ValidationUtility {

	/**
	 * Costruttore.
	 */
	private ValidationUtility() {
		//Questo metodo è lasciato intenzionalmente vuoto.
	}

	/**
	 * Metodo che valida un range di date.
	 * 
	 */
	public static void checkRange(final LocalDate dataDa,final LocalDate dataA) {

		if(dataDa.isAfter(dataA)) {
			throw new ValidationException("Valorizzare correttamente l'ordine delle date.", Fields.DATA_DA);
		}

		if(dataDa.isAfter(LocalDate.now()) || dataA.isAfter(LocalDate.now())) {
			throw new ValidationException("Le date non possono essere maggiori rispetto alla data odierna.", Fields.DATA_PERIOD);
		}

		Period period = Period.between(dataDa, dataA);
		if(period.getMonths()>6) {
			throw new ValidationException("L'intervallo di ricerca può essere al massimo di 6 mesi.", Fields.DATA_PERIOD);
		}
	}
 

}