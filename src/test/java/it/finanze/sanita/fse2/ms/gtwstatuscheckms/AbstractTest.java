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

import java.net.URI;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.LastTransactionResponseDTO;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.TransactionInspectResDTO;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository.entity.TransactionEventsETY;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    @Autowired
    RestTemplate restTemplate;

    /**
     * Init collection
     * @param transactionEvents
     */
    protected void initCollection(List<TransactionEventsETY> transactionEvents) {
        mongoTemplate.insertAll(transactionEvents);
    }

    /**
     * Drop collection on each test
     */
    protected void cleanupCollection() {
        mongoTemplate.dropCollection(TransactionEventsETY.class);
    }

    /**
     * Get transaction events generic method
     * @param workflowInstanceId id of the transaction
     * @return entity containing all the events found
     */
    ResponseEntity<TransactionInspectResDTO> getTransactionEvents(String workflowInstanceId) {
        String url = "http://localhost:" +
                webServerAppCtxt.getWebServer().getPort() +
                webServerAppCtxt.getServletContext().getContextPath() +
                "/v1/" +
                workflowInstanceId;
        return restTemplate.getForEntity(url, TransactionInspectResDTO.class);
    }

    /**
     * Search generic events passing query parameters
     * @param organization organization name
     * @param activityType type of the activity
     * @param documentType type of the document
     * @return entity containing all the events found
     */
    ResponseEntity<TransactionInspectResDTO> searchGenericTransactionEvents(
            LocalDate dateFrom,
            LocalDate dateTo,
            String organization,
            String activityType,
            String documentType,
            String status,
            String subject) {
        String url = "http://localhost:" +
                webServerAppCtxt.getWebServer().getPort() +
                webServerAppCtxt.getServletContext().getContextPath() +
                "/v1/search-events";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        putNonNullIntoMap(queryParams,"organization", organization);
        putNonNullIntoMap(queryParams,"tipoDocumento", documentType);
        putNonNullIntoMap(queryParams,"tipoAttivita", activityType);
        putNonNullIntoMap(queryParams,"status", status);
        putNonNullIntoMap(queryParams,"subject", subject);
        putNonNullIntoMap(queryParams,"dataA", String.valueOf(dateTo));
        putNonNullIntoMap(queryParams,"dataDa", String.valueOf(dateFrom));

        URI uri = UriComponentsBuilder.fromUriString(url)
                .buildAndExpand()
                .toUri();

        uri = UriComponentsBuilder
                .fromUri(uri)
                .queryParams(queryParams)
                .build()
                .toUri();

        return restTemplate.getForEntity(uri, TransactionInspectResDTO.class);
    }

    /**
     * Safe add values into query params map
     * @param map
     * @param key
     * @param value
     */
    private void putNonNullIntoMap(MultiValueMap<String, String> map, String key, String value) {
        if (value != null) {
            map.add(key, value);
        }
    }

    /**
     * Populate list to be saved into collection
     * @return
     */
    public List<TransactionEventsETY> initList() throws ParseException {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        List<TransactionEventsETY> transactionEventsETYList = new ArrayList<>();

        TransactionEventsETY transactionEvent1 = new TransactionEventsETY();
        transactionEvent1.setWorkflowInstanceId(TestConstants.workflowInstanceId);
        transactionEvent1.setOrganizzazione(TestConstants.organization1);
        transactionEvent1.setTipoAttivita(TestConstants.activityType1);
        transactionEvent1.setEventDate(Date.from(LocalDateTime.parse(TestConstants.eventDate1, format).toInstant(ZoneOffset.UTC)));
        transactionEvent1.setSubject(TestConstants.subject1);
        transactionEvent1.setEventStatus(TestConstants.eventStatus1);
        transactionEvent1.setEventType(TestConstants.eventType1);
        transactionEvent1.setIdentificativoDocumento(TestConstants.identificativoDocumento1);
        transactionEvent1.setSubject(TestConstants.subject1);
        transactionEvent1.setTraceId(TestConstants.traceIdMock);

        TransactionEventsETY transactionEvent2 = new TransactionEventsETY();
        transactionEvent2.setWorkflowInstanceId(TestConstants.workflowInstanceId2);
        transactionEvent2.setOrganizzazione(TestConstants.organization2);
        transactionEvent2.setTipoAttivita(TestConstants.activityType2);
        transactionEvent2.setEventDate(Date.from(LocalDateTime.parse(TestConstants.eventDate2, format).toInstant(ZoneOffset.UTC)));
        transactionEvent2.setSubject(TestConstants.subject2);
        transactionEvent2.setEventStatus(TestConstants.eventStatus2);
        transactionEvent2.setEventType(TestConstants.eventType2);
        transactionEvent2.setIdentificativoDocumento(TestConstants.identificativoDocumento2);
        transactionEvent2.setSubject(TestConstants.subject2);
        transactionEvent2.setTraceId(TestConstants.traceIdMock);

        TransactionEventsETY transactionEvent3 = new TransactionEventsETY();
        transactionEvent3.setWorkflowInstanceId(TestConstants.workflowInstanceId3);
        transactionEvent3.setEventDate(Date.from(LocalDateTime.parse(TestConstants.eventDate3, format).toInstant(ZoneOffset.UTC)));
        transactionEvent3.setEventStatus(TestConstants.eventStatus3);
        transactionEvent3.setEventType(TestConstants.eventType3);
        transactionEvent3.setTraceId(TestConstants.traceIdMock);

        TransactionEventsETY transactionEvent4 = new TransactionEventsETY();
        transactionEvent4.setWorkflowInstanceId(TestConstants.workflowInstanceId4);
        transactionEvent4.setEventDate(Date.from(LocalDateTime.parse(TestConstants.eventDate4, format).toInstant(ZoneOffset.UTC)));
        transactionEvent4.setEventStatus(TestConstants.eventStatus4);
        transactionEvent4.setEventType(TestConstants.eventType4);
        transactionEvent4.setTraceId(TestConstants.traceIdMock);

        transactionEventsETYList.add(transactionEvent1);
        transactionEventsETYList.add(transactionEvent2);
        transactionEventsETYList.add(transactionEvent3);
        transactionEventsETYList.add(transactionEvent4);
        return transactionEventsETYList;
    }

    /**
     * Get transaction events by trace id
     * @param traceId id of the transaction
     * @return entity containing all the events found
     */
    ResponseEntity<TransactionInspectResDTO> getTransactionEventsByTraceId(String traceId) {
        String url = "http://localhost:" +
                webServerAppCtxt.getWebServer().getPort() +
                webServerAppCtxt.getServletContext().getContextPath() +
                "/v1/search/" +
                traceId;
        return restTemplate.getForEntity(url, TransactionInspectResDTO.class);
    }

    /**
     * Get transaction events by workflow instance id
     * @param workflowInstanceId of the transaction
     * @return entity containing all the events found
     */
    ResponseEntity<LastTransactionResponseDTO> getLastTransactionEventByWorkflowInstanceId(String workflowInstanceId) {
        String url = "http://localhost:" +
                webServerAppCtxt.getWebServer().getPort() +
                webServerAppCtxt.getServletContext().getContextPath() +
                "/v1/last-state-trasaction/" +
                workflowInstanceId;
        return restTemplate.getForEntity(url, LastTransactionResponseDTO.class);
    }
    
    TransactionEventsETY buildTransactionEventsETY(int i) {
    	
        return TransactionEventsETY.builder()
                .eventDate(new Date())
                .eventStatus(TestConstants.eventStatus1)
                .eventType(TestConstants.eventType1)
                .id(null)
                .identificativoDocumento(TestConstants.identificativoDocumento1)
                .issuer("issuer")
                .message("msg")
                .organizzazione(TestConstants.organization1)
                .subject(TestConstants.subject1)
                .subjectRole(TestConstants.subject1)
                .tipoAttivita(TestConstants.activityType1)
                .traceId(TestConstants.traceIdMock)
                .workflowInstanceId(TestConstants.workflowInstanceId)
                .build();
    }
}
