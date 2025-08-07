/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.controllers;

import com.nnt.pojo.ParkingLot;
import com.nnt.services.ParkingLotService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author ngoct
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping("/parkinglots")
    public ResponseEntity<List<ParkingLot>> list(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.parkingLotService.getParkingLots(params), HttpStatus.OK);
    }

    @DeleteMapping("/parkinglots/{parkinglotId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParkingLot(@PathVariable(value = "parkinglotId") int id) {
        this.parkingLotService.deleteParkingLot(id);
    }
}
