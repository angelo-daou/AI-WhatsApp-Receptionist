package com.barbershops.barbers.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.barbershops.barbers.entity.Barber;
import com.barbershops.barbers.entity.Booking;
import com.barbershops.barbers.entity.Services;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.FunctionCall;
import com.google.genai.types.FunctionDeclaration;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.google.genai.types.Schema;
import com.google.genai.types.Tool;

@Service
public class AIService {
        private final Client client;
        private final BarberService barberService;
        private final ServiceService serviceService;
        private final BookingService bookingService;
        private HashMap<String, List<Content>> contentMap = new HashMap<>(); // <phone num and actual content history>

        public AIService(BarberService barberService, ServiceService serviceService, BookingService bookingService) {
                this.serviceService = serviceService;
                this.barberService = barberService;
                this.bookingService = bookingService;
                client = Client.builder().apiKey(System.getenv("GEMINI_API_KEY")).build();
        }

        public List<Services> getServices() {
                return serviceService.getServices();
        }

        public List<Barber> getBarbers() {
                return barberService.getBarbers();
        }

        public Boolean checkAvailability(Integer barberId, LocalDateTime startTime, LocalDateTime endTime) {
                return bookingService.checkAvailability(barberId, startTime, endTime);
        }

        public Booking createBooking(Integer barberId, Integer serviceId, String name, String phoneNum,
                        LocalDateTime startTime) {
                return bookingService.createBooking(barberId, serviceId, name, phoneNum, startTime);
        }

        private Tool getTools() {
                FunctionDeclaration getBarbers = FunctionDeclaration.builder().name("getBarbers")
                                .description("To get a shop's barbers, mainly for their names").build();
                FunctionDeclaration getServices = FunctionDeclaration.builder().name("getServices")
                                .description("To get a shop's services, mainly for their names").build();
                FunctionDeclaration checkAvailability = FunctionDeclaration.builder()
                                .name("checkAvailability")
                                .description(
                                                "Check whether a specific barber is available between the given start and end times. Use this before creating a booking.")
                                .parameters(
                                                Schema.builder()
                                                                .type("OBJECT")
                                                                .properties(Map.of(
                                                                                "barberId", Schema.builder()
                                                                                                .type("INTEGER")
                                                                                                .description("The ID of the barber")
                                                                                                .build(),
                                                                                "startTime", Schema.builder()
                                                                                                .type("STRING")
                                                                                                .description("The appointment start time in ISO-8601 format")
                                                                                                .build(),
                                                                                "endTime", Schema.builder()
                                                                                                .type("STRING")
                                                                                                .description("The appointment end time in ISO-8601 format")
                                                                                                .build()))
                                                                .required(List.of("barberId", "startTime", "endTime"))
                                                                .build())
                                .build();
                FunctionDeclaration createBooking = FunctionDeclaration.builder()
                                .name("createBooking")
                                .description(
                                                "Create a booking for a specific client, barber, and service at the requested start time. Only use after checking availability.")
                                .parameters(
                                                Schema.builder()
                                                                .type("OBJECT")
                                                                .properties(Map.of(
                                                                                "barberId", Schema.builder()
                                                                                                .type("INTEGER")
                                                                                                .description("The ID of the barber")
                                                                                                .build(),
                                                                                "serviceId", Schema.builder()
                                                                                                .type("INTEGER")
                                                                                                .description("The ID of the service")
                                                                                                .build(),
                                                                                "name", Schema.builder()
                                                                                                .type("STRING")
                                                                                                .description("The name of the client")
                                                                                                .build(),
                                                                                "phoneNum", Schema.builder()
                                                                                                .type("STRING")
                                                                                                .description("The phone number of the client")
                                                                                                .build(),
                                                                                "startTime", Schema.builder()
                                                                                                .type("STRING")
                                                                                                .description("The appointment start time in ISO-8601 format")
                                                                                                .build()))
                                                                .required(List.of("barberId", "serviceId", "name",
                                                                                "phoneNum", "startTime"))
                                                                .build())
                                .build();
                return Tool.builder().functionDeclarations(getBarbers, getServices, checkAvailability, createBooking)
                                .build();
        }

