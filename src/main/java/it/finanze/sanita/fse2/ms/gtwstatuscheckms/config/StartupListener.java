package it.finanze.sanita.fse2.ms.gtwstatuscheckms.config;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class StartupListener {

	public boolean getLimitStatus(@Nullable Integer limitConfig) {
		boolean out = true;
		
		if(limitConfig == null || limitConfig == 0) {
			out = false;
		}
		else {
			out = true;
		}
		return out;
	}
}
