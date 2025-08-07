/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.services.impl;

import com.nnt.pojo.Reservation;
import com.nnt.pojo.ReservationRequest;
import com.nnt.repositories.ReservationRepository;
import com.nnt.services.ReservationService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ngoct
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public void addReservation(List<ReservationRequest> reservations) {
        this.reservationRepository.addReservation(reservations);
    }

    @Override
    public List<Reservation> getUpcomingPendingReservations(Map<String, String> params) {
        return this.reservationRepository.getUpcomingPendingReservations(params);
    }

    @Override
    public Reservation getReservationById(int id) {
        return this.reservationRepository.getReservationById(id);
    }

    @Override
    public Reservation cancelReservationPending(int id) {
        return this.reservationRepository.cancelReservationPending(id);
    }

}
