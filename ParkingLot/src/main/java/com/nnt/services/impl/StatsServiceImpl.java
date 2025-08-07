/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.services.impl;

import com.nnt.repositories.StatsRepository;
import com.nnt.services.StatsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ngoct
 */
@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsRepository statsRepository;

    @Override
    public long totalParkingLot() {
        return this.statsRepository.totalParkingLot();
    }

    @Override
    public long totalParkingSpace() {
        return this.statsRepository.totalParkingSpace();
    }

    @Override
    public long blockedParkingSpace() {
        return this.statsRepository.blockedParkingSpace();
    }

    @Override
    public double revenueThisMonth() {
        return this.statsRepository.revenueThisMonth();
    }

    @Override
    public List<Object[]> statsRevenueByParkingLot() {
        return this.statsRepository.statsRevenueByParkingLot();
    }

    @Override
    public List<Object[]> statsRevenueByTime(String time, int year) {
        return this.statsRepository.statsRevenueByTime(time, year);
    }
}
