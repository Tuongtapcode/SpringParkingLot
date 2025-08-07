/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nnt.services;

import java.util.List;

/**
 *
 * @author ngoct
 */
public interface StatsService {

    long totalParkingLot();

    long totalParkingSpace();

    long blockedParkingSpace();

    double revenueThisMonth();

    List<Object[]> statsRevenueByParkingLot();

    List<Object[]> statsRevenueByTime(String time, int year);
}
