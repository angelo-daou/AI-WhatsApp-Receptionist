package com.barbershops.barbers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.barbershops.barbers.entity.Client;
import com.barbershops.barbers.entity.Shop;
import com.barbershops.barbers.repository.ClientRepository;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client getClient(Integer id) {
        Optional<Client> client = clientRepository.findById(id);

        if (client.isPresent()) {
            return client.get();
        }
        return null;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Integer id, Client client) {
        if (clientRepository.existsById(id)) {
            Client oldClient = clientRepository.findById(id).get();
            oldClient.setName(client.getName());
            oldClient.setPhoneNum(client.getPhoneNum());
            oldClient.setShop(client.getShop());
            return clientRepository.save(oldClient);
        }
        return null;
    }

    public Boolean deleteClient(Integer id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Client createClient(String name, String phoneNum, Shop shop) {
        return clientRepository.save(new Client(name, phoneNum, shop));
    }

}
