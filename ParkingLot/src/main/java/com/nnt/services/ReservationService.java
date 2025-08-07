/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nnt.services;

import com.nnt.pojo.Reservation;
import com.nnt.pojo.ReservationRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ngoct
 */
public interface ReservationService {

    void addReservation(List<ReservationRequest> reservations);

    List<Reservation> getUpcomingPendingReservations(Map<String, String> params);

    Reservation getReservationById(int id);

    Reservation cancelReservationPending(int id);
}
