package it.finanze.sanita.fse2.ms.gtwstatuscheckms.repository;

import it.finanze.sanita.fse2.ms.gtwstatuscheckms.config.Constants;
import it.finanze.sanita.fse2.ms.gtwstatuscheckms.utility.ProfileUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CollectionNaming {

    @Autowired
    private ProfileUtility profileUtility;

    @Bean("transactionDataBean")
    public String getTransactionDataCollection() {
        if (profileUtility.isTestProfile()) {
            return Constants.Profile.TEST_PREFIX + Constants.ComponentScan.Collections.TRANSACTION_DATA;
        }
        return Constants.ComponentScan.Collections.TRANSACTION_DATA;
    }
}
