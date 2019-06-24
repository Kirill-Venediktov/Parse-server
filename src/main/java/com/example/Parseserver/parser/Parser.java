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
    public void start() {
        System.out.println("Start");

        Iterable<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            ArrayList<String> links = client.getLinks();
            for (String link : links) {
                if (link.contains("https://www.ebay.com/")) {
                ParserThread thread = applicationContext.getBean(ParserThread.class);
                thread.setClient(client);
                thread.setLink(link);
                taskExecutor.execute(thread);
                }
            }


        }


    }
}
