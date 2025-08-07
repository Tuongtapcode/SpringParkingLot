/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nnt.services;

import com.nnt.pojo.ParkingSpace;
import com.nnt.pojo.ParkingSpaceResponse;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ngoct
 */
public interface ParkingSpaceService {

    List<ParkingSpace> getParkingSpacesByParkingLotId(int id);

    List<ParkingSpaceResponse> getListParkingSpace(Map<String, String> params);

    ParkingSpace getParkingSpaceById(int id);

    ParkingSpace addOrUpdateParkingSpace(ParkingSpace ps);

    void deleteParkingSpace(int id);
}
