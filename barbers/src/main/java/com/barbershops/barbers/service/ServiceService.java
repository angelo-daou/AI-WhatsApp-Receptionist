package com.barbershops.barbers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.barbershops.barbers.entity.Services;
import com.barbershops.barbers.repository.ServiceRepository;

@Service
public class ServiceService {
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public Services getService(Integer id) {
        Optional<Services> service = serviceRepository.findById(id);

        if (service.isPresent()) {
            return service.get();
        }
        return null;
    }

    public List<Services> getServices() {
        return serviceRepository.findAll();
    }

    public Services createServices(Services service) {
        return serviceRepository.save(service);
    }

    public Services updateServices(Integer id, Services service) {
        if (serviceRepository.existsById(id)) {
            Services oldService = serviceRepository.findById(id).get();
            oldService.setDurationMinutes(service.getDurationMinutes());
            oldService.setName(service.getName());
            oldService.setPrice(service.getPrice());
            oldService.setShop(service.getShop());
            return serviceRepository.save(oldService);
        }
        return null;
    }

    public Boolean deleteServices(Integer id) {
        if (serviceRepository.existsById(id)) {
            serviceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
