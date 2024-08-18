package com.example.demo.installation;

import com.example.demo.charges.Charges;
import com.example.demo.client.Client;
import com.example.demo.client.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class InstallationService {
    @Autowired
    InstallationRepository installationRepository;
    @Autowired
    ClientRepository clientRepository;

    public List<Installation> getAllInstallations() {
        return installationRepository.findAll();
    }
    @Transactional
    public Set<Installation> getAllInstallationsForClient(Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        return client.get().getInstallations();
    }

    @Transactional
    public List<Charges> getChargesForClientInstallations(Long clientId) {
        List<Installation> installations = installationRepository.findInstallationsByClientId(clientId);
        List<Charges> charges = new ArrayList<>();

        for (Installation installation : installations) {
            charges.addAll(installation.getChargesList());
        }

        return charges;
    }


    public void saveInstallation(Installation installation) {
        installationRepository.save(installation);
    }

    public Installation getInstallationById(Long id) {
        return installationRepository.findById(id).orElse(null);
    }

    public void deleteInstallationById(Long id) {
        installationRepository.deleteById(id);
    }


}
