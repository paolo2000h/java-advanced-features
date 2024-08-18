package com.example.demo.client;

import com.example.demo.client.Client;
import com.example.demo.client.ClientRepository;
import com.example.demo.client.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a client by ID")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> client = Optional.ofNullable(clientService.getClientById(id));
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @ApiOperation(value = "Add a new client")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        Client newClient = clientService.saveClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing client by ID")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        Optional<Client> existingClient = Optional.ofNullable(clientService.getClientById(id));
        if (existingClient.isPresent()) {
            Client updatedClient = clientService.updateClient(id, client);
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a client by ID")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        Optional<Client> client = Optional.ofNullable(clientService.getClientById(id));
        if (client.isPresent()) {
            clientService.deleteClientById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
