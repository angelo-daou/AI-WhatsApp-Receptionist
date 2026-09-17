package com.barbershops.barbers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barbershops.barbers.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer> {

}
