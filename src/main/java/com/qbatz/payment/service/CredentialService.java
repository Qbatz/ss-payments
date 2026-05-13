package com.qbatz.payment.service;

import com.qbatz.payment.dao.Credentials;
import com.qbatz.payment.repositories.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {

    @Autowired
    CredentialsRepository credentialsRepository;

    public Credentials getZohoCredentials() {
        return credentialsRepository.findByService("zoho");
    }

    public void updateCredential(Credentials credentials) {
        credentialsRepository.save(credentials);
    }
}
