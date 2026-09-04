package com.barbershops.barbers.entity;

import java.util.List;
import java.util.Vector;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shopId;

    private String name;

    @OneToMany(mappedBy = "shop")
    private List<Barber> barbers = new Vector<>();

    public Shop() { // default constructor for the JPA (it will instantiate the entity from the
                    // default constr THEN put the values by populating the fields)
    }

    public Integer getShopId() {
        return shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Barber> getBarbers() {
        return barbers;
    }

    public void setBarbers(List<Barber> barbers) {
        this.barbers = barbers;
    }
}
