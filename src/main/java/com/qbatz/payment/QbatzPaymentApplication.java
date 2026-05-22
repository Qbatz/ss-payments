package com.qbatz.payment;

import com.qbatz.payment.dao.Credentials;
import com.qbatz.payment.repositories.CredentialsRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default")})
public class QbatzPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(QbatzPaymentApplication.class, args);
	}

//	@Bean
//	CommandLineRunner addCredentials(CredentialsRepository credentialRepository) {
//		return args -> {
//			Credentials credentials = new Credentials();
//			credentials.setAuthToken("1005.8a54670206ae52f86c3a70af213657a1.df99b2ffa182aceeb0e2ed4c4c0b91c9");
//			credentials.setService("zoho");
//			credentials.setSecretValue("e2e997f13e582a1a1ab210d5cc85d208a68e1165f1");
//			credentials.setClientId("1005.1LJXPZR2TPI4I24NBVW1HVY6QYEV3B");
//			credentials.setRefreshToken("1005.7178154c7ef49430f3148ae84d6da8fb.c9388be06a4120fdadee516e93e01d0e");
//			credentialRepository.save(credentials);
//		};
//	}
}
