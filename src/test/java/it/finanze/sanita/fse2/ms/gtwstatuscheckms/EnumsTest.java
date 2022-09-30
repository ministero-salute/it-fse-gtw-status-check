package it.finanze.sanita.fse2.ms.gtwstatuscheckms;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.config.Constants;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.enums.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = {Constants.ComponentScan.BASE})
@ActiveProfiles(Constants.Profile.TEST)
class EnumsTest {

    @Test
    @DisplayName("actionResEnum")
    void actionResEnum() {
        String code = ActionRes.OK.name();
        assertEquals(code, ActionRes.OK.name());
    }

    @Test
    @DisplayName("testCurrentApplicationLogEnums")
    void testCurrentApplicationLogEnums() {
        String code = "gtw-dispatcher";
        String description = "Gateway FSE - Dispatcher";
        assertEquals(code, CurrentApplicationLogEnum.DISPATCHER.getCode());
        assertEquals(description, CurrentApplicationLogEnum.DISPATCHER.getDescription());
    }

    @Test
    @DisplayName("testErrorLogEnums")
    void testErrorLogEnums() {
        String code = "KO-EDS";
        assertEquals(code, ErrorLogEnum.KO_EDS.getCode());
    }

    @Test
    @DisplayName("testEventTypeEnums")
    void testEventTypeEnums() {
        String code = EventTypeEnum.VALIDATION.name();
        assertEquals(code, EventTypeEnum.VALIDATION.name());
    }

    @Test
    @DisplayName("testResultLogEnum")
    void testResultLogEnum() {
        String code = ResultLogEnum.KO.getCode();
        assertEquals(code, ResultLogEnum.KO.getCode());
    }
}
