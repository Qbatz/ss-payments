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
//			credentials.setAuthToken("1005.21bbcece6df82ea2ea80330ac3e6f387.5990b1633bbbd772c944c7591ab07b0e");
//			credentials.setService("zoho");
//			credentials.setSecretValue("e2e997f13e582a1a1ab210d5cc85d208a68e1165f1");
//			credentials.setClientId("1005.1LJXPZR2TPI4I24NBVW1HVY6QYEV3B");
//			credentials.setRefreshToken("1005.077ddf1712e28a1036baab7a093dca41.961839ea1e539d9bec2eb3809ca4a0ff");
//			credentialRepository.save(credentials);
//		};
//	}
}
