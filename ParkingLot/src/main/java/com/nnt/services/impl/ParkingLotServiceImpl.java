/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.services.impl;

import com.nnt.pojo.ParkingLot;
import com.nnt.services.ParkingLotService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nnt.repositories.ParkingLotRepository;

/**
 *
 * @author ngoct
 */
@Service
public class ParkingLotServiceImpl implements ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Override
    public List<ParkingLot> getParkingLots() {
        return this.parkingLotRepository.getParkingLots();
    }
}
