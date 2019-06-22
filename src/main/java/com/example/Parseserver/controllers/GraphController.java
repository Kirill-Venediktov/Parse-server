package com.example.Parseserver.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GraphController {
    @GetMapping("/displayGraph")
    public String barGraph(Model model){
        return "graph.html";
    }
}
