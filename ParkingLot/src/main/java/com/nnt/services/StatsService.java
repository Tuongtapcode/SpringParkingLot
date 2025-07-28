/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nnt.services;

/**
 *
 * @author ngoct
 */
public interface StatsService {

    long totalParkingLot();

    long totalParkingSpace();

    long availableParkingSpace();

    double revenueThisMonth();
}
