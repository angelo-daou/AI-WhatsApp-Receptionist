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

import com.barbershops.barbers.entity.Client;
import com.barbershops.barbers.service.ClientService;

@RequestMapping("/shop/client")
@RestController
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable Integer id) {
        return clientService.getClient(id);
    }

    @GetMapping
    public List<Client> getClients() {
        return clientService.getClients();
    }

    @PostMapping
    public Client postClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @PutMapping("/{id}")
    public Client putClient(@PathVariable Integer id, @RequestBody Client client) {
        return clientService.updateClient(id, client);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteClient(@PathVariable Integer id) {
        return clientService.deleteClient(id);
    }
}
