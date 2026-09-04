package com.barbershops.barbers.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.barbershops.barbers.entity.Shop;
import com.barbershops.barbers.service.ShopService;

@RestController
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/shops")
    public List<Shop> getShops() {
        return shopService.getShops();
    }

    @PostMapping("/shops")
    public ResponseEntity<Shop> postShop(@RequestBody Shop shop) {
        return shopService.createShop(shop);
    }
}
