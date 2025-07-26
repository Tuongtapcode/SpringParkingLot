/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.controllers;

import com.nnt.pojo.ParkingLot;
import com.nnt.services.ExtensionService;
import com.nnt.services.ParkingLotService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ngoct
 */
@Controller
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @Autowired
    private ExtensionService extensionService;

    @GetMapping("/parkinglots")
    public String ListParkingLots(Model model) {
        model.addAttribute("activePage", "parkinglots");
        model.addAttribute("parkinglots", this.parkingLotService.getParkingLots());
        return "parkinglots";
    }

    @PostMapping("/parkinglots")
    public String saveParkinglot(@ModelAttribute(value = "parkinglot") ParkingLot p,
            @RequestParam(value = "extensionIds", required = false) List<Integer> extensionIds
    ) {
        this.parkingLotService.addOrUpdateParkingLot(p, extensionIds);
        return "redirect:/parkinglots";
    }

    //Cập nhật
    @GetMapping("/parkinglots/{parkinglotId}")
    public String updateParkinglot(Model model, @PathVariable(value = "parkinglotId") int id) {
        model.addAttribute("parkinglot", this.parkingLotService.getParkingLotById(id));
        model.addAttribute("activePage", "parkinglots");
        model.addAttribute("extensions", this.extensionService.getExtensions());
        return "parkinglotDetail";
    }

    @GetMapping("/parkinglots/add")
    public String addParkinglot(Model model) {
        model.addAttribute("parkinglot", new ParkingLot());
        model.addAttribute("activePage", "parkinglots");
        model.addAttribute("extensions", this.extensionService.getExtensions());
        return "parkinglotDetail";
    }
}
