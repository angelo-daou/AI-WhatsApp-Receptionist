# AI WhatsApp Receptionist

A booking backend for barbershops with an AI receptionist that chats with clients the way a front-desk person would on WhatsApp.

The idea came from three years of working in a salon. Clients should be able to book with a specific barber just by chatting, while the backend keeps barbers, services and appointments organized without double-booking anyone.

## Features

- **Multi-shop data model**: shops, barbers, clients, services and bookings, each with full CRUD over a REST API
- **Conflict detection**: a booking is rejected if it overlaps another booking for the same barber, if its end time isn't after its start time, or if the barber, client and service don't all belong to the same shop
- **AI receptionist**: a Gemini-powered chat endpoint with a casual, WhatsApp-style persona, using function calling to answer questions from the shop's data

## Tech stack

Java 17 · Spring Boot · Spring Data JPA / Hibernate · PostgreSQL · Google Gen AI SDK (Gemini)

## Project structure

```
barbers/src/main/java/com/barbershops/barbers/
├── entity/       Shop, Barber, Client, Services, Booking
├── repository/   Spring Data JPA repositories
├── service/      Business logic (booking validation, AI receptionist)
└── controller/   REST endpoints
```

## API

| Resource | Base path |
|---|---|
| Shops | `/shop` |
| Barbers | `/shop/barber` |
| Clients | `/shop/client` |
| Services | `/shop/service` |
| Bookings | `/shop/barber/booking` |
| AI receptionist | `POST /ai` (plain-text message in, reply out) |

Each resource supports `GET` (all), `GET /{id}`, `POST`, `PUT /{id}` and `DELETE /{id}`.

## Running locally

1. Create a PostgreSQL database named `AIreceptionist`.
2. Set the environment variables:
   ```
   DB_PASSWORD=your_postgres_password
   GEMINI_API_KEY=your_gemini_api_key
   ```
3. Start the app:
   ```
   cd barbers
   ./mvnw spring-boot:run
   ```
4. Try the receptionist:
   ```
   curl -X POST localhost:8080/ai -H "Content-Type: text/plain" -d "Hey, what services do you have?"
   ```

Tables are created automatically on startup (`ddl-auto=update`).
