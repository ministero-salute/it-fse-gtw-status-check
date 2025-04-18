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
package it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.error;

import org.springframework.http.HttpStatus;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.LogTraceInfoDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.error.ErrorInstance.Resource;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.error.ErrorInstance.Server;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.error.ErrorInstance.Validation;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.error.base.ErrorResponseDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions.NoRecordFoundException;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.exceptions.ValidationException;

public final class ErrorBuilderDTO {
    /**
     * Private constructor to disallow to access from other classes
     */
    private ErrorBuilderDTO() {}

    public static ErrorResponseDTO createConstraintError(LogTraceInfoDTO trace, ValidationException ex) {
        return new ErrorResponseDTO(
            trace,
            ErrorType.VALIDATION.getType(),
            ErrorType.VALIDATION.getTitle(),
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            ErrorType.VALIDATION.toInstance(Validation.CONSTRAINT_FIELD, ex.getField())
        );
    }

    public static ErrorResponseDTO createGenericError(LogTraceInfoDTO trace, Exception ex) {
        return new ErrorResponseDTO(
            trace,
            ErrorType.SERVER.getType(),
            ErrorType.SERVER.getTitle(),
            ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ErrorType.SERVER.toInstance(Server.INTERNAL)
        );
    }

    public static ErrorResponseDTO createNoRecordFoundError(LogTraceInfoDTO trace, NoRecordFoundException ex) {
        return new ErrorResponseDTO(
            trace,
            ErrorType.RESOURCE.getType(),
            ErrorType.RESOURCE.getTitle(),
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            ErrorType.RESOURCE.toInstance(Resource.NOT_FOUND)
        );
    }
}
