/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nnt.repositories;

import com.nnt.pojo.Reservation;
import com.nnt.pojo.ReservationRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ngoct
 */
public interface ReservationRepository {

    Reservation getReservationById(int id);

    void addReservation(List<ReservationRequest> reservations);

    List<Reservation> getUpcomingPendingReservations(Map<String, String> params);

    Reservation cancelReservationPending(int id);
}
