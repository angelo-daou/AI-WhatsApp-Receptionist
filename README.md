# AI WhatsApp Receptionist

A WhatsApp booking assistant for barbershops. Clients message the shop on WhatsApp, and an AI receptionist answers them, checks which barber is free, and books the appointment in the backend.

The idea came from three years of working in a salon. Clients should be able to book with a specific barber just by chatting, while the backend keeps barbers, services and appointments organized without double-booking anyone.

## How it works

1. A client sends a WhatsApp message. Meta's WhatsApp Cloud API forwards it to the `/webhook` endpoint.
2. The message is handed to Gemini along with that client's earlier messages, so the conversation keeps its context.
3. Gemini decides which tools it needs and calls them through function calling:
   - `getServices` and `getBarbers` to answer from the shop's real data
   - `checkAvailability` to see if a barber is free at the requested time
   - `createBooking` to save the appointment once the client confirms
4. The reply is sent back to the client on WhatsApp through the Graph API.

## Features

- **WhatsApp integration**: webhook verification and incoming messages from the WhatsApp Cloud API, with replies sent back asynchronously
- **AI receptionist with tool calling**: Gemini works from the shop's real services, barbers and schedule, and is instructed never to invent prices or availability
- **Conflict detection**: a booking is rejected if it overlaps another booking for the same barber, if its end time isn't after its start time, or if the barber, client and service don't all belong to the same shop
- **Automatic client records**: a new client is created from their phone number the first time they book
- **Multi-shop data model**: shops, barbers, clients, services and bookings, each with full CRUD over a REST API

## Tech stack

Java 17 · Spring Boot · Spring Data JPA / Hibernate · PostgreSQL · Google Gen AI SDK (Gemini) · WhatsApp Cloud API

## Project structure

```
barbers/src/main/java/com/barbershops/barbers/
├── entity/       Shop, Barber, Client, Services, Booking
├── repository/   Spring Data JPA repositories
├── service/      Booking validation, AI receptionist, WhatsApp messaging
├── controller/   REST endpoints and the WhatsApp webhook
└── dto/          Classes mapping the WhatsApp webhook payload
```

## API

| Resource | Base path |
|---|---|
| WhatsApp webhook | `GET /webhook` (Meta verification), `POST /webhook` (incoming messages) |
| Shops | `/shop` |
| Barbers | `/shop/barber` |
| Clients | `/shop/client` |
| Services | `/shop/service` |
| Bookings | `/shop/barber/booking` |

Each REST resource supports `GET` (all), `GET /{id}`, `POST`, `PUT /{id}` and `DELETE /{id}`.

## Running locally

1. Create a PostgreSQL database named `AIreceptionist`.
2. Set the environment variables:
   ```
   DB_PASSWORD=your_postgres_password
   GEMINI_API_KEY=your_gemini_api_key
   META_ACCESS_TOKEN=your_whatsapp_cloud_api_token
   ```
3. Start the app:
   ```
   cd barbers
   ./mvnw spring-boot:run
   ```
4. Expose port 8080 with a tunnel (for example ngrok) and set `https://<your-url>/webhook` as the webhook in your Meta developer app, using the verify token from `WhatsAppController`.
5. Add a shop, barbers and services through the REST API, then message your WhatsApp test number.

Tables are created automatically on startup (`ddl-auto=update`).
