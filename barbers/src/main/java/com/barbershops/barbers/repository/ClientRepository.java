package com.barbershops.barbers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barbershops.barbers.entity.Client;
import com.barbershops.barbers.entity.Shop;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByPhoneNumAndShop(String phoneNum, Shop shop);
}
