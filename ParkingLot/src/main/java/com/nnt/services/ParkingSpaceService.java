/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nnt.services;

import com.nnt.pojo.ParkingSpace;
import java.util.List;

/**
 *
 * @author ngoct
 */
public interface ParkingSpaceService {

    List<ParkingSpace> getParkingSpacesByParkingLotId(int id);

    ParkingSpace getParkingSpaceById(int id);

    ParkingSpace addOrUpdateParkingSpace(ParkingSpace ps);

    void deleteParkingSpace(int id);
}
