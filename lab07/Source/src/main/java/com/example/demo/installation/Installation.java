package com.example.demo.installation;

import com.example.demo.charges.Charges;
import com.example.demo.client.Client;
import com.example.demo.payment.Payment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Installation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private Long routerNumber;
    private String serviceType;
    @ToStringExclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ToStringExclude
    @OneToMany(mappedBy = "installation", fetch = FetchType.EAGER)
    private Set<Charges> chargesList = new HashSet<>();

    @ToStringExclude
    @OneToMany(mappedBy = "installation", fetch = FetchType.EAGER)
    private Set<Payment> paymentList = new HashSet<>();

    public void addPayment(Payment payment) {
        payment.setInstallation(this);
        this.paymentList.add(payment);
    }

    public void removePayment(Payment payment) {
        this.paymentList.remove(payment);
        payment.setInstallation(null);
    }

    public void addCharges(Charges charges) {
        charges.setInstallation(this);
        this.chargesList.add(charges);
    }

    public void removeCharges(Charges charges) {
        this.chargesList.remove(charges);
        charges.setInstallation(null);
    }

    public Set<Charges> getChargesList() {
        return chargesList;
    }

    public Set<Payment> getPaymentList() {
        return paymentList;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getRouterNumber() {
        return routerNumber;
    }

    public void setRouterNumber(Long routerNumber) {
        this.routerNumber = routerNumber;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Installation{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", routerNumber=" + routerNumber +
                ", serviceType='" + serviceType + '\'' +
               // ", client=" + client +
                '}';
    }
}
