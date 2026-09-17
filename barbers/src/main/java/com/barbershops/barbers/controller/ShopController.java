package com.barbershops.barbers.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barbershops.barbers.entity.Shop;
import com.barbershops.barbers.service.ShopService;

@RequestMapping("/shop")
@RestController
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    public List<Shop> getShops() {
        return shopService.getShops();
    }

    @GetMapping("/{id}")
    public Shop getShop(@PathVariable Integer id) {
        return shopService.getShop(id);
    }

    @PostMapping
    public Shop postShop(@RequestBody Shop shop) {
        return shopService.createShop(shop);
    }

    @PutMapping("/{id}")
    public Boolean putShop(@PathVariable Integer id, @RequestBody Shop shop) {
        return shopService.updateShop(id, shop);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteShop(@PathVariable Integer id) {
        return shopService.deleteShop(id);
    }
}
