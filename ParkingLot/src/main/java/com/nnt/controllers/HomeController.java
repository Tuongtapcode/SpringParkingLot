/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ngoct
 */
@Controller
public class HomeController {

    
    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("activePage", "dashboard");
        return "index";
    }
}
