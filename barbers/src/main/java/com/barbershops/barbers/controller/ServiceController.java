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

import com.barbershops.barbers.entity.Services;
import com.barbershops.barbers.service.ServiceService;

@RequestMapping("/shop/service")
@RestController
public class ServiceController {
    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("/{id}")
    public Services getService(@PathVariable Integer id) {
        return serviceService.getService(id);
    }

    @GetMapping
    public List<Services> getServices() {
        return serviceService.getServices();
    }

    @PostMapping
    public Services postService(@RequestBody Services service) {
        return serviceService.createServices(service);
    }

    @PutMapping("/{id}")
    public Services putService(@PathVariable Integer id, @RequestBody Services service) {
        return serviceService.updateServices(id, service);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteServices(@PathVariable Integer id) {
        return serviceService.deleteServices(id);
    }
}
