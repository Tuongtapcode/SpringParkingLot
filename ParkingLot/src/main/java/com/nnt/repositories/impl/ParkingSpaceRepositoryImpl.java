/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.repositories.impl;

import com.nnt.pojo.ParkingSpace;
import com.nnt.pojo.ParkingSpaceResponse;
import com.nnt.pojo.Reservation;
import com.nnt.repositories.ParkingSpaceRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author ngoct
 */
@Repository
@Transactional
public class ParkingSpaceRepositoryImpl implements ParkingSpaceRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    private static final int PAGE_SIZE = 10;

    @Override
    public List<ParkingSpace> getParkingSpacesByParkingLotId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        if (id == 0) {
            Query<ParkingSpace> q = s.createQuery("FROM ParkingSpace", ParkingSpace.class);
            return q.getResultList();
        }
        Query q = s.createQuery("FROM ParkingSpace WHERE parkingLotId.id=:id", ParkingSpace.class);
        q.setParameter("id", id);
        return q.getResultList();
    }

    @Override
    public ParkingSpace getParkingSpaceById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        ParkingSpace p = s.get(ParkingSpace.class, id);
        return p;
    }

    @Override
    public ParkingSpace addOrUpdateParkingSpace(ParkingSpace ps) {
        Session s = this.factory.getObject().getCurrentSession();
        Date now = new Date();
        if (ps.getId() == null) {
            ps.setCreatedAt(now);
            ps.setUpdatedAt(now);
            s.persist(ps);
        } else {
            ps.setCreatedAt(this.getParkingSpaceById(ps.getId()).getCreatedAt());
            ps.setUpdatedAt(now);
            s.merge(ps);
        }

        return ps;
    }

    @Override
    public void deleteParkingSpace(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        ParkingSpace ps = this.getParkingSpaceById(id);
        s.remove(ps);
    }

    @Override
    public List<ParkingSpaceResponse> getListParkingSpace(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<ParkingSpace> q = b.createQuery(ParkingSpace.class);
        Root<ParkingSpace> root = q.from(ParkingSpace.class);
        q.select(root);

        List<Predicate> predicates = new ArrayList<>();

        if (params != null) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), "%" + kw + "%"));
            }

            String vh = params.get("vh");
            if (vh != null && !vh.isEmpty()) {
                predicates.add(b.like(b.lower(root.get("vehicleType")), "%" + vh.toLowerCase() + "%"));
            }

            String parkingLotId = params.get("pklId");
            if (parkingLotId != null && !parkingLotId.isEmpty()) {
                predicates.add(b.equal(root.get("parkingLotId").as(Integer.class), Integer.parseInt(parkingLotId)));
            }

            q.where(predicates.toArray(new Predicate[0]));
        }

        Query query = s.createQuery(q);

        if (params != null && params.containsKey("page")) {
            int page = Integer.parseInt(params.get("page"));
            int start = (page - 1) * PAGE_SIZE;
            query.setMaxResults(PAGE_SIZE);
            query.setFirstResult(start);
        }

        List<ParkingSpace> spaces = query.getResultList();

        // Parse startTime & endTime từ params
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        if (params != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                if (params.containsKey("startTime")) {
                    startTime = LocalDateTime.parse(params.get("startTime"), formatter);
                }
                if (params.containsKey("endTime")) {
                    endTime = LocalDateTime.parse(params.get("endTime"), formatter);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd HH:mm:ss");
            }
        }

        LocalDateTime now = LocalDateTime.now();
        List<ParkingSpaceResponse> result = new ArrayList<>();

        for (ParkingSpace space : spaces) {
            String status = "AVAILABLE";

            if ("BLOCKED".equals(space.getStatus())) {
                status = "BLOCKED";
            } else if (startTime != null && endTime != null) {
                for (Reservation res : space.getReservationSet()) {
                    if ("CONFIRM".equals(res.getStatus())) {
                        LocalDateTime resStart;
                        LocalDateTime resEnd;

                        if (res.getActualStartTime() != null && res.getActualEndTime() != null) {
                            resStart = res.getActualStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                            resEnd = res.getActualEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                        } else {
                            resStart = res.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                            resEnd = res.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                        }

                        if (resEnd.isAfter(startTime) && resStart.isBefore(endTime)) {
                            if (now.isAfter(resStart) && now.isBefore(resEnd)) {
                                status = "OCCUPIED";
                            } else {
                                status = "BOOKED";
                            }
                            break;
                        }
                    }
                }
            }

            ParkingSpaceResponse dto = new ParkingSpaceResponse();
            dto.setId(space.getId());
            dto.setName(space.getName());
            dto.setVehicleType(space.getVehicleType().toString());
            dto.setParkingLotId(space.getParkingLotId().getId());
            dto.setStatus(status);

            result.add(dto);
        }
        return result;
    }

//
//    @Override
//    public List<ParkingSpace> getListParkingSpace(Map<String, String> params) {
//        Session s = this.factory.getObject().getCurrentSession();
//        CriteriaBuilder b = s.getCriteriaBuilder();
//        CriteriaQuery<ParkingSpace> q = b.createQuery(ParkingSpace.class);
//        Root root = q.from(ParkingSpace.class);
//        q.select(root);
//        if (params != null) {
//            List<Predicate> predicates = new ArrayList<>();
//
//            String kw = params.get("kw");
//
//            if (kw != null && !kw.isEmpty()) {
//                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
//            }
//            String parkingLotId = params.get("pklId");
//            if (parkingLotId != null && !parkingLotId.isEmpty()) {
//                predicates.add(b.equal(root.get("parkingLotId").as(Integer.class), parkingLotId));
//            }
//            q.where(predicates.toArray(Predicate[]::new));
//            Query query = s.createQuery(q);
//        }
//        Query query = s.createQuery(q);
//        if (params != null && params.containsKey("page")) {
//            int page = Integer.parseInt(params.get("page"));
//            int start = (page - 1) * PAGE_SIZE;
//
//            query.setMaxResults(PAGE_SIZE);
//            query.setFirstResult(start);
//        }
//
//        return query.getResultList();
//
//    }
}
