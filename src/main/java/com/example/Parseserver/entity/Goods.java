package com.example.Parseserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Builder
@AllArgsConstructor
@Entity
@Table(name = "goods")
public class Goods {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "goods_id")
    private int id;

    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private double price;
    private String currency;
    private double priceRub;

//    @ManyToOne(cascade = CascadeType.ALL)
//    private Client client;

    public Goods() {
    }

//    public Client getClient() {
//        return client;
//    }
//
//    public void setClient(Client client) {
//        this.client = client;
//    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    //
    @ManyToMany(mappedBy = "goods")
    private List<Client> clients = new ArrayList<>();
//
//    public List<Client> getClients() {
//        return clients;
//    }
//
//    public void setClients(List<Client> clients) {
//        this.clients = clients;
//    }



    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getPriceRub() {
        return priceRub;
    }

    public void setPriceRub(double priceRub) {
        this.priceRub = priceRub;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
