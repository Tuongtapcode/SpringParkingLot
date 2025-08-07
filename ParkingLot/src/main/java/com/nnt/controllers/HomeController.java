/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.controllers;

import com.nnt.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ngoct
 */
@Controller
public class HomeController {

    @Autowired
    private StatsService statsService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("totalPL", this.statsService.totalParkingLot());
        model.addAttribute("totalPS", this.statsService.totalParkingSpace());
        model.addAttribute("availablePSp", this.statsService.blockedParkingSpace());
        model.addAttribute("rvThisMonth", this.statsService.revenueThisMonth());

        return "index";
    }
}
