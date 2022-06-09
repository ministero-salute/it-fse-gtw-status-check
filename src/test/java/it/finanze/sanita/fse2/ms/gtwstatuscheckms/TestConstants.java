package it.finanze.sanita.fse2.ms.gtwstatuscheckms;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestConstants {
    public static final String workflowInstanceId = "e80e4862b2c246158523deb1359fead8";
    public static final String organization1 = "Bearer token";
    public static final String documentType1 = "Bearer token";
    public static final String activityType1 = "Bearer token";
    public static final String eventStatus1 = "SUCCESS";
    public static final String eventType1 = "VALIDATION";
    public static final String eventDate1 = "2022-05-24T17:45:46Z";
    public static final String identificativoDocumento1 = "Bearer token";
    public static final String subject1 = "Bearer token";

    public static final String workflowInstanceId2 = "e80e4862b2c246158523deb1359fead8";
    public static final String organization2 = "Bearer token";
    public static final String documentType2 = "Bearer token";
    public static final String activityType2 = "Bearer token";
    public static final String eventStatus2 = "SUCCESS";
    public static final String eventType2 = "PUBLICATION";
    public static final String eventDate2 = "2022-05-24T17:46:30Z";
    public static final String identificativoDocumento2 = "Bearer token";
    public static final String subject2 = "Bearer token";

    public static final String transactionId3 = "e80e4862b2c246158523deb1359fead8";
    public static final String eventStatus3 = "SUCCESS";
    public static final String eventType3 = "SEND_TO_INI";
    public static final String eventDate3 = "2022-05-24T17:46:31Z";

    public static final String transactionId4 = "e80e4862b2c246158523deb1359fead8";
    public static final String eventStatus4 = "SUCCESS";
    public static final String eventType4 = "SEND_TO_EDS";
    public static final String eventDate4 = "2022-05-24T17:46:32Z";

    public static final String transactionIDNoFound = "c";
    public static LocalDate dateFrom = LocalDate.parse("2022-05-24", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    public static LocalDate dateTo = LocalDate.parse("2022-05-24", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
}
