package com.barbershops.barbers.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Barber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer barberId;

    private String name;

    // @ManyToOne which is a relationship annotation for JPA that means "One barber
    // or more/many barber can belong to a specific shop"
    @ManyToOne
    private Shop shop;

    public Barber() {
    }

    public Integer getBarberId() {
        return barberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
