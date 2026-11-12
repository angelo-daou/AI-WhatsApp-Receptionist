package com.barbershops.barbers.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;

    @ManyToOne
    private Shop shop;

    @ManyToOne
    private Barber barber;

    @ManyToOne
    private Services service;

    @ManyToOne
    private Client client;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Booking() {
    }

    public Booking(Shop shop, Barber barber, Services service, Client client, LocalDateTime startTime,
            LocalDateTime endTime) {
        this.shop = shop;
        this.barber = barber;
        this.service = service;
        this.client = client;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Barber getBarber() {
        return barber;
    }

    public void setBarber(Barber barber) {
        this.barber = barber;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

}
