package com.qbatz.payment;

import com.qbatz.payment.dao.Credentials;
import com.qbatz.payment.repositories.CredentialsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QbatzPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(QbatzPaymentApplication.class, args);
	}

	@Bean
	CommandLineRunner addCredentials(CredentialsRepository credentialRepository) {
		return args -> {
//			Credentials credentials = new Credentials();
//			credentials.setAuthToken("1000.2306430c5b48cdb3dbc725a6038274cb.c60048d2830de00ef224e7d3a3e5e674");
//			credentials.setService("zoho");
//			credentials.setSecretValue("db20f122ac9e4c7e0d2f872a534ea6ce5f7b9f89f1");
//			credentials.setClientId("1000.VQXZ7ZTIN3P4H0BRD2EM1HY2GABFVC");
//			credentials.setRefreshToken("1000.20e3755f4209fc16b228915193d3c887.5b2530c277a68728f5156a53731cc701");
//			credentialRepository.save(credentials);
		};
	}
}
