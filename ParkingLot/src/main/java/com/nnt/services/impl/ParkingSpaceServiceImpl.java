/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.services.impl;

import com.nnt.pojo.ParkingSpace;
import com.nnt.pojo.ParkingSpaceResponse;
import com.nnt.repositories.ParkingSpaceRepository;
import com.nnt.services.ParkingSpaceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ngoct
 */
@Service
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    @Override
    public List<ParkingSpace> getParkingSpacesByParkingLotId(int id) {
        return this.parkingSpaceRepository.getParkingSpacesByParkingLotId(id);
    }

    @Override
    public ParkingSpace getParkingSpaceById(int id) {
        return this.parkingSpaceRepository.getParkingSpaceById(id);
    }

    @Override
    public ParkingSpace addOrUpdateParkingSpace(ParkingSpace ps) {
        return this.parkingSpaceRepository.addOrUpdateParkingSpace(ps);
    }

    @Override
    public void deleteParkingSpace(int id) {
        this.parkingSpaceRepository.deleteParkingSpace(id);
    }

    @Override
    public List<ParkingSpaceResponse> getListParkingSpace(Map<String, String> params) {
        return this.parkingSpaceRepository.getListParkingSpace(params);
    }

}
