package com.example.Parseserver.controllers;

import com.example.Parseserver.entity.Client;
import com.example.Parseserver.parser.ParserThread;
import com.example.Parseserver.repository.GoodsRepository;
import com.example.Parseserver.service.ClientService;
import com.example.Parseserver.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

@Controller
public class AddGoodsController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TaskExecutor taskExecutor;


    @RequestMapping(value = "/admin/addGoods", method = RequestMethod.GET)
    public String showForm ( Model model){
        return "/admin/add_goods";
    }

    @RequestMapping(value = "/admin/addGoods", method = RequestMethod.POST)
    public String submitForm (@RequestParam String link, Model model){
        model.addAttribute("link", link);
        Client client = clientService.findCurrentClient();
        if (!client.getLinks().contains(link)){
            client.getLinks().add(link);
            ParserThread thread = applicationContext.getBean(ParserThread.class);
            thread.setLink(link);
            thread.setClient(client);
            taskExecutor.execute(thread);
            model.addAttribute("addInfo", client.getLogin());
        }
        return "/admin/add_goods";
    }
}