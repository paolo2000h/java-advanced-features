package com.example.demo.charges;

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
}
