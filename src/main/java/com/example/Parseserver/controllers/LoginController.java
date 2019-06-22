package com.example.Parseserver.controllers;

import com.example.Parseserver.entity.Client;
import com.example.Parseserver.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        Client client = new Client();
        modelAndView.addObject("client", client);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid Client client, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Client clientExists = clientService.findClientByLogin(client.getLogin());
        if (clientExists != null) {
            bindingResult
                    .rejectValue("client", "error.client",
                            "There is already a client registered with the this login");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            clientService.saveClient(client);
            modelAndView.addObject("successMessage", "Client has been registered successfully");
            modelAndView.addObject("client", new Client());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/menu", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientService.findClientByLogin(auth.getName());
        modelAndView.addObject("userName", "Welcome " + client.getLogin());
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/menu");
        return modelAndView;
    }


}
