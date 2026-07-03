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
//			credentials.setAuthToken("1005.107f721678771a63b93686de94a7f31d.dd8fa744019e2cec11bacc3210b73797");
//			credentials.setService("zoho");
//			credentials.setSecretValue("e2e997f13e582a1a1ab210d5cc85d208a68e1165f1");
//			credentials.setClientId("1005.1LJXPZR2TPI4I24NBVW1HVY6QYEV3B");
//			credentials.setRefreshToken("1005.996757dc7f275d889178f9a5efa4e8ac.8596a2849f2c09bc20662af227d4b660");
//			credentialRepository.save(credentials);
//		};
//	}
}
