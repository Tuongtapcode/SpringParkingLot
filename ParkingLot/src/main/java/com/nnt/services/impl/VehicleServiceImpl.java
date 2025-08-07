/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.services.impl;

import com.nnt.pojo.Vehicle;
import com.nnt.repositories.VehicleRepository;
import com.nnt.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ngoct
 */
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Vehicle getParkingLotByLicensePlate(String s) {
        return this.vehicleRepository.getParkingLotByLicensePlate(s);
    }

}
