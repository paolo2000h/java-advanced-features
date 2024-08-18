package com.example.demo;

import com.example.demo.charges.Charges;
import com.example.demo.charges.ChargesService;
import com.example.demo.client.Client;
import com.example.demo.client.ClientService;
import com.example.demo.installation.Installation;
import com.example.demo.installation.InstallationService;
import com.example.demo.payment.Payment;
import com.example.demo.payment.PaymentService;
import com.example.demo.priceList.PriceList;
import com.example.demo.priceList.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class InternetProvider implements CommandLineRunner {
    @Autowired
    ClientService clientService;
    @Autowired
    InstallationService installationService;
    @Autowired
    PriceListService priceListService;
    @Autowired
    ChargesService chargesService;
    @Autowired
    PaymentService paymentService;

    public static void main(String[] args) {
        SpringApplication.run(InternetProvider.class, args);
    }

    private void setSampleData() {
        Client client1 = new Client();
        client1.setName("John");
        client1.setSurname("Lock");
        clientService.saveClient(client1);

        Client client2 = new Client();
        client2.setName("Adam");
        client2.setSurname("Smith");
        clientService.saveClient(client2);

        Installation i1 = new Installation();
        i1.setClient(client1);
        i1.setServiceType("service1");
        i1.setAddress("Wyspianskiego 76");
        i1.setRouterNumber(101L);
        clientService.addInstallationToClient(client1.getId(), i1);

    }

    @Override
    public void run(String... args) {
        setSampleData();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Clients");
            System.out.println("2. Installations");
            System.out.println("3. Price list");
            System.out.println("4. Charges");
            System.out.println("5. Payments");

            int option = scanner.nextInt();

            switch (option) {
                case 1 -> {
                    System.out.println("1. Add client");
                    System.out.println("2. Show clients");
                    System.out.println("3. Delete client");
                    System.out.println("4. Back");

                    int option2 = scanner.nextInt();

                    switch (option2) {
                        case 1 -> {
                            System.out.println("Name:");
                            String name = scanner.next();
                            System.out.println("Surname:");
                            String surname = scanner.next();
                            Client client = new Client();
                            client.setName(name);
                            client.setSurname(surname);
                            clientService.saveClient(client);
                            System.out.println("Added client!");
                        }
                        case 2 -> {
                            List<Client> clients = clientService.getAllClients();
                            for (Client c : clients) {
                                System.out.println(c);
                            }
                        }
                        case 3 -> {
                            System.out.println("Delete client by id:");
                            int clientId = scanner.nextInt();
                            clientService.deleteClientById((long) clientId);
                        }
                    }
                }
                case 2 -> {
                    System.out.println("1. Add installation");
                    System.out.println("2. Show installations");
                    System.out.println("3. Delete installation");
                    System.out.println("4. Back");

                    int option2 = scanner.nextInt();

                    switch (option2) {
                        case 1 -> {
                            System.out.println("Address:");
                            String address = scanner.next();
                            System.out.println("Router number:");
                            Long routerNumber = scanner.nextLong();
                            System.out.println("Service type:");
                            String serviceType = scanner.next();
                            System.out.println("Client id:");
                            Long id = scanner.nextLong();
                            Client client = clientService.getClientById(id);
                            Installation installation = new Installation();
                            installation.setAddress(address);
                            installation.setRouterNumber(routerNumber);
                            installation.setServiceType(serviceType);
                            installation.setClient(client);
                            clientService.addInstallationToClient(id, installation);
                            System.out.println("Added installation!");
                        }
                        case 2 -> {
                            List<Installation> installations = installationService.getAllInstallations();
                            for (Installation i : installations) {
                                System.out.println(i);
                            }
                        }
                        case 3 -> {
                            System.out.println("Delete installation by id:");
                            int installationId = scanner.nextInt();
                            installationService.deleteInstallationById((long) installationId);
                        }
                        case 4 -> {
                        }
                    }
                }
                case 3 -> {
                    System.out.println("1. Add price list");
                    System.out.println("2. Show price lists");
                    System.out.println("3. Delete price list");
                    System.out.println("4. Back");

                    int option2 = scanner.nextInt();

                    switch (option2) {
                        case 1 -> {
                            System.out.println("Service type:");
                            String serviceType = scanner.next();
                            System.out.println("Price:");
                            Long price = scanner.nextLong();
                            PriceList priceList = new PriceList();
                            priceList.setPrice(BigDecimal.valueOf(price));
                            priceList.setServiceType(serviceType);
                            priceListService.savePriceList(priceList);
                            System.out.println("Added price list!");
                        }
                        case 2 -> {
                            List<PriceList> priceLists = priceListService.getAllPriceLists();
                            for (PriceList priceList : priceLists) {
                                System.out.println(priceList);
                            }
                        }
                        case 3 -> {
                            System.out.println("Delete price list by id:");
                            int priceListId = scanner.nextInt();
                            priceListService.deletePriceListById((long) priceListId);
                        }
                        case 4 -> {
                        }
                    }
                }
                case 4 -> {
                    System.out.println("1. Add charges");
                    System.out.println("2. Show charges");
                    int charges = scanner.nextInt();

                    switch (charges) {
                        case 1 -> {
                            System.out.println("Amount to pay:");
                            long amount = scanner.nextLong();
                            System.out.println("Due date days from today:");
                            int days = scanner.nextInt();
                            LocalDate localDate = LocalDate.now();
                            localDate = localDate.plusDays(days);
                            System.out.println("Installation id:");
                            int installation = scanner.nextInt();
                            Installation installation1 = installationService.getInstallationById((long) installation);
                            Charges charges1 = new Charges();
                            charges1.setPaymentDueDate(localDate);
                            charges1.setAmountToPay(BigDecimal.valueOf(amount));
                            charges1.setInstallation(installation1);
                            installation1.addCharges(charges1);
                            chargesService.saveCharges(charges1);
                            installationService.saveInstallation(installation1);

                        }
                        case 2 -> {
                            System.out.println("Client id:");
                            int client = scanner.nextInt();
                            List<Charges> chargesList = installationService.getChargesForClientInstallations((long) client);
                            for (Charges c : chargesList) {
                                System.out.println("Id: " + c.getId() + " " + c.getInstallation() + "Amount: " + c.getAmountToPay() + " " + c.getPaymentDueDate());
                            }
                        }
                    }
                }
                case 5 -> {
                    System.out.println("1. Add payment");
                    System.out.println("2. Show client balance");
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1 -> {
                            System.out.println("Amount to pay:");
                            long amount = scanner.nextLong();
                            LocalDate localDate = LocalDate.now();
                            Payment payment = new Payment();
                            payment.setPaymentDate(localDate);
                            payment.setAmountPaid(BigDecimal.valueOf(amount));
                            System.out.println("Installation id: ");
                            int i = scanner.nextInt();
                            Installation installation = installationService.getInstallationById((long) i);
                            installation.addPayment(payment);
                            installationService.saveInstallation(installation);
                            paymentService.savePayment(payment);
                        }
                        case 2 -> {
                            System.out.println("Client id:");
                            int client = scanner.nextInt();
                            System.out.println(clientService.getClientBalance((long) client));
                        }
                    }
                }
            }
        }
    }
}
