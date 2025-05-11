package com.example.bajajfinserv.service;

import com.example.bajajfinserv.Model.SolutionRequest;
import com.example.bajajfinserv.Model.WebhookRequest;
import com.example.bajajfinserv.Model.WebhookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class WebhookService {

    private static final String GENERATE_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

    @Autowired
    private RestTemplate restTemplate;

    public WebhookResponse generateWebhook(WebhookRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);
        
        return restTemplate.postForObject(GENERATE_WEBHOOK_URL, entity, WebhookResponse.class);
    }

    public void submitSolution(String webhookUrl, String accessToken, String finalQuery) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);
        
        SolutionRequest solutionRequest = new SolutionRequest(finalQuery);
        HttpEntity<SolutionRequest> entity = new HttpEntity<>(solutionRequest, headers);
        
        restTemplate.postForObject(webhookUrl, entity, String.class);
    }
}