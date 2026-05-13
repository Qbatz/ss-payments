package com.qbatz.payment.responses.zoho;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("api_domain")
    private String apiDomain;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private int expiresIn;
}
