/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 * 
 * Copyright (C) 2023 Ministero della Salute
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package it.finanze.sanita.fse2.ms.gtwstatuscheckms.utility;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.error.ErrorInstance.Fields;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions.ValidationException;

public final class DateUtility {

	public static Date setDateTo2359(final Date date) {
		if (date == null) {
			return null;
		}
		final Date result = (Date) date.clone();
		final Calendar cal = Calendar.getInstance();
		cal.setTime(result);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		result.setTime(cal.getTime().getTime());

		return result;
	}

	/**
	 * Setta il time della data di riferimento alle 00:00:00:000.
	 * 
	 * @param date
	 * @return
	 */
	public static Date setDateTo0000(final Date date) {
		if (date == null) {
			return null;
		}
		final Date result = (Date) date.clone();
		final Calendar cal = Calendar.getInstance();
		cal.setTime(result);
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.MILLISECOND, 000);
		result.setTime(cal.getTime().getTime());

		return result;
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
