/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
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
    @DisplayName("testEventTypeEnums")
    void testEventTypeEnums() {
        String code = EventTypeEnum.VALIDATION.name();
        assertEquals(code, EventTypeEnum.VALIDATION.name());
    }
 
}
