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

}
