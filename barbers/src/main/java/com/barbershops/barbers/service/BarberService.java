package com.barbershops.barbers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.barbershops.barbers.entity.Barber;
import com.barbershops.barbers.repository.BarberRepository;

@Service
public class BarberService {
    private final BarberRepository barberRepository;

    public BarberService(BarberRepository barberRepository) {
        this.barberRepository = barberRepository;
    }

    public List<Barber> getBarbers() {
        return barberRepository.findAll();
    }

    public Barber getBarber(Integer id) {
        Optional<Barber> barber = barberRepository.findById(id);

        if (barber.isPresent()) {
            Barber newBar = barber.get();
            return newBar;
        }
        return null;
    }

    public Barber createBarber(Barber barber) {
        return barberRepository.save(barber);
    }

    public Barber updateBarber(Integer id, Barber barber) {
        if (barberRepository.existsById(id)) {
            Barber foundBarber = barberRepository.findById(id).get(); // it cannot be null bcs of the create method
            foundBarber.setName(barber.getName());
            foundBarber.setShop(barber.getShop());
            return barberRepository.save(foundBarber);
        }
        return null;
    }

    public boolean deleteBarber(Integer id) {
        if (barberRepository.existsById(id)) {
            barberRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
