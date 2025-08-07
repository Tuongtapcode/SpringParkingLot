/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nnt.services;

import com.nnt.pojo.ParkingLot;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ngoct
 */
public interface ParkingLotService {

    List<ParkingLot> getParkingLots(Map<String, String> params);

    ParkingLot getParkingLotById(int id);

    void addOrUpdateParkingLot(ParkingLot p, List<Integer> extensionIds);

    void deleteParkingLot(int id);

}
