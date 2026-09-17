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
            return shop.get();
        }
        return null;
    }

    public Shop createShop(Shop shop) {
        return shopRepository.save(shop); // should have validation like if blank attribute etc. and correct
                                          // http responses
    }

    public boolean updateShop(Integer id, Shop shop) {
        if (shopRepository.existsById(id)) {
            Shop foundShop = shopRepository.findById(id).get(); // it cannot be null bcs of the create method
            foundShop.setName(shop.getName());
            shopRepository.save(foundShop);
            return true;
        }
        return false;
    }

    public boolean deleteShop(Integer id) {
        if (shopRepository.existsById(id)) {
            shopRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
