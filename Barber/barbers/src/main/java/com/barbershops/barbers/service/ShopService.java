package com.barbershops.barbers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.barbershops.barbers.entity.Shop;
import com.barbershops.barbers.repository.ShopRepository;

@Service
public class ShopService {
    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public List<Shop> getShops() {
        return shopRepository.findAll(); // works but eventually bad design because there is a possibility to return
                                         // like 10 thousands of shops
    }

    public Shop getShop(Integer id) {
        Optional<Shop> shop = shopRepository.findById(id);

        if (shop.isPresent()) {
            Shop newShop = shop.get();
            return newShop;
        }
        return null;
    }

    public Shop createShop(Shop shop) {
        if (shop != null) {
            return shopRepository.save(shop); // should have validation like if null etc. and correct http responses
        }
        return null;
    }

    public boolean updateBarber(Integer id, Shop shop) {
        if (shopRepository.existsById(id)) {
            Shop foundShop = shopRepository.findById(id).get(); // it cannot be null bcs of the create method
            foundShop.setName(shop.getName());
            shopRepository.save(foundShop);
            return true;
        }
        return false;
    }

    public boolean deleteBarber(Integer id) {
        if (shopRepository.existsById(id)) {
            shopRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
