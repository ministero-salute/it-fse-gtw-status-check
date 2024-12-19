
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
package it.finanze.sanita.fse2.ms.gtwstatuscheckms.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions.BusinessException;

/**
 * Converter local date.
 */
@Component
@ConfigurationPropertiesBinding
public class LocalDateConverter implements Converter<String, LocalDate> {

	/**
     * Metodo di conversione.
     */
	@Override
	public LocalDate convert(final String date) {
		LocalDate out = handleFormat(date, "yyyy-MM-dd");
		if (out == null) {
			out = handleFormat(date, "yyyy/MM/dd");
		}
		if (out == null) {
			throw new BusinessException("Inserire la data nel formato ");
		}
		return out;
	}

	/**
     * Metodo di conversione gestione formato.
     */
	private LocalDate handleFormat(final String date, final String format) {
		LocalDate out = null;
		try {
			if (date == null) {
				return out;
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format); 
			out = LocalDate.parse(date, formatter);
		} catch (Exception e) {
			out = null;
		}
		return out;
	}
}