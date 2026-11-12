package com.barbershops.barbers.service;

import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WhatsAppService {
    private final AIService aiService;
    private final RestClient restClient = RestClient.create();

    public WhatsAppService(AIService aiService) {
        this.aiService = aiService;
    }

    @Async
    public void messageProcessing(String phone, String text) {
        System.out.println("Calling Gemini...");

        String aiResponse = aiService.chat(phone, text + "\nThe phone number of the client is: " + phone).toString();

        System.out.println("Gemini finished!");
        System.out.println("Response: " + aiResponse);

        sendMessage(phone, aiResponse);
    }

    public void sendMessage(String phone, String message) {
        String url = "https://graph.facebook.com/v26.0/1345830268618105/messages";

        String accessToken = System.getenv("META_ACCESS_TOKEN");

        System.out.println("Token exists: " + (accessToken != null));
        System.out.println("Token length: " + (accessToken == null ? 0 : accessToken.length()));

        Map<String, Object> body = Map.of(
                "messaging_product", "whatsapp",
                "to", phone,
                "type", "text",
                "text", Map.of(
                        "body", message));

        restClient.post().uri(url).header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json").body(body).retrieve().toBodilessEntity();
    }
}
