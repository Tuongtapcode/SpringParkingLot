/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.controllers;

import com.nnt.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author ngoct
 */
@RestController // RESTful web controller — tức là nó sẽ trả dữ liệu (thường là JSON/XML)
@RequestMapping("/api") // Tạo prefix chung cho tất cả method bên trong class.
@CrossOrigin //Cho phép truy cập từ nguồn khác 
public class ApiParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @DeleteMapping("/parkinglots/{parkinglotId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParkingLot(@PathVariable(value = "parkinglotId") int id) {
        this.parkingLotService.deleteParkingLot(id);
    }
}
