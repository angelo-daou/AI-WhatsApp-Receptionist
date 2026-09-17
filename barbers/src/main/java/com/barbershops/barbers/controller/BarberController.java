package com.barbershops.barbers.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.barbershops.barbers.entity.Barber;
import com.barbershops.barbers.service.BarberService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/shop/barber")
@RestController
public class BarberController {
    private final BarberService barberService;

    public BarberController(BarberService barberService) {
        this.barberService = barberService;
    }

    @GetMapping("/{id}")
    public Barber getBarber(@PathVariable Integer id) {
        return barberService.getBarber(id);
    }

    @GetMapping
    public List<Barber> getBarbers() {
        return barberService.getBarbers();
    }

    @PostMapping
    public Barber postBarber(@RequestBody Barber barber) {
        return barberService.createBarber(barber);
    }

    @PutMapping("/{i}")
    public Barber putBarber(@PathVariable Integer i, @RequestBody Barber barber) {
        return barberService.updateBarber(i, barber);
    }

    @DeleteMapping("/{i}")
    public Boolean deleteBarber(@PathVariable Integer i) {
        return barberService.deleteBarber(i);
    }
}
