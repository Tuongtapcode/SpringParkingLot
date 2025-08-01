/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nnt.repositories;

import com.nnt.pojo.ParkingLot;
import java.util.List;

/**
 *
 * @author ngoct
 */
public interface ParkingLotRepository {

    public List<ParkingLot> getParkingLots();

    ParkingLot getParkingLotById(int id);

    void addOrUpdateParkingLot(ParkingLot p);

    void deleteParkingLot(int id);

    List<ParkingLot> getParkingLotByIds(List<Integer> ids);
}
