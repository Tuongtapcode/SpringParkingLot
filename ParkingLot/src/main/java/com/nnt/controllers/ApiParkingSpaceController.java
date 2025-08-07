/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.controllers;

import com.nnt.pojo.ParkingSpace;
import com.nnt.pojo.ParkingSpaceResponse;
import com.nnt.services.ParkingSpaceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author ngoct
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiParkingSpaceController {

    @Autowired
    private ParkingSpaceService parkingSpaceService;

    @DeleteMapping("/parkingspaces/{parkingSpaceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParkingSpace(@PathVariable(value = "parkingSpaceId") int id) {
        this.parkingSpaceService.deleteParkingSpace(id);
    }

    @GetMapping("/parkinglots/{parkingLotId}/spaces")
    public ResponseEntity<List<ParkingSpace>> getSpacesById(@PathVariable("parkingLotId") int id) {
        return ResponseEntity.ok(parkingSpaceService.getParkingSpacesByParkingLotId(id));
    }

    @GetMapping("/parkingspaces")
    public ResponseEntity<List<ParkingSpaceResponse>> getSpaces(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.parkingSpaceService.getListParkingSpace(params), HttpStatus.OK);
    }
}
