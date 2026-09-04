package com.barbershops.barbers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barbershops.barbers.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
