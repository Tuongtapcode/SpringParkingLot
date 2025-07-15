/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.controllers;

import com.nnt.pojo.ParkingLot;
import com.nnt.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author ngoct
 */
@Controller
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping("/parkinglots")
    public String ListParkingLots(Model model) {
        model.addAttribute("activePage", "parkinglots");
         model.addAttribute("parkinglots", this.parkingLotService.getParkingLots());
        return "parkinglots";
    }
}
