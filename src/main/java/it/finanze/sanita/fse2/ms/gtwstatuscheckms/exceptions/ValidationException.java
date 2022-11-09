/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions;

import lombok.Getter;

/**
 * 
 * Generic validation exception.
 */
@Getter
public class ValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4420700371354323215L;

	private final String field;

	/**
	 * Message constructor.
	 *
	 * @param msg	Message to be shown.
	 */
	public ValidationException(final String msg, final String field) {
		super(msg);
		this.field = field;
	}
	
}
