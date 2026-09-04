package com.barbershops.barbers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barbershops.barbers.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}
