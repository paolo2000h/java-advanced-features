package com.example.demo.charges;

import com.example.demo.installation.Installation;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Charges {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate paymentDueDate;
    private BigDecimal amountToPay;
    @ManyToOne
    @JoinColumn(name = "installation_id")
    private Installation installation;

    public Charges() {
    }

    public Charges(LocalDate paymentDueDate, BigDecimal amountToPay) {
        this.paymentDueDate = paymentDueDate;
        this.amountToPay = amountToPay;
    }

    public Charges(Long id, LocalDate paymentDueDate, BigDecimal amountToPay) {
        this.id = id;
        this.paymentDueDate = paymentDueDate;
        this.amountToPay = amountToPay;
    }

    public Installation getInstallation() {
        return installation;
    }

    public void setInstallation(Installation installation) {
        this.installation = installation;
    }

    public LocalDate getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(LocalDate paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public BigDecimal getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(BigDecimal amountToPay) {
        this.amountToPay = amountToPay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
