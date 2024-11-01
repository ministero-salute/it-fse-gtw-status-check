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
package it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions;

/**
 * 
 *
 *	Eccezione di record not found.
 */
public class NoRecordFoundException extends RuntimeException {

	/**
	 * Seriale.
	 */
	private static final long serialVersionUID = 5632725723070077498L;

	/**
	 * Costruttore.
	 * 
	 * @param msg	messaggio
	 */
	public NoRecordFoundException(final String msg) {
		super(msg);
	}
	
	/**
	 * Costruttore.
	 * 
	 * @param msg	messaggio
	 * @param e		eccezione
	 */
	public NoRecordFoundException(final String msg, final Exception e) {
		super(msg, e);
	}
	
	/**
	 * Costruttore.
	 * 
	 * @param e	eccezione.
	 */
	public NoRecordFoundException(final Exception e) {
		super(e);
	}
	
}
