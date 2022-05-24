package com.globant.clientrest.controller;

import com.globant.clientrest.entity.Client;
import com.globant.clientrest.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public Collection<Client> getAllClients() {
        return clientService.findAll();
    }

    @GetMapping("/clients/{id}")
    public Client getClientById(@PathVariable final Long id) {
        return clientService.findById(id);
    }

    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@Valid @RequestBody final Client client) {
        final Client newClient = clientService.save(client);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newClient.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/clients/{id}")
    public Client updateClient(@Valid @RequestBody final Client client, @PathVariable final Long id) {
        client.setId(id);
        return clientService.update(client);
    }

    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable final Long id) {
        clientService.delete(id);
    }

}
