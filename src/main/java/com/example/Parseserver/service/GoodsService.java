package com.example.Parseserver.service;

import com.example.Parseserver.entity.Client;
import com.example.Parseserver.entity.Goods;
import com.example.Parseserver.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsService {
    private GoodsRepository goodsRepository;
    private ClientService clientService;

    @Autowired

    public GoodsService(GoodsRepository goodsRepository, ClientService clientService) {
        this.goodsRepository = goodsRepository;
        this.clientService = clientService;
    }


    public List<Goods> getUniqueGoods () {
        Client client = clientService.findCurrentClient();
        int count = 0;
        List<Goods> uniqueGoods = new ArrayList<>();
        for (Goods goods : client.getGoods()){
            for(Goods ugoods : uniqueGoods){
                if (goods.getTitle().equals(ugoods.getTitle())){
                    if (goods.getDate().isAfter(ugoods.getDate())){
                        uniqueGoods.remove(ugoods);
                        uniqueGoods.add(goods);
                    }
                    count = 1;
                    break;
                }
            }
            if(count==0) {
                uniqueGoods.add(goods);
            }
            count = 0;
        }
        return uniqueGoods;
    }
}
