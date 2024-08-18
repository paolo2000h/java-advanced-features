package com.example.demo.charges;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChargesService {
    @Autowired
    ChargesRepository chargesRepository;

    public List<Charges> getAllCharges() {
        return chargesRepository.findAll();
    }

    public void saveCharges(Charges charges) {
        chargesRepository.save(charges);
    }

    public Charges getChargesById(Long id) {
        return chargesRepository.findById(id).orElse(null);
    }

    public void deleteChargesById(Long id) {
        chargesRepository.deleteById(id);
    }

    public Charges updateCharge(Long id, Charges charge) {
        if (charge.getId() == null) {
            throw new IllegalArgumentException("Charge ID cannot be null");
        }
        Charges existingCharge = chargesRepository.findById(charge.getId())
                .orElseThrow(() -> new EntityNotFoundException("Charge with ID " + charge.getId() + " not found"));
        existingCharge.setAmountToPay(charge.getAmountToPay());
        existingCharge.setPaymentDueDate(charge.getPaymentDueDate());
        existingCharge.setInstallation(charge.getInstallation());
        return chargesRepository.save(existingCharge);
    }

}
