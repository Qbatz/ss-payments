package com.qbatz.payment;

import com.qbatz.payment.dao.Credentials;
import com.qbatz.payment.repositories.CredentialsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(QbatzPaymentApplication.class);
	}

}
