/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.services.impl;

import com.nnt.pojo.Extension;
import com.nnt.pojo.ParkingLot;
import com.nnt.repositories.ExtensionRepository;
import com.nnt.services.ParkingLotService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nnt.repositories.ParkingLotRepository;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ngoct
 */
@Service
public class ParkingLotServiceImpl implements ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ExtensionRepository extensionRepository;

    @Override
    public List<ParkingLot> getParkingLots() {
        return this.parkingLotRepository.getParkingLots();
    }

    @Override
    public ParkingLot getParkingLotById(int id) {
        return this.parkingLotRepository.getParkingLotById(id);
    }

    @Override
    public void addOrUpdateParkingLot(ParkingLot p, List<Integer> extensionIds) {

        Set<Extension> extensions = new HashSet<>();

        if (extensionIds != null && !extensionIds.isEmpty()) {
            List<Extension> extList = this.extensionRepository.getExtensionByIds(extensionIds);
            extensions.addAll(extList);
        }
        p.setExtensionSet(extensions); // Gán extensionSet
        this.parkingLotRepository.addOrUpdateParkingLot(p);

    }

    @Override
    public void deleteParkingLot(int id) {
        this.parkingLotRepository.deleteParkingLot(id);
    }
}
