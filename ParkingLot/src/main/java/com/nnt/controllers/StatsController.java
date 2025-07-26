package com.nnt.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ngoct
 */
@Controller
public class StatsController {

    @GetMapping("/stats")
    public String ListParkingSpaces(Model model) {
        model.addAttribute("activePage", "stats");
        return "stats";
    }
}