        private Object executeTool(FunctionCall functionCall) {
                String functionName = functionCall.name().orElse("");
                if (functionName.equals("getBarbers")) {
                        return getBarbers();
                }
                if (functionName.equals("getServices")) {
                        return getServices();
                }
                if (functionName.equals("checkAvailability")) {
                        Map<String, Object> args = functionCall.args().orElse(Map.of());
                        Integer barberId = ((Number) args.get("barberId")).intValue();
                        LocalDateTime startTime = LocalDateTime.parse((String) args.get("startTime"));
                        LocalDateTime endTime = LocalDateTime.parse((String) args.get("endTime"));
                        return checkAvailability(barberId, startTime, endTime);
                }
                if (functionName.equals("createBooking")) {
                        Map<String, Object> args = functionCall.args().orElse(Map.of());
                        Integer barberId = ((Number) args.get("barberId")).intValue();
                        Integer serviceId = ((Number) args.get("serviceId")).intValue();
                        String name = (String) args.get("name");
                        String phoneNum = (String) args.get("phoneNum");
                        LocalDateTime startTime = LocalDateTime.parse((String) args.get("startTime"));
                        return createBooking(barberId, serviceId, name, phoneNum, startTime);
                }
                return null;
        }

        public Object chat(String phone, String message) {
                String today = LocalDateTime.now().toString();
                GenerateContentConfig config = GenerateContentConfig.builder()
                                .systemInstruction(
                                                Content.builder()
                                                                .parts(
                                                                                List.of(
                                                                                                Part.fromText(
                                                                                                                "You are a friendly WhatsApp receptionist for a barbershop.\n"
                                                                                                                                +
                                                                                                                                "Speak casually and naturally, like a real human receptionist.\n"
                                                                                                                                +
                                                                                                                                "Keep responses short and conversational.\n"
                                                                                                                                +
                                                                                                                                "Do not sound robotic, overly formal, or corporate.\n"
                                                                                                                                +
                                                                                                                                "Use emojis occasionally, but don't overuse them.\n\n"
                                                                                                                                +

                                                                                                                                "Your goal is to help customers:\n"
                                                                                                                                +
                                                                                                                                "- See available services\n"
                                                                                                                                +
                                                                                                                                "- Choose a barber\n"
                                                                                                                                +
                                                                                                                                "- Check appointment availability\n"
                                                                                                                                +
                                                                                                                                "- Book appointments\n\n"
                                                                                                                                +

                                                                                                                                "When the customer asks about services, use the getServices tool.\n"
                                                                                                                                +
                                                                                                                                "When the customer asks about barbers, use the getBarbers tool.\n"
                                                                                                                                +
                                                                                                                                "Never invent services, barbers, prices, or availability.\n"
                                                                                                                                +
                                                                                                                                "The current date and time is "
                                                                                                                                + today
                                                                                                                                + ".\n"
                                                                                                                                +
                                                                                                                                "Use this when interpreting relative dates and times.")))
                                                                .build())
                                .tools(getTools())
                                .build();

                List<Content> contents = contentMap.computeIfAbsent(
                                phone,
                                k -> new ArrayList<>());

                contents.add(
                                Content.builder()
                                                .role("user")
                                                .parts(List.of(Part.fromText(message)))
                                                .build());

                while (true) {

                        GenerateContentResponse response = client.models.generateContent(
                                        "gemini-3.1-flash-lite",
                                        contents,
                                        config);

                        Content modelContent = response.candidates()
                                        .get()
                                        .get(0)
                                        .content()
                                        .get();

                        contents.add(modelContent);

                        if (response.functionCalls() == null
                                        || response.functionCalls().isEmpty()) {

                                return response.text();
                        }

                        for (FunctionCall functionCall : response.functionCalls()) {

                                Object result = executeTool(functionCall);

                                Content functionResponseContent = Content.builder()
                                                .role("user")
                                                .parts(
                                                                List.of(
                                                                                Part.fromFunctionResponse(
                                                                                                functionCall.name()
                                                                                                                .orElse(""),
                                                                                                Map.of("result", result))))
                                                .build();

                                contents.add(functionResponseContent);
                        }
                }
        }

}
