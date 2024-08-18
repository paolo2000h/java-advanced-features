package com.example.demo.priceList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class PriceList {
    @Id
    @GeneratedValue
    private Long id;
    private String serviceType;
    private BigDecimal price;

    public PriceList() {
    }

    public PriceList(Long id, String serviceType, BigDecimal price) {
        this.id = id;
        this.serviceType = serviceType;
        this.price = price;
    }

    public PriceList(String serviceType, BigDecimal price) {
        this.serviceType = serviceType;
        this.price = price;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "PriceList{" +
                "id=" + id +
                ", serviceType='" + serviceType + '\'' +
                ", price=" + price +
                '}';
    }
}
