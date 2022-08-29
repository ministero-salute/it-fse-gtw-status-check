package it.finanze.sanita.fse2.ms.gtwstatuscheckms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import lombok.Getter;

@Configuration
@Getter
public class DbPropertyCFG {
	
	@Value("${status-check.db-limit-property}")
	private Integer limitConfig;
}