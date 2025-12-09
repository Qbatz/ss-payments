package com.qbatz.payment.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {
    @Id
    String service;
    String clientId;
    String authToken;
    String secretValue;
    String refreshToken;
    String otherSecrets;
}
