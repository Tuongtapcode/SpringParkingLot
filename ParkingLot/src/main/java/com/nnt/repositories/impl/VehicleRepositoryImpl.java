/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nnt.repositories.impl;

import com.nnt.pojo.Vehicle;
import com.nnt.repositories.VehicleRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ngoct
 */
@Repository
@Transactional
public class VehicleRepositoryImpl implements VehicleRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Vehicle getParkingLotByLicensePlate(String licensePlate) {
        Session session = this.factory.getObject().getCurrentSession();
        String hql = "FROM Vehicle WHERE licensePlate = :licensePlate";
        return session.createQuery(hql, Vehicle.class)
                .setParameter("licensePlate", licensePlate)
                .uniqueResult();
    }

}
