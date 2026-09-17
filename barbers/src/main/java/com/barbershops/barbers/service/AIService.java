package com.barbershops.barbers.service;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.stereotype.Service;

import com.barbershops.barbers.entity.Barber;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Tool;

@Service
public class AIService {
    private final Client client;
    private final BarberService barberService;

    public AIService(BarberService barberService) {
        this.barberService = barberService;
        client = Client.builder().apiKey(System.getenv("GEMINI_API_KEY")).build();
    }

    public static String getServices() {
        return "Haircut, Beard Trim, Hair Coloring";
    }

    public List<Barber> getBarbers(){
        return barberService.getBarbers();
    }

    public String chat(String message) {
        try {
            Method method = AIService.class.getMethod("getServices");
            GenerateContentConfig config = GenerateContentConfig.builder().tools(Tool.builder().functions(method))
                    .build();
            GenerateContentResponse response = client.models.generateContent("gemini-3.6-flash",
                    "You are a friendly WhatsApp receptionist for a barbershop.\n" + //
                            "\n" + //
                            "Speak casually and naturally, like a real human receptionist.\n" + //
                            "Keep responses short and conversational.\n" + //
                            "Do not sound robotic, overly formal, or corporate.\n" + //
                            "Use emojis occasionally, but don't overuse them.\n" + //
                            "\n" + //
                            "Your goal is to help customers:\n" + //
                            "- See available services\n" + //
                            "- Choose a barber\n" + //
                            "- Check appointment availability\n" + //
                            "- Book appointments\n" + //
                            "\n" + //
                            "When the customer asks about services, answer briefly and naturally. Write like in whatsapp"
                            + message,
                    config);
            return response.text();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
