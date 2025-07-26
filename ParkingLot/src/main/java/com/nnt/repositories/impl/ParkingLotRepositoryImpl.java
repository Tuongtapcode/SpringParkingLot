/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.repositories.impl;

import com.nnt.pojo.ParkingLot;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.nnt.repositories.ParkingLotRepository;
import java.util.Date;
import org.hibernate.Hibernate;

/**
 *
 * @author ngoct
 */
@Repository
@Transactional
public class ParkingLotRepositoryImpl implements ParkingLotRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<ParkingLot> getParkingLots() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM ParkingLot", ParkingLot.class);
        return q.getResultList();
    }

    @Override
    public ParkingLot getParkingLotById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        ParkingLot p = s.get(ParkingLot.class, id);

        // Ép Hibernate load extensionSet ngay khi còn session
        Hibernate.initialize(p.getExtensionSet());

        return p;
    }

    @Override
    public void addOrUpdateParkingLot(ParkingLot p) {
        Session s = this.factory.getObject().getCurrentSession();
        Date now = new Date();

        if (p.getId() == null) {
            p.setCreatedAt(now);
            p.setUpdatedAt(now);
            s.persist(p);
        } else {
//            ParkingLot existing = s.get(ParkingLot.class, p.getId());
//            p.setCreatedAt(existing.getCreatedAt());
            p.setCreatedAt(this.getParkingLotById(p.getId()).getCreatedAt());
            p.setUpdatedAt(now); // gán thời gian cập nhật
            s.merge(p);
        }
    }

    @Override
    public void deleteParkingLot(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        ParkingLot p = this.getParkingLotById(id);
        if (p != null) {
            p.getExtensionSet().clear();
            s.remove(p);
        }

    }

    @Override
    public List<ParkingLot> getParkingLotByIds(List<Integer> ids) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM ParkingLot  WHERE id IN (:ids)", ParkingLot.class).setParameter("ids", ids);
        return q.getResultList();
    }
}
