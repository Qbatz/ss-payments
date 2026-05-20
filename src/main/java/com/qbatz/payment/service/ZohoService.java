package com.qbatz.payment.service;

import com.qbatz.payment.config.RestTemplateLoggingInterceptor;
import com.qbatz.payment.dao.Credentials;
import com.qbatz.payment.payloads.Payments.GeneratePayments;
import com.qbatz.payment.responses.payments.PaymentLinks;
import com.qbatz.payment.dto.PaymentSessions;
import com.qbatz.payment.responses.zoho.AuthTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.JsonNode;

import com.qbatz.payment.enumm.ActivitySource;
import com.qbatz.payment.enumm.ActivitySourceType;
import com.qbatz.payment.dao.Users;
import com.qbatz.payment.repositories.UserRepository;
import com.qbatz.payment.config.Authentication;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZohoService {

    private final RestTemplate restTemplate;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private OrderHistoryService orderHistoryService;

    @Autowired
    private PaymentSessionService paymentSessionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private Authentication authentication;

    public ZohoService() {
        RestTemplate template = new RestTemplate(
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        template.setInterceptors(Collections.singletonList(new RestTemplateLoggingInterceptor()));
        this.restTemplate = template;
    }


    public PaymentLinks generatePaymentLink(String hostelId, GeneratePayments generatePayments, int count) {
        Credentials credentials = credentialService.getZohoCredentials();
        try {

            String accountId = "60035196766";
            String url = "https://payments.zoho.in/api/v1/paymentlinks";

            MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
            formParams.add("account_id", accountId);

            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                    .queryParam("account_id", accountId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Zoho-oauthtoken "+ credentials.getAuthToken());

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("amount", generatePayments.amount());
            requestBody.put("currency", "INR");
            requestBody.put("description", "Plan Extension");

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, JsonNode.class);
            if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
                JsonNode paymentLinksNode = responseEntity.getBody().get("payment_links");
                PaymentLinks details = new PaymentLinks(
                        paymentLinksNode.get("url").asString(),
                        paymentLinksNode.get("payment_link_id").asString());

                orderHistoryService.createOrder(
                        hostelId,
                        details,
                        generatePayments.amount(),
                        generatePayments.planCode(),
                        generatePayments.discountAmount(),
                        generatePayments.planPrice(),
                        generatePayments.createdBy());

                com.qbatz.payment.dao.PaymentSessions paymentSessions = paymentSessionService.addPaymentSession(
                        details.paymentLinkId(),
                        generatePayments.amount(),
                        hostelId,
                        generatePayments.discountAmount(),
                        generatePayments.planPrice(),
                        generatePayments.planCode());

                String userId = authentication.getName() != null ? authentication.getName() : generatePayments.createdBy();
                Users users = userRepository.findUserByUserId(userId);

                usersService.addUserLog(
                        hostelId,
                        paymentSessions.getPaymentSessionId(),
                        ActivitySource.PAYMENTS,
                        ActivitySourceType.CREATE_SESSION,
                        users);

                return details;
            }
        }
        catch (HttpClientErrorException.Unauthorized ex) {
            count = count + 1;
            if (count < 3) {
                Credentials credentials1 = refreshAuthToken(credentials);
                return generatePaymentLink(hostelId, generatePayments, count);
            }

        }

        return null;

    }

//    public String generate() {
//        Credentials credentials = credentialService.getZohoCredentials();
//
//        String url = "https://accounts.zoho.in/oauth/v2/token";
//        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
//        formParams.add("client_id", credentials.getClientId());
//        formParams.add("client_secret", credentials.getSecretValue());
//        formParams.add("refresh_token", credentials.getRefreshToken());
//        formParams.add("redirect_uri", "https://smartstay.qbatz.com/");
//        formParams.add("grant_type", "authorization_code");
//    }



    public Credentials refreshAuthToken(Credentials credentials) {
        String url = "https://accounts.zoho.in/oauth/v2/token";

        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add("client_id", credentials.getClientId());
        formParams.add("client_secret", credentials.getSecretValue());
        formParams.add("refresh_token", credentials.getRefreshToken());
        formParams.add("redirect_uri", "https://smartstay.qbatz.com/");
        formParams.add("grant_type", "refresh_token");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formParams, headers);

        try {
            ResponseEntity<AuthTokenResponse> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, AuthTokenResponse.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                credentials.setAuthToken(response.getBody().getAccessToken());

                credentialService.updateCredential(credentials);
                return credentials;
            }
            return null;
        }
        catch (HttpClientErrorException.BadRequest badRequest) {
            throw new RuntimeException("Something went wrong");
        }
    }

    public com.qbatz.payment.responses.payments.PaymentSessions generatePaymentSessions(String hostelId, GeneratePayments generatePayments, int count) {
        Credentials credentials = credentialService.getZohoCredentials();
        try {
            String accountId = "60035196766";
            String url = "https://payments.zoho.in/api/v1/paymentsessions";

            MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
            formParams.add("account_id", accountId);

            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                    .queryParam("account_id", accountId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Zoho-oauthtoken "+ credentials.getAuthToken());

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("amount", generatePayments.amount());
            requestBody.put("currency", "INR");

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<PaymentSessions> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, PaymentSessions.class);
            System.out.println(responseEntity.getStatusCode());
            if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
                if (responseEntity.getBody() != null) {
                    PaymentSessions.PaymentSession sessions = responseEntity.getBody().getPaymentSession();
                    if (sessions != null) {
                        return new com.qbatz.payment.responses.payments.PaymentSessions(sessions.getPaymentsSessionId(), sessions.getAmount());
                    }
                }
                return null;

            }
            return null;
        }
        catch (HttpClientErrorException.Unauthorized ex) {
            count = count + 1;
            if (count < 3) {
                Credentials credentials1 = refreshAuthToken(credentials);
                return generatePaymentSessions(hostelId, generatePayments, count);
            }

            return null;

        }


    }

    public ResponseEntity<?> getPaymentStatusFromZoho(String paymentId, int count) {
        Credentials credentials = credentialService.getZohoCredentials();

        try {
            String url = "https://payments.zoho.in/api/v1/payments/" + paymentId;
            String accountId = "60035196766";

            MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
            formParams.add("account_id", accountId);

            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                    .queryParam("account_id", accountId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Zoho-oauthtoken "+ credentials.getAuthToken());

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);

            System.out.println(responseEntity.getStatusCode());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
        catch (HttpClientErrorException.Unauthorized ex) {
            count = count + 1;
            if (count < 3) {
                Credentials credentials1 = refreshAuthToken(credentials);
                return getPaymentStatusFromZoho(paymentId, count);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (HttpClientErrorException.BadRequest badRequest) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
