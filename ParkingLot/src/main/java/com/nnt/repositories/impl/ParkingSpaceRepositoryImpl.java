/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.repositories.impl;

import com.nnt.pojo.ParkingSpace;
import com.nnt.repositories.ParkingSpaceRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

/**
 *
 * @author ngoct
 */
@Repository
@Transactional
public class ParkingSpaceRepositoryImpl implements ParkingSpaceRepository {
    
    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public List<ParkingSpace> getParkingSpacesByParkingLotId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
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
    
}
