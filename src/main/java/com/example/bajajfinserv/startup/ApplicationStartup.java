package com.example.bajajfinserv.startup;

import com.example.bajajfinserv.Model.WebhookRequest;
import com.example.bajajfinserv.Model.WebhookResponse;
import com.example.bajajfinserv.service.SqlProblemSolver;
import com.example.bajajfinserv.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup {

    @Autowired
    private WebhookService webhookService;

    @Autowired
    private SqlProblemSolver sqlProblemSolver;

    // Replace these with your actual details
    private static final String NAME = "Garima Kushwah";
    private static final String REG_NO = "1184"; // Use your registration number
    private static final String EMAIL = "garimakushwah1404@gmail.com";

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationStart() {
        try {
            // Step 1: Generate webhook
            WebhookRequest request = new WebhookRequest(NAME, REG_NO, EMAIL);
            WebhookResponse response = webhookService.generateWebhook(request);
            
            if (response != null && response.getWebhook() != null && response.getAccessToken() != null) {
                System.out.println("Successfully generated webhook");
                System.out.println("Webhook URL: " + response.getWebhook());
                
                // Step 2: Solve SQL problem based on registration number
                String sqlSolution = sqlProblemSolver.solveQuery(REG_NO);
                System.out.println("SQL Solution: " + sqlSolution);
                
                // Step 3: Submit solution to webhook
                webhookService.submitSolution(response.getWebhook(), response.getAccessToken(), sqlSolution);
                System.out.println("Solution submitted successfully");
            } else {
                System.out.println("Failed to generate webhook or received null response");
            }
        } catch (Exception e) {
            System.out.println("Error during application startup: " + e.getMessage());
            e.printStackTrace();
        }
    }
}