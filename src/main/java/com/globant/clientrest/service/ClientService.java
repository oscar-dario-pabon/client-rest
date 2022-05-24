package com.globant.clientrest.service;

import com.globant.clientrest.entity.Client;
import com.globant.clientrest.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Collection<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(final Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente con id " + id + " no fue encontrado"));
    }

    public Client save(final Client client) {
        return clientRepository.save(client);
    }

    public Client update(final Client client) {
        clientRepository.findById(client.getId()).orElseThrow(() -> new EntityNotFoundException("Cliente con id " + client.getId() + " no fue encontrado"));
        return clientRepository.save(client);
    }

    public void delete(final Long id) {
        final Client client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente con id " + id + " no fue encontrado"));
        clientRepository.delete(client);
    }
}
