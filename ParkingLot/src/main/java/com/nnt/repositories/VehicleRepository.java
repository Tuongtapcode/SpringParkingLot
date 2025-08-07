/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nnt.repositories;

import com.nnt.pojo.Vehicle;

/**
 *
 * @author ngoct
 */
public interface VehicleRepository {

    Vehicle getParkingLotByLicensePlate(String s);
}
