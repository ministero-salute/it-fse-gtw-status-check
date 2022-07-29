package it.finanze.sanita.fse2.ms.gtwstatuscheckms.utility;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProfileUtility {
    @Autowired
    private Environment environment;

    public boolean isTestProfile() {
        if (environment != null && environment.getActiveProfiles().length > 0) {
            return environment.getActiveProfiles()[0].toLowerCase().contains(Constants.Profile.TEST);
        }
        return false;
    }
}
