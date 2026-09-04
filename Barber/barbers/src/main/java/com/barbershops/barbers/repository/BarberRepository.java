package com.barbershops.barbers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barbershops.barbers.entity.Barber;

public interface BarberRepository extends JpaRepository<Barber, Integer> {

}
