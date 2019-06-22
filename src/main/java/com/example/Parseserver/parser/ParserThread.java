package com.example.Parseserver.parser;

import com.example.Parseserver.entity.Client;
import com.example.Parseserver.entity.Goods;
import com.example.Parseserver.repository.ClientRepository;
import com.example.Parseserver.repository.GoodsRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class ParserThread implements Runnable {
    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    ClientRepository clientRepository;

    private String link;
    private Client client;

    public ParserThread() {
    }

    public ParserThread(String link, Client client) {
        this.link = link;
        this.client = client;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        if (link.contains("https://www.ebay.com/")) {
            Document document = null;
            try {
                document = Jsoup.connect(link).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String title = document.title();
            String priceRubTag = document.select("#prcIsumConv").text();

            Elements priceTag  = document.select(".notranslate#prcIsum");

            title = title.substring(0, title.indexOf("|")).trim();

            priceRubTag = priceRubTag.replaceAll("[a-zA-Zа-яА-Я().]*", "")
                    .replaceAll("\\s","")
                    .replaceAll(",", ".");

            double priceRub = Double.parseDouble(priceRubTag);

            String currency = priceTag.text().substring(0,priceTag.text().indexOf(" "));

            double price = Double.parseDouble(priceTag.attr("content"));

            Goods goods = new Goods();
            goods.setTitle(title);
            goods.setPriceRub(priceRub);
            goods.setCurrency(currency);
            goods.setPrice(price);
            goods.setDate(LocalDate.now());
            goods.getClients().add(client);

//            List<Client> clients = new ArrayList<>();
//            clients.add(client);
//            goods.setClients(clients);

//            goodsRepository.save(goods);

            client.getGoods().add(goods);
            clientRepository.save(client);

            System.out.println("title: " + title);
            System.out.println("priceRub: " + priceRub);
            System.out.println("currency: " + currency);
            System.out.println("price: "  + price);
        }
    }
}
