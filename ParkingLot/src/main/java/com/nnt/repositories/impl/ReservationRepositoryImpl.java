/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.repositories.impl;

import com.nnt.pojo.ParkingSpace;
import com.nnt.pojo.Reservation;
import com.nnt.pojo.ReservationRequest;
import com.nnt.pojo.User;
import com.nnt.repositories.ParkingSpaceRepository;
import com.nnt.repositories.ReservationRepository;
import com.nnt.repositories.UserRepository;
import com.nnt.repositories.VehicleRepository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.ZoneId;

/**
 *
 * @author ngoct
 */
@Repository
@Transactional
public class ReservationRepositoryImpl implements ReservationRepository {

    private static final int PAGE_SIZE = 3;

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public void addReservation(List<ReservationRequest> reservations) {
        Session s = this.factory.getObject().getCurrentSession();

        for (var reservation : reservations) {
            boolean available = true;
            if (!available) {
                throw new RuntimeException("Chỗ đỗ đã bị đặt trùng thời gian: " + reservation.getParkingSpaceId());
            }
            Reservation r = new Reservation();
            r.setStartTime(reservation.getStartTime());
            r.setEndTime(reservation.getEndTime());
            ParkingSpace ps = this.parkingSpaceRepository.getParkingSpaceById(reservation.getParkingSpaceId());
            r.setParkingSpaceId(ps);
            r.setStatus("PENDING");
            r.setUserId(this.userRepository.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));// update current user
            LocalDateTime now = LocalDateTime.now();
            Date date = Timestamp.valueOf(now);
            r.setCreatedAt(date);
            if (reservation.getLicensePlate() != null && !reservation.getLicensePlate().isBlank()) {
                r.setVehicleId(this.vehicleRepository.getParkingLotByLicensePlate(reservation.getLicensePlate()));
            }
            s.persist(r);
            double pricePerHour = ps.getParkingLotId().getPricePerHour();
            LocalDateTime start = reservation.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime end = reservation.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            double hours = Duration.between(start, end).toMinutes() / 60.0;
            Double baseAmount = hours * pricePerHour;
            r.setBaseAmount(baseAmount);
        }
    }

    @Override
    public List<Reservation> getUpcomingPendingReservations(Map<String, String> params) {
//        Session s = this.factory.getObject().getCurrentSession();
//        User u = this.userRepository.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        String hql = "SELECT r FROM Reservation r WHERE r.status = :status AND r.userId = :user AND r.startTime > CURRENT_TIMESTAMP";
//        Query<Reservation> query = s.createQuery(hql, Reservation.class);
//        query.setParameter("status", "PENDING");
//        query.setParameter("user", u);
//
//        if (params != null && params.containsKey("page")) {
//            int page = Integer.parseInt(params.get("page"));
//            int start = (page - 1) * PAGE_SIZE;
//
//            query.setMaxResults(PAGE_SIZE);
//            query.setFirstResult(start);
//        }
//
//        return query.getResultList();
        Session s = this.factory.getObject().getCurrentSession();
        User u = this.userRepository.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        String hql = "SELECT r FROM Reservation r WHERE r.userId = :user ORDER BY ID DESC";
        Query<Reservation> query = s.createQuery(hql, Reservation.class);
        query.setParameter("user", u);
        

        if (params != null && params.containsKey("page")) {
            int page = Integer.parseInt(params.get("page"));
            int start = (page - 1) * PAGE_SIZE;

            query.setMaxResults(PAGE_SIZE);
            query.setFirstResult(start);
        }

        return query.getResultList();
    }

    @Override
    public Reservation getReservationById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Reservation p = s.get(Reservation.class, id);
        return p;
    }

    @Override
    public Reservation cancelReservationPending(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        User u = this.userRepository.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        String hql = "SELECT r FROM Reservation r WHERE r.id = :id AND r.userId = :user AND r.status = :status";
        Query<Reservation> query = s.createQuery(hql, Reservation.class);
        query.setParameter("id", id);
        query.setParameter("user", u);
        query.setParameter("status", "PENDING");
        Reservation r;
        r = query.getSingleResult();

        r.setStatus("CANCELLED");
        s.merge(r);
        return r;
    }
}
