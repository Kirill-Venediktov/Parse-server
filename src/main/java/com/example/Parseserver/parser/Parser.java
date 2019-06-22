package com.example.Parseserver.parser;

import com.example.Parseserver.entity.Client;
import com.example.Parseserver.entity.Goods;
import com.example.Parseserver.repository.ClientRepository;
import com.example.Parseserver.repository.GoodsRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@Component
public class Parser {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TaskExecutor taskExecutor;

    @Bean
    //second, minute, hour, day of month, month, day(s) of week
    //    @Scheduled(cron = "001***", zone = "Europe/Moscow")
    public void start(){
        System.out.println("Start");

        Iterable<Client> clients = clientRepository.findAll();
        for (Client client : clients){
            ArrayList<String> links = client.getLinks();
            List<String> parsedLinks = new ArrayList<>();
            for (String link : links){
                ParserThread thread = applicationContext.getBean(ParserThread.class);
                thread.setClient(client);
                thread.setLink(link);
                taskExecutor.execute(thread);
            }
        }
//
//        Document document = null;
//        try {
//            document = Jsoup.connect("https://www.ebay.com/itm/Elastic-Premium-Stretch-French-Style-Nato-Watch-Strap-20mm-22mm/153524679083?hash=item23bec8b1ab:m:mNOdUz2PhsoHaGxqk_pHWNA&var=453512245006").get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String title = document.title();
//        String priceRubTag = document.select("#prcIsumConv").text();
//
//        Elements priceTag  = document.select(".notranslate#prcIsum");
//
//        title = title.substring(0, title.indexOf("|")).trim();
//
//        priceRubTag = priceRubTag.replaceAll("[a-zA-Zа-яА-Я().]*", "")
//                .replaceAll("\\s","")
//                .replaceAll(",", ".");
//
//        double priceRub = Double.parseDouble(priceRubTag);
//
//        String currency = priceTag.text().substring(0,priceTag.text().indexOf(" "));
//
//        double price = Double.parseDouble(priceTag.attr("content"));
//
//        Goods goods = new Goods();
//        goods.setTitle(title);
//        goods.setPriceRub(priceRub);
//        goods.setCurrency(currency);
//        goods.setPrice(price);
//        goods.setDate(LocalDate.now());
//
//        goodsRepository.save(goods);
//
//        System.out.println("title: " + title);
//        System.out.println("priceRub: " + priceRub);
//        System.out.println("currency: " + currency);
//        System.out.println("price: "  + price);
//

    }


}
