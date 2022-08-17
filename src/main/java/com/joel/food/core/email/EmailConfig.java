package com.joel.food.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.joel.food.domain.service.EnvioEmailService;
import com.joel.food.infrastructure.service.email.FakeEnvioEmailService;
import com.joel.food.infrastructure.service.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties emailProperties;

	@Bean
	public EnvioEmailService envioEmailService() {
		switch (emailProperties.getImpl()) {
			case FAKE:
				return new FakeEnvioEmailService();
			case SMTP:
				return new SmtpEnvioEmailService();
			default:
				return null;
		}
	}

}