package com.example.demo.priceList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceListService {
    @Autowired
    private PriceListRepository priceListRepository;

    public List<PriceList> getAllPriceLists() {
        return priceListRepository.findAll();
    }

    public void savePriceList(PriceList priceList) {
        priceListRepository.save(priceList);
    }

    public PriceList getPriceListById(Long id) {
        return priceListRepository.findById(id).orElse(null);
    }

    public void deletePriceListById(Long id) {
        priceListRepository.deleteById(id);
    }

}
