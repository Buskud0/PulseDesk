package com.pulsedesk.comment_processor.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class AiService {

    @Value("${huggingface.api.url}")
    private String apiUrl;

    @Value("${huggingface.api.key}")
    private String apiKey;

    @Value("${huggingface.api.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    public String analyzeComment(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", model);

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "user", "content",
                "Is the following text a customer support issue?  (yes/no) " +
                "Is the priority (low/medium/high)? " +
                "is it a (bug/feature/billing/account/other) issue? " +
                "Also write a short summary of the issue. " +
                "Answer strictly in this format: 'isIssue;priority;issueType;short summary', Text: " + text));
        body.put("messages", messages);
        body.put("max_tokens", 30);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            String aiText = (String) message.get("content");

            System.out.println("AI ATSAKYMAS: " + aiText);
            return aiText;
        } catch (Exception e) {
            System.err.println("AI KLAIDA: " + e.getMessage());
            return "ERROR";
        }
    }
}