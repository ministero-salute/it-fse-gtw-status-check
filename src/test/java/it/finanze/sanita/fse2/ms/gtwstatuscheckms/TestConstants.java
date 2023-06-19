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
package it.finanze.sanita.fse2.ms.gtwstatuscheckms;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestConstants {
    public static final String workflowInstanceId = "2.16.840.1.113883.2.9.2.120.4.4.97bb3fc5bee3032679f4f07419e04af6375baafa17024527a98ede920c6812ed.e09243811d^^^^urn:ihe:iti:xdw:2013:workflowInstanceId";
    public static final String organization1 = "Bearer token";
    public static final String documentType1 = "Bearer token";
    public static final String activityType1 = "Bearer token";
    public static final String eventStatus1 = "SUCCESS";
    public static final String eventType1 = "VALIDATION";
    public static final String eventDate1 = "2022-05-24T17:45:46Z";
    public static final String identificativoDocumento1 = "Bearer token";
    public static final String subject1 = "Bearer token";

    public static final String workflowInstanceId2 = "2.16.840.1.113883.2.9.2.120.4.4.97bb3fc5bee3032679f4f07419e04af6375baafa17024527a98ede920c6812ed.e09243811d^^^^urn:ihe:iti:xdw:2013:workflowInstanceId";
    public static final String organization2 = "Bearer token";
    public static final String documentType2 = "Bearer token";
    public static final String activityType2 = "Bearer token";
    public static final String eventStatus2 = "SUCCESS";
    public static final String eventType2 = "PUBLICATION";
    public static final String eventDate2 = "2022-05-24T17:46:30Z";
    public static final String identificativoDocumento2 = "Bearer token";
    public static final String subject2 = "Bearer token";

    public static final String workflowInstanceId3 = "2.16.840.1.113883.2.9.2.120.4.4.97bb3fc5bee3032679f4f07419e04af6375baafa17024527a98ede920c6812ed.e09243811d^^^^urn:ihe:iti:xdw:2013:workflowInstanceId";
    public static final String eventStatus3 = "SUCCESS";
    public static final String eventType3 = "SEND_TO_INI";
    public static final String eventDate3 = "2022-05-24T17:46:31Z";

    public static final String workflowInstanceId4 = "2.16.840.1.113883.2.9.2.120.4.4.97bb3fc5bee3032679f4f07419e04af6375baafa17024527a98ede920c6812ed.e09243811d^^^^urn:ihe:iti:xdw:2013:workflowInstanceId";
    public static final String eventStatus4 = "SUCCESS";
    public static final String eventType4 = "SEND_TO_EDS";
    public static final String eventDate4 = "2022-05-24T17:46:32Z";

    public static final String workflowInstanceIdNoFound = "c";
    public static LocalDate dateFrom = LocalDate.parse("2022-05-24", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    public static LocalDate dateTo = LocalDate.parse("2022-05-24", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    public static String traceIdMock = "traceIdMock1";
}
