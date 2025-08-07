/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.controllers;

import com.nnt.pojo.Extension;
import com.nnt.services.ExtensionService;
import com.nnt.services.ParkingLotService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author ngoct
 */
@Controller
public class ExtensionController {

    @Autowired
    private ExtensionService extensionService;

    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping("/extensions")
    public String ListExtensions(Model model) {
        model.addAttribute("activePage", "extensions");
        model.addAttribute("extensions", this.extensionService.getExtensions());
        return "extensions";
    }

    //Cập nhật
    @GetMapping("/extensions/{extensionId}")
    public String updateExtension(Model model, @PathVariable(value = "extensionId") int id) {
        model.addAttribute("activePage", "extensions");
        model.addAttribute("extension", this.extensionService.getExtensionById(id));
        model.addAttribute("parkinglots", this.parkingLotService.getParkingLots(Collections.emptyMap()));
        return "extensionDetail";
    }

    @PostMapping("/extensions")
    public String saveExtension(@ModelAttribute(value = "extension") Extension ex) {
        this.extensionService.addOrUpdateExtension(ex);
        return "redirect:/extensions";
    }

    @GetMapping("/extensions/add")
    public String addExtension(Model model) {
        model.addAttribute("activePage", "extensions");
        model.addAttribute("extension", new Extension());
        model.addAttribute("parkinglots", this.parkingLotService.getParkingLots(Collections.emptyMap()));
        return "extensionDetail";
    }
}
