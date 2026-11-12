package com.barbershops.barbers.controller;

import org.springframework.web.bind.annotation.RestController;

import com.barbershops.barbers.dto.WhatsAppWebhook;
import com.barbershops.barbers.service.WhatsAppService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/webhook")
@RestController
public class WhatsAppController {
    private final WhatsAppService whatsAppService;

    public WhatsAppController(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }

    @GetMapping
    public ResponseEntity<?> getMethodName(@RequestParam("hub.mode") String mode,
            @RequestParam("hub.verify_token") String token, @RequestParam("hub.challenge") String challenge) {

        if ("subscribe".equals(mode) && "barbershop_webhook_123".equals(token)) {
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping
    public ResponseEntity<?> receiveMessage(@RequestBody WhatsAppWebhook webhook) {
        String phoneNum = webhook.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0).getFrom();
        String body = webhook.getEntry().get(0).getChanges().get(0).getValue().getMessages().get(0).getText().getBody();
        System.out.println("Phone: " + phoneNum);
        System.out.println("Message: " + body);
        whatsAppService.messageProcessing(phoneNum, body);
        return ResponseEntity.ok().build();
    }
}
