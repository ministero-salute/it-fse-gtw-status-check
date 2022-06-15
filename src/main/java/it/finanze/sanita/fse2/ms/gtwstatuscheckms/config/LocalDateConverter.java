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
				out = null;
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format); 
			out = LocalDate.parse(date, formatter);
		} catch (Exception e) {
			out = null;
		}
		return out;
	}
}