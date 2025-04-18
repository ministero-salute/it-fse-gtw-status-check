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
package it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.mongo;

import java.util.List;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.TransactionSearchDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.TransactionEventsETY;

public interface ITransactionInspectRepo {

	List<TransactionEventsETY> findEventsByWorkflowInstanceId(String workflowInstanceId);

	List<TransactionEventsETY> searchGenericEvents(TransactionSearchDTO searchParametersDTO);
	
	List<TransactionEventsETY> findEventsByTraceId(String traceId);

	TransactionEventsETY findLastEventByWorkflowInstanceId(String workflowInstanceId);
	
	List<TransactionEventsETY> findByIdDocumento(String idDocumento);
}
