package com.example.Parseserver.controllers;

import com.example.Parseserver.entity.Client;
import com.example.Parseserver.entity.Goods;
import com.example.Parseserver.repository.ClientRepository;
import com.example.Parseserver.repository.GoodsRepository;
import com.example.Parseserver.service.ClientService;
import com.example.Parseserver.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StatisticsController {

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/admin/statistics", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("allGoods", goodsService.getUniqueGoods());
        return "/admin/statistics";
    }

    @RequestMapping(value = "/admin/statistics", method = RequestMethod.POST)
    public String chooseGood(Model model, @RequestParam(name = "goodsId", required = false) int goodsId) {
        String title = goodsRepository.findById(goodsId).get().getTitle();
        model.addAttribute("goods", goodsRepository.findByTitle(title));
        model.addAttribute("allGoods", goodsService.getUniqueGoods());
        return "/admin/statistics";
    }

//    @RequestMapping(value = "/admin/statistics", method = RequestMethod.GET)
//    public String showStat(Model model, @RequestParam int goodsId) {
//        model.addAttribute("goods", goodsRepository.findById(goodsId));
//        return "/admin/statistics";
//    }
}
