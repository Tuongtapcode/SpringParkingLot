/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.controllers;

import com.nnt.pojo.ParkingSpace;
import com.nnt.services.ParkingLotService;
import com.nnt.services.ParkingSpaceService;
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
public class ParkingSpaceController {

    @Autowired
    private ParkingSpaceService parkingSpaceService;

    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping("/parkingspaces")
    public String ListParkingSpaces(Model model, @RequestParam(value = "lotId", required = false, defaultValue = "1") int lotId) {
        model.addAttribute("activePage", "parkingspaces");
        model.addAttribute("parkinglots", this.parkingLotService.getParkingLots());
        model.addAttribute("parkingSpaces", this.parkingSpaceService.getParkingSpacesByParkingLotId(lotId));
        model.addAttribute("selectedLotId", lotId);
        return "parkingspaces";
    }

    @GetMapping("/parkingspaces/{parkingspaceId}")
    public String updateParkinglot(Model model, @PathVariable(value = "parkingspaceId") int id) {
        model.addAttribute("activePage", "parkingspaces");
        model.addAttribute("parkingspace", this.parkingSpaceService.getParkingSpaceById(id));
        model.addAttribute("parkinglots", this.parkingLotService.getParkingLots());
        return "parkingspaceDetail";
    }

    @PostMapping("/parkingspaces")
    public String saveParkinglot(@ModelAttribute(value = "parkingspace") ParkingSpace p) {
        this.parkingSpaceService.addOrUpdateParkingSpace(p);
        return "redirect:/parkingspaces";
    }

    //Thêm chỗ đỗ 
    @GetMapping("/parkingspaces/add")
    public String addParkinglot(Model model) {
        model.addAttribute("parkinglots", this.parkingLotService.getParkingLots());
        model.addAttribute("parkingspace", new ParkingSpace());
        return "parkingspaceDetail";
    }
    
    
}
