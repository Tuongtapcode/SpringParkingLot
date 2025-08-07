package com.nnt.controllers;

import com.nnt.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    private StatsService statsService;

    @GetMapping("/stats")
    public String ListParkingSpaces(Model model,
            @RequestParam(name = "month", required = false, defaultValue = "MONTH") String time,
            @RequestParam(name = "year", required = false, defaultValue = "2025") Integer year) {
        model.addAttribute("activePage", "stats");

        model.addAttribute("statsRevenueParkingLot", this.statsService.statsRevenueByParkingLot());
        model.addAttribute("statsRevenueParkingLotByTime", this.statsService.statsRevenueByTime(time.toUpperCase(), year));
        model.addAttribute("selectedTime", time);
        model.addAttribute("inputedYear", year);
        return "stats";
    }
}
