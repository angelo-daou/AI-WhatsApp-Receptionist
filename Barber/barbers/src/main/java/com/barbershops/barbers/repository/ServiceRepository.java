package com.barbershops.barbers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barbershops.barbers.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

}
