package com.example.demo.client;

import com.example.demo.charges.Charges;
import com.example.demo.installation.Installation;
import com.example.demo.payment.Payment;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }

    @Transactional
    public void addInstallationToClient(Long clientId, Installation installation) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client != null) {
            client.getInstallations().add(installation);
            installation.setClient(client);
            clientRepository.save(client);
        }
    }

    @Transactional
    public BigDecimal getClientBalance(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Client not found"));
        BigDecimal balance = BigDecimal.ZERO;

        for (Installation installation : client.getInstallations()) {
            for (Charges charge : installation.getChargesList()) {
                balance = balance.subtract(charge.getAmountToPay());
            }
        }

        for (Installation installation : client.getInstallations()) {
            for (Payment payment : installation.getPaymentList()) {
                balance = balance.add(payment.getAmountPaid());
            }
        }

        return balance;
    }

    public Client updateClient(Long id, Client updatedClient) {
        Client existingClient = clientRepository.findById(updatedClient.getId()).orElse(null);

        if (existingClient != null) {
            existingClient.setName(updatedClient.getName());
            existingClient.setSurname(updatedClient.getSurname());
            return clientRepository.save(existingClient);
        } else {
            return null;
        }
    }
}
