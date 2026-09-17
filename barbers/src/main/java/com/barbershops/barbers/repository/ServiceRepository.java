package com.barbershops.barbers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barbershops.barbers.entity.Services;

public interface ServiceRepository extends JpaRepository<Services, Integer> {

}
