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
//			credentials.setAuthToken("1005.5dbe3e9d0f12409f4bda84df39747be4.1dec3a05915c01a62a7240cbb3b8cfda");
//			credentials.setService("zoho");
//			credentials.setSecretValue("e2e997f13e582a1a1ab210d5cc85d208a68e1165f1");
//			credentials.setClientId("1005.1LJXPZR2TPI4I24NBVW1HVY6QYEV3B");
//			credentials.setRefreshToken("1005.fe878a46224ba7d0fc00c657fa450030.654455ed0134d5a5467bbf23ace08b80");
//			credentialRepository.save(credentials);
//		};
//	}
}
