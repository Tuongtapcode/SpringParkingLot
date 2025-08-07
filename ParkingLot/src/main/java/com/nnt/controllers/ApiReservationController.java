/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.controllers;

import com.nnt.pojo.Reservation;
import com.nnt.pojo.ReservationRequest;
import com.nnt.services.ReservationService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ngoct
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/secure/reservation")
    @ResponseStatus(HttpStatus.CREATED)
    public void addReservation(@RequestBody List<ReservationRequest> reservationRequest) {
        this.reservationService.addReservation(reservationRequest);
    }

    @GetMapping("/secure/upcoming/pending")
    public ResponseEntity<List<Reservation>> getUpcomingPendingReservations(@RequestParam Map<String, String> params) {
        List<Reservation> reservations = this.reservationService.getUpcomingPendingReservations(params);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping("/secure/reservation/{id}/cancel")
    public ResponseEntity<?> cancelReservation(@PathVariable("id") int id) {
        Reservation r = this.reservationService.cancelReservationPending(id);
        return ResponseEntity.ok(r);    
    }
}
